package com.packtpub.gwtbook.samples.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DynamicListsServiceAsync {
	public void getManufacturers(AsyncCallback callback);

	public void getBrands(String manufacturer, AsyncCallback callback);

	public void getModels(String manufacturer, String brand,
			AsyncCallback callback);
}
