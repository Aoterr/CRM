package com.zendaimoney.crm.channel.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.crm.channel.entity.Channel;
import com.zendaimoney.crm.channel.service.ChannelService;

@Controller
@RequestMapping(value = "/crm/channel")
public class ChannelController {
	@Autowired
	private ChannelService channelService;

	@RequestMapping("getChannels")
	@ResponseBody
	public List<Channel> getChannel() {
		return channelService.findAllOrder();
	}

	@RequestMapping("getChannelTop")
	@ResponseBody
	public List<Channel> getChannelByParent() {
		return channelService.findAllByParent(Long.valueOf(1));
	}

	@RequestMapping("getChannelAll")
	@ResponseBody
	public List<Channel> getChannelAll() {
		return channelService.findOrder();
	}

	@RequestMapping("getChannelTopAll")
	@ResponseBody
	public List<Channel> getChannelByParentAll() {
		return channelService.findByParent(Long.valueOf(1));
	}

}
