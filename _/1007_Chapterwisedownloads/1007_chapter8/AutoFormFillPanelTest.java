package com.packtpub.gwtbook.samples.client.panels;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;

/**
 * GWT JUnit tests must extend GWTTestCase.
 */
public class AutoFormFillPanelTest extends GWTTestCase {

	/**
	 * Must refer to a valid module that sources this class.
	 */
	public String getModuleName() {
		return "com.packtpub.gwtbook.samples.Samples";
	}

	public void testEverything() {
		final AutoFormFillPanel autoFormFillPanel = new AutoFormFillPanel();
		assertEquals("Customer ID : ", autoFormFillPanel.getCustIDLbl()
				.getText());
		assertEquals("autoFormItem-Label", autoFormFillPanel.getCustIDLbl()
				.getStyleName());

		assertEquals("Address : ", autoFormFillPanel.getAddressLbl().getText());
		assertEquals("autoFormItem-Label", autoFormFillPanel.getAddressLbl()
				.getStyleName());

		assertEquals("City : ", autoFormFillPanel.getCityLbl().getText());
		assertEquals("autoFormItem-Label", autoFormFillPanel.getCityLbl()
				.getStyleName());

		assertEquals("First Name : ", autoFormFillPanel.getFirstNameLbl()
				.getText());
		assertEquals("autoFormItem-Label", autoFormFillPanel.getFirstNameLbl()
				.getStyleName());

		assertEquals("Last Name : ", autoFormFillPanel.getLastNameLbl()
				.getText());
		assertEquals("autoFormItem-Label", autoFormFillPanel.getLastNameLbl()
				.getStyleName());

		assertEquals("Phone Number : ", autoFormFillPanel.getPhoneLbl()
				.getText());
		assertEquals("autoFormItem-Label", autoFormFillPanel.getPhoneLbl()
				.getStyleName());

		assertEquals("State : ", autoFormFillPanel.getStateLbl().getText());
		assertEquals("autoFormItem-Label", autoFormFillPanel.getStateLbl()
				.getStyleName());

		assertEquals("Zip Code : ", autoFormFillPanel.getZipLbl().getText());
		assertEquals("autoFormItem-Label", autoFormFillPanel.getZipLbl()
				.getStyleName());

		autoFormFillPanel.simulateCustIDChanged("1111");

		Timer timer = new Timer() {
			public void run() {
				assertEquals("Joe", autoFormFillPanel.getFirstName().getText());
				assertEquals("Customer", autoFormFillPanel.getLastName()
						.getText());
				assertEquals("123 peachtree street", autoFormFillPanel
						.getAddress().getText());
				assertEquals("Atlanta", autoFormFillPanel.getCity().getText());
				assertEquals("GA", autoFormFillPanel.getState().getText());
				assertEquals("30339", autoFormFillPanel.getZip().getText());
				assertEquals("770-123-4567", autoFormFillPanel.getPhone()
						.getText());

				finishTest();
			}
		};

		delayTestFinish(2000);

		timer.schedule(100);
	}
}
