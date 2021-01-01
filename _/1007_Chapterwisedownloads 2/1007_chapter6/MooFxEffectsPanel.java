package com.packtpub.gwtbook.samples.client.panels;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.packtpub.gwtbook.samples.client.SamplePanel;
import com.packtpub.gwtbook.samples.client.util.MooFx;

public class MooFxEffectsPanel extends SamplePanel {

	private VerticalPanel workPanel = new VerticalPanel();

	private HTML opacityBox = new HTML(
			"<div class='moofxBox'><div id=\"opacitybox\"><p class=\"text\">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p></div></div>");

	private HTML heightBox = new HTML(
			"<div class='moofxBox'><div id=\"heightbox\"><p class=\"text\">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p></div></div>");

	private HTML widthBox = new HTML(
			"<div class='moofxBox'><div id=\"widthbox\"><p class=\"text\">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p></div></div>");

	public static SinkInfo init() {
		return new SinkInfo("Moo.fx", "Moo.fx effects.") {
			public SamplePanel createInstance() {
				return new MooFxEffectsPanel();
			}
		};
	}

	private Element widthBoxElement;

	private Element heightBoxElement;

	private Element opacityBoxElement;

	public MooFxEffectsPanel() {
		HorizontalPanel infoPanel = new HorizontalPanel();
		infoPanel
				.add(new HTML(
						"<div class='infoProse'>Use cool Moo.fx effects in your GWT application.</div>"));

		Button opacityButton = new Button("Toggle Opacity");
		opacityButton.setStyleName("moofxButton");
		workPanel.add(opacityButton);
		workPanel.add(opacityBox);

		DeferredCommand.add(new Command() {
			public void execute() {
				opacityBoxElement = MooFx.opacity(DOM
						.getElementById("opacitybox"));
			}
		});

		opacityButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				MooFx.toggleOpacity(opacityBoxElement);
			}
		});

		Button heightButton = new Button("Toggle Height");
		heightButton.setStyleName("moofxButton");
		workPanel.add(heightButton);
		workPanel.add(heightBox);

		DeferredCommand.add(new Command() {
			public void execute() {
				heightBoxElement = MooFx.height(
						DOM.getElementById("heightbox"), "duration:2500");
			}
		});

		heightButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				MooFx.toggleHeight(heightBoxElement);
			}
		});

		Button widthButton = new Button("Toggle Width");
		widthButton.setStyleName("moofxButton");
		workPanel.add(widthButton);
		workPanel.add(widthBox);

		DeferredCommand.add(new Command() {
			public void execute() {
				widthBoxElement = MooFx.width(DOM.getElementById("widthbox"),
						"duration:2000");
			}
		});

		widthButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				MooFx.toggleWidth(widthBoxElement);
			}
		});

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