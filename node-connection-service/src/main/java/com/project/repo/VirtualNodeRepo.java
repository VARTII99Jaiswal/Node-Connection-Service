package com.project.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.model.VirtualNode;

public interface VirtualNodeRepo extends JpaRepository<VirtualNode, Integer> {

	@Query("SELECT v from VirtualNode v WHERE v.virtualNodeName=?1")
	VirtualNode findByName(String vnodeName);

	@Query("SELECT CASE WHEN COUNT(vnd) > 0 THEN TRUE ELSE FALSE END FROM VirtualNode vnd WHERE vnd.virtualNodeName IN (?1)")
	boolean findIfVirtualNodewithNameExist(List<String> virtualNodes);

}
