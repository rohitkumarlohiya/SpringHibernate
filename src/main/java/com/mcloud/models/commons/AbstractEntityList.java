package com.mcloud.models.commons;

import java.util.List;

import com.mcloud.entities.AbstractEntity;

public class AbstractEntityList {
	private long totalRecords;
	public List<AbstractEntity> entityList;

	public AbstractEntityList(long totalRecords, List<AbstractEntity> entityList) {
		super();
		this.totalRecords = totalRecords;
		this.entityList = entityList;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<AbstractEntity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<AbstractEntity> entityList) {
		this.entityList = entityList;
	}
}
