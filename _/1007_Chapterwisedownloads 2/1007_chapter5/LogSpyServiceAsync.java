package com.packtpub.gwtbook.samples.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LogSpyServiceAsync {
  public void getAllLogEntries(AsyncCallback callback);

  public void getNextLogEntries(AsyncCallback callback);
}
