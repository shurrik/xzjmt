package com.xzjmt.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xzjmt.common.util.Md5Utils;
import com.xzjmt.entity.User;
import com.xzjmt.manager.UserMng;


public class ShiroDbRealm extends JdbcRealm{
	private static final Logger log = LoggerFactory.getLogger(ShiroDbRealm.class);
	
	@Autowired
	protected UserMng userMng;
	
	protected String authenticationQuery = "select member_id,email,passwd,user_type,nickname,realname,gender,mobile,is_lock as locked,salt from kf_member";

    protected String userRolesQuery = "SELECT r.role_name FROM ka_sys_user u,ka_sys_user_role ur,ka_sys_role r WHERE u.user_id=ur.user_id AND ur.role_id=r.role_id AND u.login_name=?";

    protected String permissionsQuery = "SELECT * FROM ka_sys_menu m,ka_sys_role_menu rm,ka_sys_role r WHERE m.menu_id=rm.menu_id AND rm.role_id=r.role_id AND r.role_name=?";
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        // Null username is invalid
        if (!StringUtils.hasText(username)) {
            throw new InvalidAccountException("Null usernames are not allowed by this realm.");
        }
        AuthenticationInfo info = null;
        try {
            User user = userMng.findUserByLoginName(username.trim());
            if(user.getLocked()!=null && user.getLocked()) {
            	throw new LockedAccountException("The account has been locked by administrator for the user [" + username + "]");
            }
            String salt = user.getSalt()!=null?user.getSalt():"";
            String password = String.copyValueOf(upToken.getPassword()).trim();
            //password = new Md5Hash(new Md5Hash(password).toHex()  + salt).toHex();
            password = Md5Utils.getLongToken(Md5Utils.getLongToken(password)  + salt);
            upToken.setPassword(password.toCharArray());
            
            String realmName = user.getNickName() == null ? "" : user.getNickName();
            String credential = user.getPasswd() == null ? "" : user.getPasswd().trim();
            user.setPasswd(null);
            user.setSalt(null);
            info = new SimpleAuthenticationInfo(user, credential.toCharArray(), realmName);
        } 
        catch(UnknownAccountException e) {
			throw e;
        }
        catch (Exception e) {
            final String message = "There was an error while authenticating user [" + username + "]";
            if (log.isErrorEnabled()) {
                log.error(message, e);
            }
            // Rethrow any SQL errors as an authentication exception
            throw new AuthenticationException(message, e);
        } 
        return info;
	}
	
	public static void main(String[] args)
	{
		String password="1";
		String salt = "632370";
        password = Md5Utils.getLongToken(Md5Utils.getLongToken(password)  + salt);
        System.out.println(password);
	}

}