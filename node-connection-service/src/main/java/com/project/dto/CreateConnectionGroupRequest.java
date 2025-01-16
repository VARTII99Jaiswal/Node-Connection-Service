package com.project.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateConnectionGroupRequest {

	@NotNull
	@NotEmpty(message="Connection Group name cannot be empty")
	private String groupName;
	@NotNull(message="Virtual Node list cannot be null")	
	private List<VirtualNodesDetails> virtualNodes;
}
