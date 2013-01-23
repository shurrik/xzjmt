package com.xzjmt.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xzjmt.common.dao.EntityView;
import com.xzjmt.common.page.PageContext;
import com.xzjmt.common.util.DateUtils;
import com.xzjmt.dao.MessageDAO;
import com.xzjmt.entity.Message;
import com.xzjmt.entity.User;

@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
public class MessageMng {

	@Autowired
	private MessageDAO messageDAO;
	
	public Integer sendMessage(String title,String content,User sender,User receiver)
	{
		Message m = new Message();
		m.setTitle(title);
		m.setContent(content);
		m.setCreateDate(DateUtils.now());
		m.setSender(sender.getUserId());
		m.setSenderEmail(sender.getEmail());
		m.setSenderName(sender.getNickName());
		m.setReceiver(receiver.getUserId());
		m.setReceiverEmail(receiver.getEmail());
		m.setReceiverName(receiver.getNickName());
		return messageDAO.addNew(m);
	}
	
	public PageContext<Message> queryUsePage(EntityView ev, Integer pageNum, Integer pageSize) throws Exception
	{
		return messageDAO.queryUsePage(ev, pageNum, pageSize);
	}
	
	public Message findById(Integer id)
	{
		return messageDAO.findById(id);
	}
}
