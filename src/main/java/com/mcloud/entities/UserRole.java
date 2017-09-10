package com.mcloud.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "USER_ROLE")
public class UserRole extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", length = 11, nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AUTO_USER_ID", nullable = false)
	@JsonBackReference
	private CustomUser autoUser;

	@Column(name = "ROLE", nullable = false, length = 45)
	private String role;

	public UserRole() {
	}

	public String getRole() {
		return role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomUser getAutoUser() {
		return autoUser;
	}

	public void setAutoUser(CustomUser autoUser) {
		this.autoUser = autoUser;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
