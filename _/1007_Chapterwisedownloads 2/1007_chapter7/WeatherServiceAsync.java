package com.packtpub.gwtbook.widgets.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WeatherServiceAsync {
	public void getWeather(String zipCode, AsyncCallback callback);
}
