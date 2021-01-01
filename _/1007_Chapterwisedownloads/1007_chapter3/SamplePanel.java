package com.packtpub.gwtbook.samples.client;

import com.google.gwt.user.client.ui.Composite;

/**
 * A 'sink' is a single panel of the kitchen sink. They are meant to be lazily
 * instantiated so that the application doesn't pay for all of them on startup.
 */
public abstract class SamplePanel extends Composite {

	/**
	 * Encapsulated information about a sink. Each sink is expected to have a
	 * static <code>init()</code> method that will be called by the kitchen sink
	 * on startup.
	 */
	public abstract static class SinkInfo {
		private SamplePanel instance;

		private String name, description;

		public SinkInfo(String name, String desc) {
			this.name = name;
			description = desc;
		}

		public abstract SamplePanel createInstance();

		public String getDescription() {
			return description;
		}

		public final SamplePanel getInstance() {
			if (instance != null)
				return instance;
			return (instance = createInstance());
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * Called just before this sink is hidden.
	 */
	public void onHide() {
	}

	/**
	 * Called just after this sink is shown.
	 */
	public void onShow() {
	}
}
