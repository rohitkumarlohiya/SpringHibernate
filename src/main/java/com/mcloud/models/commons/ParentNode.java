package com.mcloud.models.commons;

public class ParentNode {
	private long id;
	private String name;

	public ParentNode(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public ParentNode() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
