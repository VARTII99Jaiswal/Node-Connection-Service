package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.CommonResponseBean;
import com.project.dto.CreateConnectionGroupRequest;
import com.project.service.ConnectionGroupService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@RestController
@Validated
@RequestMapping("/v1")
public class VNodeConnectController {

	@Autowired
	private ConnectionGroupService connectionGrpSvc;

	@PostMapping(path = "/connection_group")
	public ResponseEntity<CommonResponseBean> createConnectionGroup(
			@Valid @RequestBody CreateConnectionGroupRequest connectionGrpReqbody) {
		CommonResponseBean resp = connectionGrpSvc.createConnectionGroup(connectionGrpReqbody);
		return setHttpStatusCode(resp);
	}
	
	@GetMapping(path = "/virtual_node_name/{name}")
	public ResponseEntity<CommonResponseBean> createConnectionGroup(
		@NotEmpty @NotNull	@PathVariable String name) {
		CommonResponseBean resp = connectionGrpSvc.fetchConnectionGroupBasedVirtualNodeName(name);
		return setHttpStatusCode(resp);
	}

	private ResponseEntity<CommonResponseBean> setHttpStatusCode(CommonResponseBean resp) {
		switch (resp.getRespCode()) {
		case 1000:
			return new ResponseEntity<CommonResponseBean>(resp, HttpStatus.OK);
		case 5000:
			return new ResponseEntity<CommonResponseBean>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		case 5001:
			return new ResponseEntity<CommonResponseBean>(resp, HttpStatus.BAD_REQUEST);
		case 1001:
			return new ResponseEntity<CommonResponseBean>(resp, HttpStatus.CREATED);
		default:
			return new ResponseEntity<CommonResponseBean>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
