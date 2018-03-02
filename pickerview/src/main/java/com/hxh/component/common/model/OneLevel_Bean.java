package com.hxh.component.common.model;

import java.util.ArrayList;

/**
 * 标题: ProvinceModel.java
 * 作者: hxh
 * 日期: 2018/2/3 11:50
 * 描述: 省
 *  【省】   一对多个  【市】  一对多个  【区】
 */
public class OneLevel_Bean {
	private String name;//省的名字
	private String id;//一级数据的id（区域码）
	private ArrayList<TwoLevel_Bean> data;

	private String extra;//一级数据的额外携带数据

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<TwoLevel_Bean> getData() {
		return data;
	}

	public void setData(ArrayList<TwoLevel_Bean> data) {
		this.data = data;
	}

	@Override
	public boolean equals(Object obj) {
		OneLevel_Bean bean = obj instanceof OneLevel_Bean ? ((OneLevel_Bean) obj) : null;

		return bean.getName().equals(this.name);
	}

	@Override
	public String toString() {
		return name;
	}
}
