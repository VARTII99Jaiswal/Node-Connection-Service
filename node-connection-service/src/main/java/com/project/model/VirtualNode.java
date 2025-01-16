package com.project.model;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "virtual_node_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VirtualNode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "virtual_node_name")
	private String virtualNodeName;

	@ManyToOne
	private VirtualNode parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VirtualNode> children;

	@ManyToOne
	@JoinColumn(name = "connection_group_id", referencedColumnName = "id")
	private ConnectionGroup connectionGroupId;
}
