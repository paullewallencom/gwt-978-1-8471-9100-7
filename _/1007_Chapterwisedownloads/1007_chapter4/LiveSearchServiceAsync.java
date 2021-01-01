package com.packtpub.gwtbook.samples.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LiveSearchServiceAsync {
	public void getCompletionItems(String itemToMatch, AsyncCallback callback);
}
