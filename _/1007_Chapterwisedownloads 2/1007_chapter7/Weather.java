package com.packtpub.gwtbook.widgets.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Weather implements IsSerializable {
	private String chill = "";

	private String city = "";

	private String currentCondition = "";

	private String currentTemp = "";

	private String direction = "";

	private String error = "";

	private String humidity = "";

	private String imageUrl = "";

	private String latitude = "";

	private String longitude = "";

	private String pressure = "";

	private String rising = "";

	private String speed = "";

	private String state = "";

	private String sunrise = "";

	private String sunset = "";

	private String visibility = "";

	private String zipCode = "";

	public Weather() {
	}

	public Weather(String zipCode, String chill, String direction,
			String speed, String humidity, String visibility, String pressure,
			String rising, String sunrise, String sunset, String latitude,
			String longitude, String currentCondition, String currentTemp,
			String imageUrl, String city, String state) {
		this.zipCode = zipCode;
		this.chill = chill;
		this.direction = direction;
		this.speed = speed;
		this.humidity = humidity;
		this.visibility = visibility;
		this.pressure = pressure;
		this.rising = rising;
		this.sunrise = sunrise;
		this.sunset = sunset;
		this.latitude = latitude;
		this.longitude = longitude;
		this.currentCondition = currentCondition;
		this.currentTemp = currentTemp;
		this.imageUrl = imageUrl;
		this.city = city;
		this.state = state;
	}

public String getChill() {
	return chill;
}

public void setChill(String chill) {
	this.chill = chill;
}

public String getCity() {
	return city;
}

public void setCity(String city) {
	this.city = city;
}

public String getCurrentCondition() {
	return currentCondition;
}

public void setCurrentCondition(String currentCondition) {
	this.currentCondition = currentCondition;
}

public String getCurrentTemp() {
	return currentTemp;
}

public void setCurrentTemp(String currentTemp) {
	this.currentTemp = currentTemp;
}

public String getDirection() {
	return direction;
}

public void setDirection(String direction) {
	this.direction = direction;
}

public String getError() {
	return error;
}

public void setError(String error) {
	this.error = error;
}

public String getHumidity() {
	return humidity;
}

public void setHumidity(String humidity) {
	this.humidity = humidity;
}

public String getImageUrl() {
	return imageUrl;
}

public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}

public String getLatitude() {
	return latitude;
}

public void setLatitude(String latitude) {
	this.latitude = latitude;
}

public String getLongitude() {
	return longitude;
}

public void setLongitude(String longitude) {
	this.longitude = longitude;
}

public String getPressure() {
	return pressure;
}

public void setPressure(String pressure) {
	this.pressure = pressure;
}

public String getRising() {
	return rising;
}

public void setRising(String rising) {
	this.rising = rising;
}

public String getSpeed() {
	return speed;
}

public void setSpeed(String speed) {
	this.speed = speed;
}

public String getState() {
	return state;
}

public void setState(String state) {
	this.state = state;
}

public String getSunrise() {
	return sunrise;
}

public void setSunrise(String sunrise) {
	this.sunrise = sunrise;
}

public String getSunset() {
	return sunset;
}

public void setSunset(String sunset) {
	this.sunset = sunset;
}

public String getVisibility() {
	return visibility;
}

public void setVisibility(String visibility) {
	this.visibility = visibility;
}

public String getZipCode() {
	return zipCode;
}

public void setZipCode(String zipCode) {
	this.zipCode = zipCode;
}


}
