package com.packtpub.gwtbook.samples.client;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.google.gwt.junit.client.GWTTestCase;
import com.packtpub.gwtbook.samples.client.panels.AutoFormFillPanelTest;
import com.packtpub.gwtbook.samples.client.panels.AutoFormFillServiceTest;

/**
 * GWT JUnit tests must extend GWTTestCase.
 */
public class SamplesTestSuite extends GWTTestCase {

	/**
	 * Must refer to a valid module that sources this class.
	 */
	public String getModuleName() {
		return "com.packtpub.gwtbook.samples.Samples";
	}

	public static Test suite() {
		TestSuite samplesTestSuite = new TestSuite();
		samplesTestSuite.addTestSuite(AutoFormFillServiceTest.class);
		samplesTestSuite.addTestSuite(AutoFormFillPanelTest.class);
		return samplesTestSuite;
	}

}
