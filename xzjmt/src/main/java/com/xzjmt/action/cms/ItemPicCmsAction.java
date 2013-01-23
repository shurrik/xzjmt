package com.xzjmt.action.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xzjmt.action.BaseAction;
import com.xzjmt.dao.ItemPicDAO;

@Controller
public class ItemPicCmsAction extends BaseAction{

	@Autowired
	private ItemPicDAO itemPicDAO;
}
