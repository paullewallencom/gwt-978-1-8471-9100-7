package com.packtpub.gwtbook.samples.client.panels;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.packtpub.gwtbook.samples.client.AutoFormFillService;
import com.packtpub.gwtbook.samples.client.AutoFormFillServiceAsync;
import com.packtpub.gwtbook.samples.client.SamplePanel;

public class AutoFormFillPanel extends SamplePanel {

	final AutoFormFillServiceAsync autoFormFillService = (AutoFormFillServiceAsync) GWT
			.create(AutoFormFillService.class);

	private TextBox custID = new TextBox();

	private TextBox firstName = new TextBox();

	private TextBox lastName = new TextBox();

	private TextBox address = new TextBox();

	private TextBox zip = new TextBox();

	private TextBox phone = new TextBox();

	private TextBox city = new TextBox();

	private TextBox state = new TextBox();

	private Label custIDLbl = new Label("Customer ID : ");

	private Label firstNameLbl = new Label("First Name : ");

	private Label lastNameLbl = new Label("Last Name : ");

	private Label addressLbl = new Label("Address : ");

	private Label zipLbl = new Label("Zip Code : ");

	private Label phoneLbl = new Label("Phone Number : ");

	private Label cityLbl = new Label("City : ");

	private Label stateLbl = new Label("State : ");

	public static SinkInfo init() {
		return new SinkInfo("Auto Form Fill",
				"Automatically fill forms with values from server.") {
			public SamplePanel createInstance() {
				return new AutoFormFillPanel();
			}
		};
	}

	public AutoFormFillPanel() {
		ServiceDefTarget endpoint = (ServiceDefTarget) autoFormFillService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "autoformfill");

		VerticalPanel workPanel = new VerticalPanel();

		custID.addKeyboardListener(new KeyboardListener() {
			public void onKeyDown(Widget sender, char keyCode, int modifiers) {
			}

			public void onKeyPress(Widget sender, char keyCode, int modifiers) {
			}

			public void onKeyUp(Widget sender, char keyCode, int modifiers) {
				if (custID.getText().length() > 0) {
					AsyncCallback callback = new AsyncCallback() {
						public void onSuccess(Object result) {
							setValues((HashMap) result);
						}

						public void onFailure(Throwable caught) {
							Window
									.alert("Error while calling the Auto Form Fill service."
											+ caught.getMessage());
						}
					};

					autoFormFillService.getFormInfo(custID.getText(), callback);
				} else {
					clearValues();
				}
			}
		});

		custID.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {
				if (custID.getText().length() > 0) {
					AsyncCallback callback = new AsyncCallback() {
						public void onSuccess(Object result) {
							setValues((HashMap) result);
						}

						public void onFailure(Throwable caught) {
							Window
									.alert("Error while calling the auto form fill service."
											+ caught.getMessage());
						}
					};
					autoFormFillService.getFormInfo(custID.getText(), callback);
				} else {
					clearValues();
				}

			}
		});

		HorizontalPanel itemPanel = new HorizontalPanel();
		itemPanel.setStyleName("autoFormItem-Panel");
		custIDLbl.setStyleName("autoFormItem-Label");
		itemPanel.add(custIDLbl);
		custID.setStyleName("autoFormItem-Textbox");
		itemPanel.add(custID);
		workPanel.add(itemPanel);
		itemPanel = new HorizontalPanel();
		itemPanel.setStyleName("autoFormItem-Panel");
		firstNameLbl.setStyleName("autoFormItem-Label");
		itemPanel.add(firstNameLbl);
		firstName.setStyleName("autoFormItem-Textbox");
		itemPanel.add(firstName);
		workPanel.add(itemPanel);
		itemPanel = new HorizontalPanel();
		itemPanel.setStyleName("autoFormItem-Panel");
		lastNameLbl.setStyleName("autoFormItem-Label");
		itemPanel.add(lastNameLbl);
		lastName.setStyleName("autoFormItem-Textbox");
		itemPanel.add(lastName);
		workPanel.add(itemPanel);
		itemPanel = new HorizontalPanel();
		itemPanel.setStyleName("autoFormItem-Panel");
		addressLbl.setStyleName("autoFormItem-Label");
		itemPanel.add(addressLbl);
		address.setStyleName("autoFormItem-Textbox");
		itemPanel.add(address);
		workPanel.add(itemPanel);
		itemPanel = new HorizontalPanel();
		itemPanel.setStyleName("autoFormItem-Panel");
		cityLbl.setStyleName("autoFormItem-Label");
		itemPanel.add(cityLbl);
		city.setStyleName("autoFormItem-Textbox");
		itemPanel.add(city);
		workPanel.add(itemPanel);
		itemPanel = new HorizontalPanel();
		itemPanel.setStyleName("autoFormItem-Panel");
		stateLbl.setStyleName("autoFormItem-Label");
		itemPanel.add(stateLbl);
		state.setStyleName("autoFormItem-Textbox");
		itemPanel.add(state);
		workPanel.add(itemPanel);
		itemPanel = new HorizontalPanel();
		itemPanel.setStyleName("autoFormItem-Panel");
		zipLbl.setStyleName("autoFormItem-Label");
		itemPanel.add(zipLbl);
		zip.setStyleName("autoFormItem-Textbox");
		itemPanel.add(zip);
		workPanel.add(itemPanel);
		itemPanel = new HorizontalPanel();
		itemPanel.setStyleName("autoFormItem-Panel");
		phoneLbl.setStyleName("autoFormItem-Label");
		itemPanel.add(phoneLbl);
		phone.setStyleName("autoFormItem-Textbox");
		itemPanel.add(phone);
		workPanel.add(itemPanel);

		HorizontalPanel infoPanel = new HorizontalPanel();
		infoPanel
				.add(new HTML(
						"<div class='infoProse'>This example demonstrates how to automatically fill a form by retrieving the data from the server asynchronously. Start typing a customer ID in the provided field, and corresponding values for that customer are retrieved asynchronously from the server and the form filled for you.</div>"));

		DockPanel workPane = new DockPanel();
		workPane.add(infoPanel, DockPanel.NORTH);
		workPane.add(workPanel, DockPanel.CENTER);
		workPane.setCellHeight(workPanel, "100%");
		workPane.setCellWidth(workPanel, "100%");

		initWidget(workPane);
	}

	public void onShow() {
	}

	private void setValues(HashMap values) {
		if (values.size() > 0) {
			firstName.setText((String) values.get("first name"));
			lastName.setText((String) values.get("last name"));
			address.setText((String) values.get("address"));
			city.setText((String) values.get("city"));
			state.setText((String) values.get("state"));
			zip.setText((String) values.get("zip"));
			phone.setText((String) values.get("phone"));
		} else {
			clearValues();
		}
	}

	private void clearValues() {
		firstName.setText(" ");
		lastName.setText(" ");
		address.setText(" ");
		city.setText(" ");
		state.setText(" ");
		zip.setText(" ");
		phone.setText(" ");
	}

	public TextBox getAddress() {
		return address;
	}

	public TextBox getCity() {
		return city;
	}

	public TextBox getCustID() {
		return custID;
	}

	public TextBox getFirstName() {
		return firstName;
	}

	public TextBox getLastName() {
		return lastName;
	}

	public TextBox getPhone() {
		return phone;
	}

	public TextBox getState() {
		return state;
	}

	public TextBox getZip() {
		return zip;
	}

	public Label getAddressLbl() {
		return addressLbl;
	}

	public Label getCityLbl() {
		return cityLbl;
	}

	public Label getCustIDLbl() {
		return custIDLbl;
	}

	public Label getFirstNameLbl() {
		return firstNameLbl;
	}

	public Label getLastNameLbl() {
		return lastNameLbl;
	}

	public Label getPhoneLbl() {
		return phoneLbl;
	}

	public Label getStateLbl() {
		return stateLbl;
	}

	public Label getZipLbl() {
		return zipLbl;
	}

	public void simulateCustIDChanged(String custIDValue) {
		if (custIDValue.length() > 0) {
			AsyncCallback callback = new AsyncCallback() {
				public void onSuccess(Object result) {
					setValues((HashMap) result);
				}

				public void onFailure(Throwable caught) {
					Window.alert("Error calling the Auto Form Fill service"
							+ caught.getMessage());
				}
			};

			custID.setText(custIDValue);
			autoFormFillService.getFormInfo(custIDValue, callback);
		} else {
			clearValues();
		}
	}
}