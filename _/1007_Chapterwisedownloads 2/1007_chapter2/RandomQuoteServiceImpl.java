package com.packtpub.gwtbook.hellogwt.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.packtpub.gwtbook.hellogwt.client.RandomQuoteService;

public class RandomQuoteServiceImpl extends RemoteServiceServlet implements
		RandomQuoteService {
	private Random randomizer = new Random();

	private static final long serialVersionUID = -1502084255979334403L;

	private static List quotes = new ArrayList();

	static {
		quotes.add("No great thing is created suddenly — Epictetus");
		quotes.add("Well done is better than well said — Benjamin Franklin");
		quotes.add("No wind favors he who has no destined port — Montaigne");
		quotes.add("Sometimes even to live is an act of courage — Seneca");
		quotes.add("Know thyself — Socrates");
	}

	public String getQuote() {
		return (String) quotes.get(randomizer.nextInt(4));
	}
}
