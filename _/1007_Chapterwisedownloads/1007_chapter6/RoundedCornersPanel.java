package com.packtpub.gwtbook.samples.client.panels;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.packtpub.gwtbook.samples.client.SamplePanel;
import com.packtpub.gwtbook.samples.client.util.Rico;

public class RoundedCornersPanel extends SamplePanel {

	private VerticalPanel workPanel = new VerticalPanel();

	private Grid grid = new Grid(3, 2);

	private Label lbl1 = new Label("Label with rounded corners.");

	private Label lbl2 = new Label("Label with only the top corners rounded.");

	private Label lbl3 = new Label(
			"Label with only the bottom corners rounded.");

	private Label lbl4 = new Label(
			"Label with only the bottom right corner rounded.");

	private Label lbl5 = new Label("Label with compact rounded corners.");

	private Label lbl6 = new Label("Label with rounded corners and red border.");

	public static SinkInfo init() {
		return new SinkInfo("Rounded Corners", "Rounded Corners.") {
			public SamplePanel createInstance() {
				return new RoundedCornersPanel();
			}
		};
	}

	public RoundedCornersPanel() {
		HorizontalPanel infoPanel = new HorizontalPanel();
		infoPanel
				.add(new HTML(
						"<div class='infoProse'>Labels with different kinds of rounded corners.</div>"));

		lbl1.setStyleName("roundedCornersLabel");
		Rico.corner(lbl1);
		grid.setWidget(0, 0, lbl1);

		lbl2.setStyleName("roundedCornersLabel");
		Rico.corner(lbl2, "corners:\"top\"");
		grid.setWidget(0, 1, lbl2);

		lbl3.setStyleName("roundedCornersLabel");
		Rico.corner(lbl3, "corners:\"bottom\"");
		grid.setWidget(1, 0, lbl3);

		lbl4.setStyleName("roundedCornersLabel");
		Rico.corner(lbl4, "corners:\"br\"");
		grid.setWidget(1, 1, lbl4);

		lbl5.setStyleName("roundedCornersLabel");
		Rico.corner(lbl5, "compact:true");
		grid.setWidget(2, 0, lbl5);

		lbl6.setStyleName("roundedCornersLabel");
		Rico.corner(lbl6, "border: 'red'");
		grid.setWidget(2, 1, lbl6);

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