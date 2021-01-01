package com.packtpub.gwtbook.samples.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;

public interface LogSpyService extends RemoteService {

  public ArrayList getAllLogEntries();

  public ArrayList getNextLogEntries();
}
