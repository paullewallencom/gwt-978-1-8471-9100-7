package com.packtpub.gwtbook.samples.client.panels;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.packtpub.gwtbook.samples.client.DynamicListsService;
import com.packtpub.gwtbook.samples.client.DynamicListsServiceAsync;
import com.packtpub.gwtbook.samples.client.SamplePanel;

public class DynamicListsPanel extends SamplePanel {

	final DynamicListsServiceAsync dynamicListsService = (DynamicListsServiceAsync) GWT
			.create(DynamicListsService.class);

	Grid manufacturers = new Grid(5, 1);

	Grid brands = new Grid(5, 1);

	Grid models = new Grid(5, 1);

	int selectedManufacturer = 0;

	public static SinkInfo init() {
		return new SinkInfo("Dynamic Lists",
				"Dynamically fill list with info when you select an item in each list") {
			public SamplePanel createInstance() {
				return new DynamicListsPanel();
			}
		};
	}

	public DynamicListsPanel() {
		ServiceDefTarget endpoint = (ServiceDefTarget) dynamicListsService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "dynamiclists");

		HorizontalPanel workPanel = new HorizontalPanel();

		manufacturers.addTableListener(new TableListener() {
			public void onCellClicked(SourcesTableEvents sender, int row,
					int cell) {
				clearSelections(manufacturers, false);
				clearSelections(brands, true);
				clearSelections(models, true);
				selectedManufacturer = row;
				manufacturers.getCellFormatter().setStyleName(row, cell,
						"dynamicLists-Selected");
				AsyncCallback callback = new AsyncCallback() {
					public void onSuccess(Object result) {
						brands.clear();
						int row = 0;
						for (Iterator iter = ((ArrayList) result).iterator(); iter
								.hasNext();) {
							brands.setText(row++, 0, (String) iter.next());
						}
					}

					public void onFailure(Throwable caught) {
						Window
								.alert("Error calling the Dynamic Lists service to get the brands."
										+ caught.getMessage());
					}
				};
				dynamicListsService.getBrands(manufacturers.getText(row, cell),
						callback);
			}
		});

		brands.addTableListener(new TableListener() {

			public void onCellClicked(SourcesTableEvents sender, int row,
					int cell) {
				clearSelections(brands, false);
				clearSelections(models, true);
				brands.getCellFormatter().setStyleName(row, cell,
						"dynamicLists-Selected");
				AsyncCallback callback = new AsyncCallback() {
					public void onSuccess(Object result) {
						models.clear();
						int row = 0;
						for (Iterator iter = ((ArrayList) result).iterator(); iter
								.hasNext();) {
							models.setText(row++, 0, (String) iter.next());
						}
					}

					public void onFailure(Throwable caught) {
						Window
								.alert("Error calling the Dynamic Lists service to get the models."
										+ caught.getMessage());
					}
				};
				dynamicListsService.getModels(manufacturers.getText(
						selectedManufacturer, cell), brands.getText(row, cell),
						callback);

			}
		});

		models.addTableListener(new TableListener() {
			public void onCellClicked(SourcesTableEvents sender, int row,
					int cell) {
				clearSelections(models, false);
				models.getCellFormatter().setStyleName(row, cell,
						"dynamicLists-Selected");
			}
		});

		VerticalPanel itemPanel = new VerticalPanel();
		Label itemLabel = new Label("Select Manufacturer");
		itemLabel.setStyleName("dynamicLists-Label");
		itemPanel.add(itemLabel);
		itemPanel.add(manufacturers);
		workPanel.add(itemPanel);

		itemPanel = new VerticalPanel();
		itemLabel = new Label("Select Brand");
		itemLabel.setStyleName("dynamicLists-Label");
		itemPanel.add(itemLabel);
		itemPanel.add(brands);
		workPanel.add(itemPanel);

		itemPanel = new VerticalPanel();
		itemLabel = new Label("Models");
		itemLabel.setStyleName("dynamicLists-Label");
		itemPanel.add(itemLabel);
		itemPanel.add(models);
		workPanel.add(itemPanel);

		manufacturers.setStyleName("dynamicLists-List");
		brands.setStyleName("dynamicLists-List");
		models.setStyleName("dynamicLists-List");

		workPanel.setStyleName("dynamicLists-Panel");
		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object result) {
				int row = 0;
				for (Iterator iter = ((ArrayList) result).iterator(); iter
						.hasNext();) {
					manufacturers.setText(row++, 0, (String) iter.next());
				}
			}

			public void onFailure(Throwable caught) {
				Window
						.alert("Error calling the Dynamic Lists service to get the manufacturers."
								+ caught.getMessage());
			}
		};
		dynamicListsService.getManufacturers(callback);

		HorizontalPanel infoPanel = new HorizontalPanel();
		infoPanel
				.add(new HTML(
						"<div class='infoProse'>This example demonstrates the creation of dynamic lists. You select an item from the first list and corresponding items are retrieved asynchronously from the server to display in the second list. You can then select an item in the second list to get another selection of items. In this particular example, we retrieve car brand by manufacturer, and then get and display the spefic models for the selected brand.</div>"));

		DockPanel workPane = new DockPanel();
		workPane.add(infoPanel, DockPanel.NORTH);
		workPane.add(workPanel, DockPanel.CENTER);
		workPane.setCellHeight(workPanel, "100%");
		workPane.setCellWidth(workPanel, "100%");
		initWidget(workPane);
	}

	public void clearSelections(Grid grid, boolean clearData) {
		for (int i = 0; i < grid.getRowCount(); i++) {
			if (clearData) {
				grid.setText(i, 0, " ");
			}
			grid.getCellFormatter().setStyleName(i, 0,
					"dynamicLists-Unselected");
		}
	}

	public void onShow() {
	}

}