package com.packtpub.gwtbook.samples.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.packtpub.gwtbook.samples.client.SamplePanel.SinkInfo;
import com.packtpub.gwtbook.samples.client.panels.AutoFormFillPanel;
import com.packtpub.gwtbook.samples.client.panels.CalendarWidgetPanel;
import com.packtpub.gwtbook.samples.client.panels.ColorSelectorPanel;
import com.packtpub.gwtbook.samples.client.panels.CreateXMLPanel;
import com.packtpub.gwtbook.samples.client.panels.DynamicListsPanel;
import com.packtpub.gwtbook.samples.client.panels.EditableTreeNodesPanel;
import com.packtpub.gwtbook.samples.client.panels.FlickrEditableLabelPanel;
import com.packtpub.gwtbook.samples.client.panels.I18nPanel;
import com.packtpub.gwtbook.samples.client.panels.InfoPanel;
import com.packtpub.gwtbook.samples.client.panels.JigsawPuzzlePanel;
import com.packtpub.gwtbook.samples.client.panels.LiveSearchPanel;
import com.packtpub.gwtbook.samples.client.panels.LogSpyPanel;
import com.packtpub.gwtbook.samples.client.panels.MooFxEffectsPanel;
import com.packtpub.gwtbook.samples.client.panels.PageableDataPanel;
import com.packtpub.gwtbook.samples.client.panels.ParseXMLPanel;
import com.packtpub.gwtbook.samples.client.panels.PasswordStrengthPanel;
import com.packtpub.gwtbook.samples.client.panels.RoundedCornersPanel;
import com.packtpub.gwtbook.samples.client.panels.ScriptaculousEffectsPanel;
import com.packtpub.gwtbook.samples.client.panels.SortableTablesPanel;
import com.packtpub.gwtbook.samples.client.panels.StickyNotesPanel;
import com.packtpub.gwtbook.samples.client.panels.WeatherWidgetPanel;

public class Samples implements EntryPoint, HistoryListener {

	private SinkInfo curInfo;

	private SamplePanel curSink;

	private HTML description = new HTML();

	private SamplePanelsList list = new SamplePanelsList();

	private DockPanel panel = new DockPanel();

	private DockPanel sinkContainer;

	public void onHistoryChanged(String token) {
		// Find the SinkInfo associated with the history context. If one is
		// found, show it (It may not be found, for example, when the user mis-
		// types a URL, or on startup, when the first context will be "").
		SinkInfo info = list.find(token);
		if (info == null) {
			showInfo();
			return;
		}
		show(info, false);
	}

	public void onModuleLoad() {
		// Load all the sinks.
		loadSinks();

		// Put the sink list on the left, and add the outer dock panel to the
		// root.
		sinkContainer = new DockPanel();
		sinkContainer.setStyleName("ks-Sink");

		VerticalPanel vp = new VerticalPanel();
		vp.setWidth("100%");
		vp.add(description);
		vp.add(sinkContainer);

		description.setStyleName("ks-Info");

		panel.add(list, DockPanel.WEST);
		panel.add(vp, DockPanel.CENTER);

		panel.setCellVerticalAlignment(list, HasAlignment.ALIGN_TOP);
		panel.setCellWidth(vp, "100%");

		History.addHistoryListener(this);
		RootPanel.get().add(panel);

		// Show the initial screen.
		String initToken = History.getToken();
		if (initToken.length() > 0)
			onHistoryChanged(initToken);
		else
			showInfo();
	}

	public void show(SinkInfo info, boolean affectHistory) {
		// Don't bother re-displaying the existing sink. This can be an issue
		// in practice, because when the history context is set, our
		// onHistoryChanged() handler will attempt to show the currently-visible
		// sink.
		if (info == curInfo)
			return;
		curInfo = info;

		// Remove the old sink from the display area.
		if (curSink != null) {
			curSink.onHide();
			sinkContainer.remove(curSink);
		}

		// Get the new sink instance, and display its description in the
		// sink list.
		curSink = info.getInstance();
		list.setSinkSelection(info.getName());
		description.setHTML(info.getDescription());

		// If affectHistory is set, create a new item on the history stack. This
		// will ultimately result in onHistoryChanged() being called. It will
		// call
		// show() again, but nothing will happen because it will request the
		// exact
		// same sink we're already showing.
		if (affectHistory)
			History.newItem(info.getName());

		// Display the new sink.
		sinkContainer.add(curSink, DockPanel.CENTER);
		sinkContainer.setCellWidth(curSink, "100%");
		sinkContainer.setCellHeight(curSink, "100%");
		sinkContainer.setCellVerticalAlignment(curSink, DockPanel.ALIGN_TOP);
		curSink.onShow();
	}

	private void loadSinks() {
		list.addSink(InfoPanel.init());
		list.addSink(LiveSearchPanel.init());
		list.addSink(PasswordStrengthPanel.init());
		list.addSink(AutoFormFillPanel.init());
		list.addSink(SortableTablesPanel.init());
		list.addSink(DynamicListsPanel.init());
		list.addSink(FlickrEditableLabelPanel.init());
		list.addSink(PageableDataPanel.init());
		list.addSink(LogSpyPanel.init());
		list.addSink(EditableTreeNodesPanel.init());
		list.addSink(StickyNotesPanel.init());
		list.addSink(JigsawPuzzlePanel.init());
		list.addSink(MooFxEffectsPanel.init());
		list.addSink(RoundedCornersPanel.init());
		list.addSink(ColorSelectorPanel.init());
		list.addSink(ScriptaculousEffectsPanel.init());
		list.addSink(CalendarWidgetPanel.init());
		list.addSink(WeatherWidgetPanel.init());
		list.addSink(I18nPanel.init());
		list.addSink(CreateXMLPanel.init());
		list.addSink(ParseXMLPanel.init());
	}

	private void showInfo() {
		show(list.find("Info"), false);
	}
}
