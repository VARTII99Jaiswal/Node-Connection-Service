package com.project.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.ConnectionGroupDao;
import com.project.dao.VirtualNodeDao;
import com.project.dto.CommonResponseBean;
import com.project.dto.CreateConnectionGroupRequest;
import com.project.service.ConnectionGroupService;

@Service
public class ConnectionGroupServiceImpl implements ConnectionGroupService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionGroupServiceImpl.class);

	@Autowired
	private ConnectionGroupDao connectionDao;

	@Autowired
	private VirtualNodeDao virtualNodeDao;

	@Override
	public CommonResponseBean createConnectionGroup(CreateConnectionGroupRequest connectionGrpReqbody) {
		CommonResponseBean response = new CommonResponseBean();
		try {
			LOGGER.info("Create Connection group request rcvd:{}", connectionGrpReqbody.toString());
			boolean isExistingVirtualNodeName = virtualNodeDao.checkIfVirtualNodeExistWithName(connectionGrpReqbody);
			if (isExistingVirtualNodeName) {
				setRespCodeAndMessage(response, 5001, "Duplicate Virtual Node Name");
				return response;
			}
			connectionDao.saveConnectionGroup(connectionGrpReqbody, response);
			response.setConnectionGroupName(connectionGrpReqbody.getGroupName());
			setRespCodeAndMessage(response, 1001, "Connection Group Created");
			LOGGER.info("Create Connection Group Request Processed");
		} catch (RuntimeException e) {
			LOGGER.error("Exception occured creating connection group", e);
			setRespCodeAndMessage(response, 5000, "Internal Server Error");
		}
		return response;
	}

	@Override
	public CommonResponseBean fetchConnectionGroupBasedVirtualNodeName(String name) {
		CommonResponseBean response = new CommonResponseBean();
		try {
			LOGGER.info("Fetch Connection group name for virtual node:{} rcvd", name);
			String connectionGroupName = virtualNodeDao.fetchConnGrpNameBasedVNodeName(name);
			if (connectionGroupName != null && !connectionGroupName.isEmpty()) {
				LOGGER.info("Connection Group Name:{} fetched for virtual node:{}", connectionGroupName, name);
				response.setConnectionGroupName(connectionGroupName);
				setRespCodeAndMessage(response, 1000, "Connection Group Name fetched");
			} else {
				LOGGER.info("Connection Group Name not fetched for virtual node:{}", name);
				setRespCodeAndMessage(response, 1000, "Request Processed,Connection Group not found");
			}
			LOGGER.info("Create Connection Group Request Processed");
		} catch (RuntimeException e) {
			LOGGER.error("Exception occured creating connection group", e);
			setRespCodeAndMessage(response, 5000, "Internal Server Error");
		}
		return response;
	}

	private void setRespCodeAndMessage(CommonResponseBean response, int respCode, String respMsg) {
		response.setRespCode(respCode);
		response.setRespMsg(respMsg);
	}

}
