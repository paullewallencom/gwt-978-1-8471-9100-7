package com.packtpub.gwtbook.samples.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.packtpub.gwtbook.samples.client.PasswordStrengthService;

public class PasswordStrengthServiceImpl extends RemoteServiceServlet implements
		PasswordStrengthService {

	private int STRONG = 9;

	private int MEDIUM = 6;

	private int WEAK = 3;

	public int checkStrength(String password) {
		if (password.length() <= 4) {
			return WEAK;
		} else if (password.length() < 8) {
			return MEDIUM;
		} else {
			return STRONG;
		}
	}
}
