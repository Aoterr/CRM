package com.zendaimoney.crm.channel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.crm.BaseService;
import com.zendaimoney.crm.channel.entity.Channel;
import com.zendaimoney.crm.channel.repository.ChannelDao;

@Component
@Transactional(readOnly = true)
public class ChannelService extends BaseService<Channel> {
	@Autowired
	private ChannelDao channelDao;

	public List<Channel> findAllByParent(Long parentId) {
		return channelDao.findAllByParent(parentId);
	}

	public List<Channel> findByParent(Long parentId) {
		return channelDao.findByParent(parentId);
	}

	public List<Channel> findAllOrder() {
		return (List<Channel>) channelDao.findAllOrder();
	}

	public List<Channel> findOrder() {
		return (List<Channel>) channelDao.findOrder();
	}

	public List<Channel> findAll() {
		return (List<Channel>) channelDao.findAll();
	}

	public Channel getChannel(Long id) {
		return channelDao.findOne(id);
	}

}
