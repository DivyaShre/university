package com.university.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class RoleEntity {
	@Id
	@GeneratedValue
	private long id;
	private String role;
	private String roleName;
	private int status;
	private long createdBy;
	private Timestamp createdTS;
	private long updatedBy;
	private Timestamp updateTS;

	public RoleEntity() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedTS() {
		return createdTS;
	}

	public void setCreatedTS(Timestamp createdTS) {
		this.createdTS = createdTS;
	}

	public long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdateTS() {
		return updateTS;
	}

	public void setUpdateTS(Timestamp updateTS) {
		this.updateTS = updateTS;
	}
}
