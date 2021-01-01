package com.packtpub.gwtbook.samples.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;

public interface DynamicListsService extends RemoteService {
	public ArrayList getManufacturers();

	public ArrayList getBrands(String manufacturer);

	public ArrayList getModels(String manufacturer, String brand);
}
