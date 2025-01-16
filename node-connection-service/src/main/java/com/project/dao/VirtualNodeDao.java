package com.project.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.dto.CreateConnectionGroupRequest;
import com.project.dto.VirtualNodesDetails;
import com.project.model.ConnectionGroup;
import com.project.model.VirtualNode;
import com.project.repo.VirtualNodeRepo;

@Component
public class VirtualNodeDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(VirtualNodeDao.class);

	@Autowired
	private VirtualNodeRepo virtualNodeRepo;

	public boolean checkIfVirtualNodeExistWithName(CreateConnectionGroupRequest connectionGrpReqbody) {
		List<String> virtualNodes = new ArrayList<>();
		for (VirtualNodesDetails node : connectionGrpReqbody.getVirtualNodes()) {
			virtualNodes.add(node.getVirtualNodeName());
		}
		return virtualNodeRepo.findIfVirtualNodewithNameExist(virtualNodes);
	}

	public void saveVirtualNodes(CreateConnectionGroupRequest connectionGrpReqbody,
			ConnectionGroup savedConnectionGrpId) {
		VirtualNode parentNode = null;
		for (VirtualNodesDetails node : connectionGrpReqbody.getVirtualNodes()) {
			String parentName = node.getParentName();
			if (parentName != null && !parentName.isEmpty()) {
				parentNode = virtualNodeRepo.findByName(parentName);
			}
			VirtualNode savedNode = saveVirtualNode(node, parentNode, savedConnectionGrpId);
			LOGGER.info("Virtual Node saved with Id:{}|ConnectionGroupId:{}", savedNode.getId(), savedConnectionGrpId);
		}
	}

	private VirtualNode saveVirtualNode(VirtualNodesDetails node, VirtualNode parentNode,
			ConnectionGroup savedConnectionGrpId) {
		VirtualNode createNode = new VirtualNode();		
		createNode.setVirtualNodeName(node.getVirtualNodeName());
		createNode.setParent(parentNode);
		createNode.setConnectionGroupId(savedConnectionGrpId);
		return virtualNodeRepo.save(createNode);
	}

	public String fetchConnGrpNameBasedVNodeName(String name) {
		String connectionGroupName = "";
		VirtualNode virtualNode = virtualNodeRepo.findByName(name);
		if (virtualNode != null) {
			ConnectionGroup connectionGrp = virtualNode.getConnectionGroupId();
			if (connectionGrp != null) {
				connectionGroupName = connectionGrp.getConnectionGrpName();
			}
		} else {
			LOGGER.info("Virtual Node with name:{} is not found", name);
		}
		return connectionGroupName;
	}

}
