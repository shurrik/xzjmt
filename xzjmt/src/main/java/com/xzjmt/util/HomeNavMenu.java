package com.xzjmt.util;


/**
 * 首页导航菜单枚举
 * @author Administrator
 *
 */
public enum HomeNavMenu {
	
	ITEM("item"), 
	ABOUT("about"),
	SELF("self");
	
	private String label;
	
	private HomeNavMenu(String label) {
		this.label = label;
	}
	
	public String toString() {
		return this.label;
	}
	
	public static HomeNavMenu findByLable(String label){
		for(HomeNavMenu menu : HomeNavMenu.values()){
			if(menu.label.equals(label)){
				return menu;
			}
		}
		return null;
	}
}
