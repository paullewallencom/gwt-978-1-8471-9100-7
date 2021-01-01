package com.packtpub.gwtbook.samples.client.panels;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.packtpub.gwtbook.samples.client.SamplePanel;
import com.packtpub.gwtbook.widgets.client.CalendarWidget;

public class CalendarWidgetPanel extends SamplePanel {

	private VerticalPanel workPanel = new VerticalPanel();

	public static SinkInfo init() {
		return new SinkInfo("Calendar Widget", "Custom Calendar Widget") {
			public SamplePanel createInstance() {
				return new CalendarWidgetPanel();
			}
		};
	}

	public CalendarWidgetPanel() {
		HorizontalPanel infoPanel = new HorizontalPanel();
		infoPanel
				.add(new HTML(
						"<div class='infoProse'>Click on the navigation buttons to go forward and backward through the calendar. When you want to come back to todays date, click on the Today button.</div>"));
		CalendarWidget calendar = new CalendarWidget();
		workPanel.add(calendar);
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