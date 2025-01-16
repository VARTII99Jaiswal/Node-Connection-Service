package com.project.service;

import com.project.dto.CommonResponseBean;
import com.project.dto.CreateConnectionGroupRequest;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public interface ConnectionGroupService {

	CommonResponseBean createConnectionGroup(CreateConnectionGroupRequest connectionGrpReqbody);

	CommonResponseBean fetchConnectionGroupBasedVirtualNodeName(@NotEmpty @NotNull String name);

}
