package com.xzjmt.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xzjmt.dao.CategoryDAO;
import com.xzjmt.entity.Category;

@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
public class CategoryMng {

	@Autowired
	private CategoryDAO categoryDAO;
	
	public List<Category> findAll()
	{
		return categoryDAO.findAll();
	}
}
