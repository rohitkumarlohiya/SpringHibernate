package com.mcloud.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@MappedSuperclass
@JsonAutoDetect
public class AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "CD", nullable = false)
	private Date cd;

	@Column(name = "CB", nullable = false)
	private Long cb;

	@Column(name = "UD", nullable = true)
	private Date ud;

	@Column(name = "UB", nullable = true)
	private Long ub;

	@Column(name = "ENABLED", nullable = false)
	private Boolean enabled;

	public AbstractEntity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCd() {
		return cd;
	}

	public void setCd(Date cd) {
		this.cd = cd;
	}

	public Long getCb() {
		return cb;
	}

	public void setCb(Long cb) {
		this.cb = cb;
	}

	public Date getUd() {
		return ud;
	}

	public void setUd(Date ud) {
		this.ud = ud;
	}

	public Long getUb() {
		return ub;
	}

	public void setUb(Long ub) {
		this.ub = ub;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
