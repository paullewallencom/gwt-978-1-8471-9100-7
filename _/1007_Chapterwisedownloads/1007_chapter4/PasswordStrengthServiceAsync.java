package com.packtpub.gwtbook.samples.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PasswordStrengthServiceAsync {
	public void checkStrength(String password, AsyncCallback callback);
}
