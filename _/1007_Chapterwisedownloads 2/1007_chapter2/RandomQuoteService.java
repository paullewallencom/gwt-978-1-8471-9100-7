package com.packtpub.gwtbook.hellogwt.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface RandomQuoteService extends RemoteService {
    public String getQuote();

}
