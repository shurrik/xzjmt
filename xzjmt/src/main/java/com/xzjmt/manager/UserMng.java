package com.xzjmt.manager;

import java.sql.SQLException;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xzjmt.common.dao.EntityView;
import com.xzjmt.common.util.DateUtils;
import com.xzjmt.common.util.Md5Utils;
import com.xzjmt.dao.UserDAO;
import com.xzjmt.entity.User;

@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
public class UserMng {

	@Autowired
	private UserDAO userDAO;

	
	public User findById(Integer id)
	{
		return userDAO.findById(id);
	}
	
	public void saveOrUpdate(User user)
	{
		userDAO.saveOrUpdate(user);
	}
	/**
	 * 根据登录帐号(邮箱或昵称)获取用户对象
	 * @param loginName
	 * @return
	 * @throws SQLException
	 */
	public User findUserByLoginName(String loginName) {
		boolean matchEmail = Pattern.matches("\\w+(\\.\\w+)*@(\\w+\\.)+(\\w{2,4})+", loginName);
		User user = userDAO.findUniq(matchEmail ? "email" : "nickName", loginName);
		if(user == null) {
			throw new UnknownAccountException("No account found for user [" + loginName + "]");
		}
		return user;
	}
	
	
	/**
	 * 检查属性值是否存在
	 */
	public boolean checkExists(String propertyName, Object propertyValue) {
		boolean exists = false;
		if(StringUtils.isNotBlank(propertyName)) {
			EntityView ev = new EntityView();
			ev.add(Restrictions.eq(propertyName, propertyValue instanceof String ? ((String)propertyValue).trim() : propertyValue));
			exists = userDAO.exist(ev);
		}
		return exists;
	}
	
	public User findOne(String propertyName,String propertyValue)
	{
		return userDAO.findUniq(propertyName, propertyValue);
	}
	
	public Integer createUserWhenReg(String email,String activeCode)
	{
		User user = new User();
		user.setEmail(email);
		user.setActiveCode(activeCode);
		user.setActived(false);
		user.setRegisterDate(DateUtils.now());
		return userDAO.addNew(user);
	}
	
	public boolean save(Integer userId, String newPassword,String nickName) {
		String newSalt = Md5Utils.getRandomSalt() + "";
		//String newSalt = "632370";
		newPassword = Md5Utils.getLongToken(Md5Utils.getLongToken(newPassword)  + newSalt);
		String sql = "UPDATE xz_user m SET m.nick_name=?, m.passwd=?, m.salt=? WHERE m.user_id=?";
		int n = userDAO.update(sql, new Object [] {nickName, newPassword, newSalt, userId});
		return n > 0;
	}
	
	public boolean changePasswd(Integer userId, String newPassword) {
		String newSalt = Md5Utils.getRandomSalt() + "";
		newPassword = Md5Utils.getLongToken(Md5Utils.getLongToken(newPassword)  + newSalt);
		String sql = "UPDATE xz_user m SET m.passwd=?, m.salt=? WHERE m.user_id=?";
		int n = userDAO.update(sql, new Object [] {newPassword, newSalt, userId});
		return n > 0;
	}
	
	public boolean updateAvatar(Integer userId,String avatar)
	{
		String sql = "UPDATE xz_user m SET m.avatar=? WHERE m.user_id=?";
		int n = userDAO.update(sql, new Object [] {avatar, userId});
		return n > 0;
	}
}
