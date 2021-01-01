package com.packtpub.gwtbook.samples.client.panels;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.packtpub.gwtbook.samples.client.AutoFormFillService;
import com.packtpub.gwtbook.samples.client.AutoFormFillServiceAsync;

/**
 * GWT JUnit tests must extend GWTTestCase.
 */
public class AutoFormFillServiceTest extends GWTTestCase {

	/**
	 * Must refer to a valid module that sources this class.
	 */
	public String getModuleName() {
		return "com.packtpub.gwtbook.samples.Samples";
	}

	/**
	 * Add as many tests as you like.
	 */
	public void testService() {
		final AutoFormFillServiceAsync autoFormFillService = (AutoFormFillServiceAsync) GWT
				.create(AutoFormFillService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) autoFormFillService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "autoformfill");

		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object result) {
				HashMap formValues = (HashMap) result;

				assertEquals("Joe", formValues.get("first name"));
				assertEquals("Customer", formValues.get("last name"));
				assertEquals("123 peachtree street", formValues.get("address"));
				assertEquals("Atlanta", formValues.get("city"));
				assertEquals("GA", formValues.get("state"));
				assertEquals("30339", formValues.get("zip"));
				assertEquals("770-123-4567", formValues.get("phone"));

				finishTest();
			}

			public void onFailure(Throwable caught) {
				fail("Failed to get the form info values");
			}
		};

		delayTestFinish(2000);

		autoFormFillService.getFormInfo("1111", callback);
	}

}
