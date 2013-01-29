package com.xzjmt.shiro;

import java.util.Date;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xzjmt.common.util.RequestUtils;
import com.xzjmt.dao.UserDAO;
import com.xzjmt.entity.User;

public class KsdSecurityManager extends DefaultWebSecurityManager {
/*	@Autowired
	private MemberMongoDAO memberMongoDao;
	@Autowired
	private MemberDAO memberDao;*/
	
	@Autowired
	private UserDAO userDAO;

	@Override
	protected void onSuccessfulLogin(AuthenticationToken token,AuthenticationInfo info, Subject subject) {
		super.onSuccessfulLogin(token, info, subject);
		User user = (User) subject.getPrincipal();
		if (user != null) {
			Integer userId = user.getUserId();
			String sessionId = (String) subject.getSession().getId();
			ServletRequestAttributes request = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			String lastip = RequestUtils.getIpAddr(request.getRequest());
			Date lastvisit = new Date();
			//memberDao.updateLoginStatus(memberId, sessionId, lastip, lastvisit);
			
/*			if(StringUtils.isNotBlank(member.getMongoId())) {
				Query<MemberMongo> q = memberMongoDao.createQuery();
				q.filter("_id", new ObjectId(member.getMongoId()));
				UpdateOperations<MemberMongo> ops = memberMongoDao.createUpdateOperations();
				ops.set("lastLoginTime", lastvisit);
				ops.inc("loginCount", 1);
				MemberMongo mongo = memberMongoDao.findAndModify(q, ops, false, false);
				member.setMongo(mongo);
			}*/
		}
		subject.getSession().setAttribute("user", user);
	}

	@Override
	protected void onFailedLogin(AuthenticationToken token,
			AuthenticationException ae, Subject subject) {
		super.onFailedLogin(token, ae, subject);
/*		Query<MemberMongo> q = memberMongoDao.createQuery();
		q.filter("email", token.getPrincipal());
		UpdateOperations<MemberMongo> ops = memberMongoDao.createUpdateOperations();
		ops.set("lastLoginFailureTime", new Date());
		ops.inc("loginFailureCount", 1);
		this.memberMongoDao.update(q, ops);*/
	}
	private String logoutUrl;
	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	
}