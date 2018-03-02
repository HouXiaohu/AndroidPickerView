package com.hxh.component.common.model;

/**
 * 区
 */
public class ThreeLevel_Bean {
	private String name;
	private String id;//三级数据的id

	private String extra;//额外数据



	public ThreeLevel_Bean(String name, String id) {
		this.name = name;
		this.id = id;
	}

	public ThreeLevel_Bean(String name, String id, String extra) {
		this.name = name;
		this.id = id;
		this.extra = extra;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ThreeLevel_Bean() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	@Override
	public String toString() {
		return name;
	}
}
