package com.packtpub.gwtbook.samples.client.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.packtpub.gwtbook.samples.client.SamplePanel;
import com.packtpub.gwtbook.samples.client.util.I18NSamplesConstants;

public class I18nPanel extends SamplePanel {

	private VerticalPanel workPanel = new VerticalPanel();

	private Label welcome = new Label();

	public static SinkInfo init() {
		return new SinkInfo("i18n", "Internationalization.") {
			public SamplePanel createInstance() {
				return new I18nPanel();
			}
		};
	}

	public I18nPanel() {
		HorizontalPanel infoPanel = new HorizontalPanel();
		infoPanel
				.add(new HTML(
						"<div class='infoProse'>Use internationalization in your GWT application to display text in the native language.</div>"));

		I18NSamplesConstants myConstants = (I18NSamplesConstants) GWT
				.create(I18NSamplesConstants.class);
		welcome.setText(myConstants.welcome());
		welcome.setStyleName("flagLabel");
		Image flag = new Image("images/" + myConstants.flag_image());
		flag.setStyleName("flag");
		workPanel.add(flag);
		workPanel.add(welcome);

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