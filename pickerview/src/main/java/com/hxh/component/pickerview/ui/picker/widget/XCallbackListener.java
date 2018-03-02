package com.hxh.component.pickerview.ui.picker.widget;


public abstract class XCallbackListener {

	protected abstract void callback(Object... obj);

	public void call(Object... obj) {
			callback(obj);
	}

}
