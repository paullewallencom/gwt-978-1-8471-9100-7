package com.packtpub.gwtbook.samples.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AutoFormFillServiceAsync {
	public void getFormInfo(String formKey, AsyncCallback callback);
}
