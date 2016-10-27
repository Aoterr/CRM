package org.springside.modules.orm;

import org.springframework.data.domain.Page;



public class PageVo<T> {
	private Page<T> page;

	public PageVo(Page<T> page) {
		this.page=page;
	}

	public long getTotal(){
		return page.getTotalElements();
	}
	
	public Iterable<T> getRows(){
		return page.getContent();
	}
}
