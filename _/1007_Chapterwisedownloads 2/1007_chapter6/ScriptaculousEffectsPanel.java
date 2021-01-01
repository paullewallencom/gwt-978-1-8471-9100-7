package com.packtpub.gwtbook.samples.client.panels;

import org.gwtwidgets.client.wrap.Effect;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.packtpub.gwtbook.samples.client.SamplePanel;

public class ScriptaculousEffectsPanel extends SamplePanel {

	private HorizontalPanel workPanel = new HorizontalPanel();

	private Grid grid = new Grid(2, 4);

	private Image packtlogo1 = new Image("images/packtlogo.jpg");

	private Image packtlogo2 = new Image("images/packtlogo.jpg");

	private Image packtlogo3 = new Image("images/packtlogo.jpg");

	private Image packtlogo4 = new Image("images/packtlogo.jpg");

	private Image packtlogo5 = new Image("images/packtlogo.jpg");

	private Image packtlogo6 = new Image("images/packtlogo.jpg");

	private Image packtlogo7 = new Image("images/packtlogo.jpg");

	private Image packtlogo8 = new Image("images/packtlogo.jpg");

	private Button fadeButton = new Button("fade");

	private Button puffButton = new Button("puff");

	private Button shakeButton = new Button("shake");

	private Button growButton = new Button("grow");

	private Button shrinkButton = new Button("shrink");

	private Button pulsateButton = new Button("pulsate");

	private Button blindUpButton = new Button("blindup");

	private Button blindDownButton = new Button("blinddown");

	public static SinkInfo init() {
		return new SinkInfo("Scriptaculous", "Scriptaculous effects.") {
			public SamplePanel createInstance() {
				return new ScriptaculousEffectsPanel();
			}
		};
	}

	public ScriptaculousEffectsPanel() {
		HorizontalPanel infoPanel = new HorizontalPanel();
		infoPanel.add(new HTML(
				"<div class='infoProse'>Use nifty scriptaculous effects in GWT applications.</div>"));
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {
				grid.getCellFormatter().setStyleName(i, j, "scriptaculousGrid");
			}
		}

		VerticalPanel gridCellPanel = new VerticalPanel();
		gridCellPanel.add(packtlogo1);
		packtlogo1.setStyleName("scriptaculousImage");
		fadeButton.setStyleName("scriptaculousButton");
		gridCellPanel.add(fadeButton);
		fadeButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				Effect.fade(packtlogo1);
			}
		});
		grid.setWidget(0, 0, gridCellPanel);

		gridCellPanel = new VerticalPanel();
		packtlogo3.setStyleName("scriptaculousImage");
		shakeButton.setStyleName("scriptaculousButton");
		gridCellPanel.add(packtlogo3);
		gridCellPanel.add(shakeButton);
		shakeButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				Effect.shake(packtlogo3);
			}
		});
		grid.setWidget(0, 1, gridCellPanel);

		gridCellPanel = new VerticalPanel();
		packtlogo4.setStyleName("scriptaculousImage");
		growButton.setStyleName("scriptaculousButton");
		gridCellPanel.add(packtlogo4);
		gridCellPanel.add(growButton);
		growButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				Effect.grow(packtlogo4);
			}
		});
		grid.setWidget(0, 2, gridCellPanel);

		gridCellPanel = new VerticalPanel();
		packtlogo8.setStyleName("scriptaculousImage");
		blindUpButton.setStyleName("scriptaculousButton");
		gridCellPanel.add(packtlogo8);
		gridCellPanel.add(blindUpButton);
		blindUpButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				Effect.blindUp(packtlogo8);
			}
		});
		grid.setWidget(0, 3, gridCellPanel);

		gridCellPanel = new VerticalPanel();
		packtlogo2.setStyleName("scriptaculousImage");
		puffButton.setStyleName("scriptaculousButton");
		gridCellPanel.add(packtlogo2);
		gridCellPanel.add(puffButton);
		puffButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				Effect.puff(packtlogo2);
			}
		});
		grid.setWidget(1, 0, gridCellPanel);

		gridCellPanel = new VerticalPanel();
		packtlogo5.setStyleName("scriptaculousImage");
		shrinkButton.setStyleName("scriptaculousButton");
		gridCellPanel.add(packtlogo5);
		gridCellPanel.add(shrinkButton);
		shrinkButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				Effect.shrink(packtlogo5);
			}
		});
		grid.setWidget(1, 1, gridCellPanel);

		gridCellPanel = new VerticalPanel();
		packtlogo6.setStyleName("scriptaculousImage");
		pulsateButton.setStyleName("scriptaculousButton");
		gridCellPanel.add(packtlogo6);
		gridCellPanel.add(pulsateButton);
		pulsateButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				Effect.pulsate(packtlogo6);
			}
		});
		grid.setWidget(1, 2, gridCellPanel);

		gridCellPanel = new VerticalPanel();
		packtlogo7.setStyleName("scriptaculousImage");
		blindDownButton.setStyleName("scriptaculousButton");
		gridCellPanel.add(packtlogo7);
		gridCellPanel.add(blindDownButton);
		blindDownButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				Effect.blindDown(packtlogo7);
			}
		});
		grid.setWidget(1, 3, gridCellPanel);

		workPanel.setStyleName("scriptaculouspanel");
		workPanel.add(grid);
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