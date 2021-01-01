package com.packtpub.gwtbook.widgets.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface WeatherService extends RemoteService {

	public Weather getWeather(String zipCode);

}
