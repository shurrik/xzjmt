package com.xzjmt.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.xzjmt.entity.User;

public class XzSecurityUtils extends SecurityUtils {
	/**
	 * 得到当前用户
	 * @return
	 */
	public static User getCurrentMember(){
		Subject currentUser = SecurityUtils.getSubject();
		return  (User)currentUser.getPrincipal();
	}

}
