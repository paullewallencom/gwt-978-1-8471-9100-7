package com.packtpub.gwtbook.hellogwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RandomQuoteServiceAsync {
    public void getQuote(AsyncCallback callback);
}
