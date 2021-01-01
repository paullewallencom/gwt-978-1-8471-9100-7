package com.packtpub.gwtbook.samples.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PageableDataServiceAsync {
  public void getCustomerData(int startIndex, int numItems , AsyncCallback callback);
}
