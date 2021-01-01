package com.packtpub.gwtbook.samples.client.panels;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.packtpub.gwtbook.samples.client.LiveSearchService;
import com.packtpub.gwtbook.samples.client.LiveSearchServiceAsync;
import com.packtpub.gwtbook.samples.client.SamplePanel;

public class LiveSearchPanel extends SamplePanel {
	final LiveSearchServiceAsync liveSearchService = (LiveSearchServiceAsync) GWT
			.create(LiveSearchService.class);

	public FlexTable liveResultsPanel = new FlexTable();

	public TextBox searchText = new TextBox();

	public static SinkInfo init() {
		return new SinkInfo("Live Search", "Live Search using GWT.") {
			public SamplePanel createInstance() {
				return new LiveSearchPanel();
			}
		};
	}

	public LiveSearchPanel() {
		ServiceDefTarget endpoint = (ServiceDefTarget) liveSearchService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "livesearch");

		VerticalPanel workPanel = new VerticalPanel();
		searchText.setStyleName("liveSearch-TextBox");
		searchText.addKeyboardListener(new KeyboardListener() {

			public void onKeyDown(Widget sender, char keyCode, int modifiers) {
				for (int i = 0; i < liveResultsPanel.getRowCount(); i++) {
					liveResultsPanel.removeRow(i);
				}
			}

			public void onKeyPress(Widget sender, char keyCode, int modifiers) {
			}

			public void onKeyUp(Widget sender, char keyCode, int modifiers) {

				for (int i = 0; i < liveResultsPanel.getRowCount(); i++) {
					liveResultsPanel.removeRow(i);
				}

				if (searchText.getText().length() > 0) {
					AsyncCallback callback = new AsyncCallback() {
						public void onSuccess(Object result) {
							ArrayList resultItems = (ArrayList) result;
							int row = 0;
							for (Iterator iter = resultItems.iterator(); iter
									.hasNext();) {
								liveResultsPanel.setText(row++, 0,
										(String) iter.next());
							}
						}

						public void onFailure(Throwable caught) {
							Window
									.alert("Error invoking the live search service."
											+ caught.getMessage());
						}
					};

					liveSearchService.getCompletionItems(searchText.getText(),
							callback);
				}
			}
		});

		liveResultsPanel.setStyleName("liveSearch-Results");
		HorizontalPanel infoPanel = new HorizontalPanel();
		infoPanel
				.add(new HTML(
						"<div class='infoProse'>Type the first few letters of the name of a fruit in the text box below. A list of fruits with names starting with the typed letters will be displayed. The list is retrieved from the server asynchronously. This is nice AJAX pattern for providing user-friendly search functionality in an application.</div>"));
		workPanel.add(searchText);
		workPanel.add(liveResultsPanel);
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