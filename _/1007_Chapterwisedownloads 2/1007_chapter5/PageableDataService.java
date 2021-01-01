package com.packtpub.gwtbook.samples.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

public interface PageableDataService extends RemoteService {

  public List getCustomerData(int startIndex, int numItems );
}
