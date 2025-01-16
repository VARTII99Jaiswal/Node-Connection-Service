package com.project.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="connection_group_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="connection_grp_name")
	private String connectionGrpName;
	
	@Column(name="creation_date")
	private LocalDate creationDate;

	@OneToMany(mappedBy = "connectionGroupId",fetch=FetchType.LAZY)
    private List<VirtualNode> virtualNodes;


}
