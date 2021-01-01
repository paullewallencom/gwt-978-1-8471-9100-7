package com.packtpub.gwtbook.samples.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.RemoteService;

public interface AutoFormFillService extends RemoteService {
	public HashMap getFormInfo(String formKey);
}
