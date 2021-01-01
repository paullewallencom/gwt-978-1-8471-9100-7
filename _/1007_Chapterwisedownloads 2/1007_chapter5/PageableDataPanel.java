package com.packtpub.gwtbook.samples.client.panels;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.packtpub.gwtbook.samples.client.PageableDataService;
import com.packtpub.gwtbook.samples.client.PageableDataServiceAsync;
import com.packtpub.gwtbook.samples.client.SamplePanel;

public class PageableDataPanel extends SamplePanel {
	private FlexTable customerTable = new FlexTable();

	private Button backButton = new Button("<<<");

	private Button forwardButton = new Button(">>");

	private String[] customerTableHeaders = new String[] { "Name", "City",
			"Zip Code", "State", "Phone" };

	private int startIndex = 1;

	final PageableDataServiceAsync pageableDataService = (PageableDataServiceAsync) GWT
			.create(PageableDataService.class);

	public static SinkInfo init() {
		return new SinkInfo("Pageable Lists",
				"Page through a long list of data") {
			public SamplePanel createInstance() {
				return new PageableDataPanel();
			}
		};
	}

	public PageableDataPanel() {

		VerticalPanel workPanel = new VerticalPanel();

		customerTable.setWidth(500 + "px");
		customerTable.setStyleName("pageableData-Table");
		customerTable.setBorderWidth(1);
		customerTable.setCellPadding(4);
		customerTable.setCellSpacing(1);

		customerTable.setText(0, 0, customerTableHeaders[0]);
		customerTable.setText(0, 1, customerTableHeaders[1]);
		customerTable.setText(0, 2, customerTableHeaders[2]);
		customerTable.setText(0, 3, customerTableHeaders[3]);
		customerTable.setText(0, 4, customerTableHeaders[4]);

		RowFormatter rowFormatter = customerTable.getRowFormatter();
		rowFormatter.setStyleName(0, "pageableData-TableHeader");

		HorizontalPanel innerNavBar = new HorizontalPanel();
		innerNavBar.setStyleName("pageableData-NavBar");
		innerNavBar.setSpacing(8);
		backButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				if (startIndex >= 10) {
					startIndex -= 10;
					update(startIndex);
				}
			}

		});
		innerNavBar.add(backButton);

		forwardButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				if (startIndex < 40) {
					startIndex += 10;
					update(startIndex);
				}
			}

		});

		innerNavBar.add(forwardButton);

		workPanel.add(innerNavBar);

		HorizontalPanel infoPanel = new HorizontalPanel();
		infoPanel
				.add(new HTML(
						"<div class='infoProse'>Create lists that can be paged by fetching data from the server on demand when we go forward and backward in the list.</div>"));
		workPanel.add(customerTable);
		DockPanel workPane = new DockPanel();
		workPane.add(infoPanel, DockPanel.NORTH);
		workPane.add(workPanel, DockPanel.CENTER);
		workPane.setCellHeight(workPanel, "100%");
		workPane.setCellWidth(workPanel, "100%");

		initWidget(workPane);

		ServiceDefTarget endpoint = (ServiceDefTarget) pageableDataService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "pageabledata");

		update(1);
	}

	private void update(int startIndex) {
		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object result) {
				ArrayList customerData = (ArrayList) result;
				int row = 1;
				clearTable();
				for (Iterator iter = customerData.iterator(); iter.hasNext();) {
					ArrayList customer = (ArrayList) iter.next();
					customerTable.setText(row, 0, (String) customer.get(0));
					customerTable.setText(row, 1, (String) customer.get(1));
					customerTable.setText(row, 2, (String) customer.get(2));
					customerTable.setText(row, 3, (String) customer.get(3));
					customerTable.setText(row, 4, (String) customer.get(4));
					row++;
				}
			}

			public void onFailure(Throwable caught) {
				Window.alert("Error when invoking the pageable data service :"
						+ caught.getMessage());
			}
		};

		pageableDataService.getCustomerData(startIndex, 10, callback);
	}

	private void clearTable() {
		for (int row = 1; row < customerTable.getRowCount(); row++) {
			for (int col = 0; col < customerTable.getCellCount(row); col++) {
				customerTable.clearCell(row, col);
			}
		}
	}

	public void onShow() {
	}

}