package com.project.dao;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.dto.CommonResponseBean;
import com.project.dto.CreateConnectionGroupRequest;
import com.project.model.ConnectionGroup;
import com.project.repo.ConnectionGroupRepo;

@Component
public class ConnectionGroupDao {

	@Autowired
	private VirtualNodeDao virtualNodeDao;
	@Autowired
	private ConnectionGroupRepo connectionRepo;

	public void saveConnectionGroup(CreateConnectionGroupRequest connectionGrpReqbody, CommonResponseBean response) {
		ConnectionGroup connectionGroup = new ConnectionGroup();
		connectionGroup.setConnectionGrpName(connectionGrpReqbody.getGroupName());
		connectionGroup.setCreationDate(LocalDate.now());
		ConnectionGroup savedConnectionGrpId = connectionRepo.save(connectionGroup);
		virtualNodeDao.saveVirtualNodes(connectionGrpReqbody, savedConnectionGrpId);
	}

}
