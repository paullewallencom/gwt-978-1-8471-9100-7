package com.packtpub.gwtbook.samples.client.panels;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.packtpub.gwtbook.samples.client.LogSpyService;
import com.packtpub.gwtbook.samples.client.LogSpyServiceAsync;
import com.packtpub.gwtbook.samples.client.SamplePanel;

public class LogSpyPanel extends SamplePanel {
	final LogSpyServiceAsync logSpyService = (LogSpyServiceAsync) GWT
			.create(LogSpyService.class);

	public ListBox logSpyList = new ListBox();

	public TextBox monitoringInterval = new TextBox();

	public Label monitoringLabel = new Label("Monitoring Interval :");

	public Button startMonitoring = new Button("Start");

	public Button stopMonitoring = new Button("Stop");

	private boolean isMonitoring = false;

	private HorizontalPanel intervalPanel = new HorizontalPanel();

	private HorizontalPanel startStopPanel = new HorizontalPanel();

	private Timer timer;

	public static SinkInfo init() {
		return new SinkInfo("Log Spy", "Log spy.") {
			public SamplePanel createInstance() {
				return new LogSpyPanel();
			}
		};
	}

	public LogSpyPanel() {
		ServiceDefTarget endpoint = (ServiceDefTarget) logSpyService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "logspy");

		logSpyList.setVisibleItemCount(8);
		monitoringInterval.setText("1000");
		stopMonitoring.setEnabled(false);
		intervalPanel.setStyleName("logSpyPanel");
		startStopPanel.setStyleName("logSpyStartStopPanel");
		monitoringLabel.setStyleName("logSpyLabel");
		monitoringInterval.setStyleName("logSpyTextbox");
		VerticalPanel workPanel = new VerticalPanel();

		startMonitoring.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				if (!isMonitoring) {
					timer = new Timer() {
						public void run() {
							AsyncCallback callback = new AsyncCallback() {
								public void onSuccess(Object result) {
									ArrayList resultItems = (ArrayList) result;
									for (Iterator iter = resultItems.iterator(); iter
											.hasNext();) {
										logSpyList.insertItem(((String) iter
												.next()), 0);
										logSpyList.setSelectedIndex(0);
									}
								}

								public void onFailure(Throwable caught) {
									Window
											.alert("Error while invoking the log spy service "
													+ caught.getMessage());
								}
							};

							logSpyService.getNextLogEntries(callback);
						}
					};

					timer.scheduleRepeating(Integer.parseInt(monitoringInterval
							.getText()));
					isMonitoring = true;
					startMonitoring.setEnabled(false);
					stopMonitoring.setEnabled(true);
				}
			}
		});

		stopMonitoring.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				if (isMonitoring) {
					timer.cancel();
					isMonitoring = false;
					startMonitoring.setEnabled(true);
					stopMonitoring.setEnabled(false);
				}
			}
		});

		logSpyList.setStyleName("logSpy-List");
		HorizontalPanel infoPanel = new HorizontalPanel();
		infoPanel
				.add(new HTML(
						"<div class='infoProse'>View a log file live as entries are written to it. This is similar in concept to the unix utility tail. The new entries are retrieved and added in real time to the top of the list. You can start and stop the monitoring, and set the interval in milliseconds for how often you want to check the file for new entries.</div>"));
		intervalPanel.add(monitoringLabel);
		intervalPanel.add(monitoringInterval);
		startStopPanel.add(startMonitoring);
		startStopPanel.add(stopMonitoring);
		workPanel.add(intervalPanel);
		workPanel.add(startStopPanel);
		workPanel.add(logSpyList);
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