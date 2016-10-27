package com.zendaimoney.sys.desktop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.sys.desktop.entity.DesktopToolbar;
public interface DesktopDao extends PagingAndSortingRepository<DesktopToolbar, Long> , JpaSpecificationExecutor<DesktopToolbar>{

	@Query("from DesktopToolbar where display =? and type=? order by displaySequence")
	List<DesktopToolbar> getDesktopToolbarByType(Integer display,String type);
}
