package com.packtpub.gwtbook.samples.client.panels;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListenerCollection;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.packtpub.gwtbook.samples.client.SamplePanel;

public class StickyNotesPanel extends SamplePanel implements ClickListener {

  private HorizontalPanel workPanel = new HorizontalPanel();

  private Button createNote = new Button("Create Note");

  private TextBox noteTitle = new TextBox();

  private int noteLeft = 300;

  private int noteTop = 170;

  private int increment = 10;

  public static SinkInfo init() {
    return new SinkInfo("Sticky Notes", "Sticky Notes in your browser.") {
      public SamplePanel createInstance() {
        return new StickyNotesPanel();
      }
    };
  }

  public StickyNotesPanel() {
    HorizontalPanel infoPanel = new HorizontalPanel();
    infoPanel
        .add(new HTML(
            "<div class='infoProse'>Create sticky notes and drag them around to position any where in your browser window. Go ahead and try it !</div>"));

    createNote.addClickListener(this);
    createNote.setStyleName("notesButton");
    workPanel.add(createNote);
    noteTitle.setStyleName("notesTitle");
    workPanel.add(noteTitle);
    DockPanel workPane = new DockPanel();
    workPane.add(infoPanel, DockPanel.NORTH);
    workPane.add(workPanel, DockPanel.CENTER);
    workPane.setCellHeight(workPanel, "100%");
    workPane.setCellWidth(workPanel, "100%");

    initWidget(workPane);
  }

  public void onShow() {
  }

  public class StickyNote extends DialogBox {
    public StickyNote(String title) {
      super();
      if (title.length() == 0) {
        setText("New Note");
      } else {
        setText(title);
      }
      TextArea text = new TextArea();
      text.setText("Type your note here");
      text.setHeight("80px");
      setWidget(text);
      setHeight("100px");
      setWidth("100px");

      setStyleName(text.getElement(), "notesText", true);
      setStyleName("notesPanel");
    }

    public boolean onEventPreview(Event event) {
      int type = DOM.eventGetType(event);
      switch (type) {
      case Event.ONKEYDOWN: {
        return onKeyDownPreview((char) DOM.eventGetKeyCode(event),
            KeyboardListenerCollection.getKeyboardModifiers(event));
      }
      case Event.ONKEYUP: {
        return onKeyUpPreview((char) DOM.eventGetKeyCode(event),
            KeyboardListenerCollection.getKeyboardModifiers(event));
      }
      case Event.ONKEYPRESS: {
        return onKeyPressPreview((char) DOM.eventGetKeyCode(event),
            KeyboardListenerCollection.getKeyboardModifiers(event));
      }
      }
      return true;
    }
  }

  public void onClick(Widget sender) {
    StickyNote note = new StickyNote(noteTitle.getText());
    note.setPopupPosition(noteLeft + increment, noteTop + increment);
    increment = increment + 40;
    note.show();
  }
}