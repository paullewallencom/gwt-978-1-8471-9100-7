package com.packtpub.gwtbook.hellogwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HelloGWT implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    final Label quoteText = new Label();
    quoteText.setStyleName("quoteLabel");

    // create the service
    final RandomQuoteServiceAsync quoteService = (RandomQuoteServiceAsync) GWT
        .create(RandomQuoteService.class);

    // Specify the URL at which our service implementation is running.
    ServiceDefTarget endpoint = (ServiceDefTarget) quoteService;
    endpoint.setServiceEntryPoint(GWT.getModuleBaseURL()  + "quotes");


    Timer timer = new Timer() {
      public void run() {

        // create an async callback to handle the result.
        AsyncCallback callback = new AsyncCallback() {
          public void onSuccess(Object result) {
            // display the retrieved quote in the label
            quoteText.setText((String) result);
          }

          public void onFailure(Throwable caught) {
            // display the error text if we cant get quote
            quoteText.setText("Failed to get a quote.");
          }
        };

        // Make the call.
        quoteService.getQuote(callback);
      }
    };

    // Schedule the timer to run once every second
    timer.scheduleRepeating(1000);

    RootPanel.get("slot1").add(quoteText);
  }
}