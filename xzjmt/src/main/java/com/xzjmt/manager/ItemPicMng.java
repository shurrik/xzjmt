package com.xzjmt.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xzjmt.dao.ItemPicDAO;
import com.xzjmt.entity.ItemPic;

@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
public class ItemPicMng {

	@Autowired
	private ItemPicDAO itemPicDAO;
	
	public Integer add(ItemPic itemPic)
	{
		return itemPicDAO.addNew(itemPic);
	}
	
	public List<ItemPic> getByItemId(Integer itemId)
	{
		return itemPicDAO.findByProperty("itemId", itemId);
	}
}
