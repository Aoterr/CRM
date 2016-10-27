package com.zendaimoney.crm.channel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.channel.entity.Channel;

public interface ChannelDao extends PagingAndSortingRepository<Channel, Long> {

	@Query(" from Channel c where c.parentId = ? and c.state=1 order by c.orderNum asc")
	public List<Channel> findAllByParent(Long parentId);

	@Query(" from Channel c where c.parentId = ?  order by c.orderNum asc")
	public List<Channel> findByParent(Long parentId);

	@Query(" from Channel c where c.state=1   order by c.orderNum asc")
	public List<Channel> findAllOrder();

	@Query(" from Channel c  order by c.orderNum asc")
	public List<Channel> findOrder();

}
