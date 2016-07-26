package edu.softserveinc.healthbody.dto;

import java.util.List;

public class RoleDTO {

	private String idRole;
	private String name;
	private String description;
	private List<UserDTO> users;

	public RoleDTO() { }
	
	public RoleDTO(final String idRole, final String name, final String description, final List<UserDTO> users) {
		this.idRole = idRole;
		this.name = name;
		this.description = description;
		this.users = users;
	}
	
	public String getIdRole() {
		return idRole;
	}

	public final String getName() {
		return name;
	}

	public final String getDescription() {
		return description;
	}

	public final List<UserDTO> getUsers() {
		return users;
	}
}