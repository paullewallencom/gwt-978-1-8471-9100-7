package com.packtpub.gwtbook.samples.client.panels;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.packtpub.gwtbook.samples.client.SamplePanel;
import com.packtpub.gwtbook.widgets.client.WeatherWidget;

public class WeatherWidgetPanel extends SamplePanel {

	private VerticalPanel workPanel = new VerticalPanel();

	public static SinkInfo init() {
		return new SinkInfo("Weather widget", "Weather widget") {
			public SamplePanel createInstance() {
				return new WeatherWidgetPanel();
			}
		};
	}

	public WeatherWidgetPanel() {
		HorizontalPanel infoPanel = new HorizontalPanel();
		infoPanel
				.add(new HTML("<div class='infoProse'>A custom widget for viewing the weather conditions for a US city by entering the zipcode in the textbox.</div>"));

		WeatherWidget weather = new WeatherWidget();
		workPanel.add(weather);
		DockPanel workPane = new DockPanel();
		workPane.add(infoPanel, DockPanel.NORTH);
		workPane.add(workPanel, DockPanel.CENTER);
		workPane.setCellHeight(workPanel, "100%");
		workPane.setCellWidth(workPanel, "100%");
		initWidget(workPane);
	}

	public void onShow() {
	}

}