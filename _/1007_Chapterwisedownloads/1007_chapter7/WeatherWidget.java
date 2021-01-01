package com.packtpub.gwtbook.widgets.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.packtpub.gwtbook.samples.client.util.Rico;

public class WeatherWidget extends Composite implements ChangeListener {
	private VerticalPanel imagePanel = new VerticalPanel();

	private HorizontalPanel tempPanel = new HorizontalPanel();

	private VerticalPanel tempHolderPanel = new VerticalPanel();

	private HorizontalPanel currentPanel = new HorizontalPanel();

	private HorizontalPanel windPanel = new HorizontalPanel();

	private HorizontalPanel windPanel2 = new HorizontalPanel();

	private HorizontalPanel atmospherePanel = new HorizontalPanel();

	private HorizontalPanel atmospherePanel2 = new HorizontalPanel();

	private HorizontalPanel astronomyPanel = new HorizontalPanel();

	private HorizontalPanel geoPanel = new HorizontalPanel();

	private Image image = new Image();

	private Label currentTemp = new Label("");

	private Label currentCondition = new Label("");

	private Label windChill = new Label("");

	private Label windDirection = new Label("");

	private Label windSpeed = new Label("");

	private Label atmHumidity = new Label("");

	private Label atmVisibility = new Label("");

	private Label atmpressure = new Label("");

	private Label atmRising = new Label("");

	private Label astSunrise = new Label("");

	private Label astSunset = new Label("");

	private Label latitude = new Label("");

	private Label longitude = new Label("");

	private Label windLabel = new Label("Wind");

	private Label astLabel = new Label("Astronomy");

	private Label atmLabel = new Label("Atmosphere");

	private Label geoLabel = new Label("Geography");

	private Label cityLabel = new Label("");

	private TextBox zipCodeInput = new TextBox();

	final WeatherServiceAsync weatherService = (WeatherServiceAsync) GWT
			.create(WeatherService.class);

	public WeatherWidget() {
		VerticalPanel workPanel = new VerticalPanel();
		initWidget(workPanel);
		this.sinkEvents(Event.ONCHANGE);

		ServiceDefTarget endpoint = (ServiceDefTarget) weatherService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "weather");

		DOM.setAttribute(workPanel.getElement(), "id", "weatherDiv");
		DOM.setAttribute(workPanel.getElement(), "className", "weatherHolder");
		Rico.corner(workPanel.getElement(), null);

		image.setStyleName("weatherImage");
		imagePanel.add(image);
		currentCondition.setStyleName("currentCondition");
		imagePanel.add(currentCondition);
		currentPanel.add(imagePanel);

		currentTemp.setStyleName("currentTemp");
		tempPanel.add(currentTemp);
		tempPanel.add(new HTML("<div class='degrees'>&deg;</div>"));
		tempHolderPanel.add(tempPanel);
		cityLabel.setStyleName("city");
		tempHolderPanel.add(cityLabel);
		currentPanel.add(tempHolderPanel);

		windDirection.setStyleName("currentMeasurementsDegrees");
		windChill.setStyleName("currentMeasurementsDegrees");
		windSpeed.setStyleName("currentMeasurements");

		windPanel.add(windDirection);
		windPanel.add(new HTML("<div class='measurementDegrees'>&deg;</div>"));
		windPanel.add(windSpeed);
		windPanel2.add(windChill);
		windPanel2.add(new HTML("<div class='measurementDegrees'>&deg;</div>"));

		atmHumidity.setStyleName("currentMeasurements");
		atmpressure.setStyleName("currentMeasurements");
		atmVisibility.setStyleName("currentMeasurements");
		atmRising.setStyleName("currentMeasurements");

		atmospherePanel.add(atmHumidity);
		atmospherePanel.add(atmVisibility);
		atmospherePanel2.add(atmpressure);

		astSunrise.setStyleName("currentMeasurements");
		astSunset.setStyleName("currentMeasurements");

		astronomyPanel.add(astSunrise);
		astronomyPanel.add(astSunset);

		latitude.setStyleName("currentMeasurements");
		longitude.setStyleName("currentMeasurements");

		geoPanel.add(latitude);
		geoPanel.add(longitude);

		windLabel.setStyleName("conditionPanel");
		atmLabel.setStyleName("conditionPanel");
		astLabel.setStyleName("conditionPanel");
		geoLabel.setStyleName("conditionPanel");

		workPanel.add(currentPanel);
		workPanel.add(windLabel);
		workPanel.add(windPanel);
		workPanel.add(windPanel2);
		workPanel.add(atmLabel);
		workPanel.add(atmospherePanel);
		workPanel.add(atmospherePanel2);
		workPanel.add(astLabel);
		workPanel.add(astronomyPanel);
		workPanel.add(geoLabel);
		workPanel.add(geoPanel);
		HorizontalPanel bufferPanel = new HorizontalPanel();
		bufferPanel.add(new HTML("<div>&nbsp;</div>"));
		HorizontalPanel zipCodeInputPanel = new HorizontalPanel();
		Label zipCodeInputLabel = new Label("Enter Zip:");
		zipCodeInputLabel.setStyleName("zipCodeLabel");
		zipCodeInput.setStyleName("zipCodeInput");
		zipCodeInput.setText("90210");
		zipCodeInput.addChangeListener(this);
		zipCodeInputPanel.add(zipCodeInputLabel);
		zipCodeInputPanel.add(zipCodeInput);
		workPanel.add(zipCodeInputPanel);
		workPanel.add(bufferPanel);
		getAndRenderWeather(zipCodeInput.getText());
	}

	private void getAndRenderWeather(String zipCode) {
		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object result) {
				Weather weather = (Weather) result;
				if (weather.getError().length() > 0) {
					Window.alert(weather.getError());
					return;
				}
				image.setUrl(weather.getImageUrl());
				currentTemp.setText(weather.getCurrentTemp());
				currentCondition.setText(weather.getCurrentCondition());
				windDirection.setText("Direction : " + weather.getDirection());
				windChill.setText("Chill : " + weather.getChill());
				windSpeed.setText("Speed : " + weather.getSpeed() + " mph");
				atmHumidity.setText("Humidity : " + weather.getHumidity()
						+ " %");
				atmpressure.setText("Barometer : "
						+ weather.getPressure()
						+ " in and "
						+ getBarometerState(Integer.parseInt(weather
								.getRising())));
				atmVisibility.setText("Visibility : "
						+ (Integer.parseInt(weather.getVisibility()) / 100)
						+ " mi");
				astSunrise.setText("Sunrise : " + weather.getSunrise());
				astSunset.setText("Sunset : " + weather.getSunset());
				latitude.setText("Latitude : " + weather.getLatitude());
				longitude.setText("Longitude : " + weather.getLongitude());
				cityLabel
						.setText(weather.getCity() + ", " + weather.getState());
			}

			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		};
		weatherService.getWeather(zipCode, callback);
	}

	private String getBarometerState(int rising) {
		if (rising == 0) {
			return "steady";
		} else if (rising == 1) {
			return "rising";
		} else {
			return "falling";
		}
	}

	public void onChange(Widget sender) {
		if (zipCodeInput.getText().length() == 5) {
			getAndRenderWeather(zipCodeInput.getText());
		}
	}
}