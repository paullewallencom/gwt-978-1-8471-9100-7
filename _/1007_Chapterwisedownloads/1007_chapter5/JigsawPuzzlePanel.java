package com.packtpub.gwtbook.samples.client.panels;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventPreview;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;
import com.packtpub.gwtbook.samples.client.SamplePanel;

public class JigsawPuzzlePanel extends SamplePanel implements
    MouseListener {

  private AbsolutePanel workPanel = new AbsolutePanel();

  private boolean inDrag;

  private int xOffset;

  private int yOffset;

  public static SinkInfo init() {
    return new SinkInfo("Jigsaw Puzzle",
        "Piece Mona Lisa together by dragging and rearranging the images.") {
      public SamplePanel createInstance() {
        return new JigsawPuzzlePanel();
      }
    };
  }

  public JigsawPuzzlePanel() {
    HorizontalPanel infoPanel = new HorizontalPanel();
    infoPanel
        .add(new HTML(
            "<div class='infoProse'>This example demonstrates the use of dragging to move things around and place them anywhere in the window. It is easy to forget that you are actually doing this in a web browser !</div>"));

    Image monalisa = new Image("images/monalisa_face1_8.jpg");
    monalisa.addMouseListener(this);
    workPanel.add(monalisa, 60, 20);
    monalisa = new Image("images/monalisa_face1_7.jpg");
    monalisa.addMouseListener(this);
    workPanel.add(monalisa, 60, 125);
    monalisa = new Image("images/monalisa_face1_2.jpg");
    monalisa.addMouseListener(this);
    workPanel.add(monalisa, 60, 230);

    monalisa = new Image("images/monalisa_face1_3.jpg");
    monalisa.addMouseListener(this);
    workPanel.add(monalisa, 170, 20);
    monalisa = new Image("images/monalisa_face1_4.jpg");
    monalisa.addMouseListener(this);
    workPanel.add(monalisa, 170, 125);
    monalisa = new Image("images/monalisa_face1_1.jpg");
    monalisa.addMouseListener(this);
    workPanel.add(monalisa, 170, 230);

    monalisa = new Image("images/monalisa_face1_6.jpg");
    monalisa.addMouseListener(this);
    workPanel.add(monalisa, 280, 20);
    monalisa = new Image("images/monalisa_face1_9.jpg");
    monalisa.addMouseListener(this);
    workPanel.add(monalisa, 280, 125);
    monalisa = new Image("images/monalisa_face1_5.jpg");
    monalisa.addMouseListener(this);
    workPanel.add(monalisa, 280, 230);

    DOM.addEventPreview(new EventPreview() {
      public boolean onEventPreview(Event event) {
        switch (DOM.eventGetType(event)) {
        case Event.ONMOUSEDOWN:
        case Event.ONMOUSEMOVE:
        case Event.ONMOUSEUP:
          DOM.eventPreventDefault(event);
        }
        return true;
      }
    });

    DockPanel workPane = new DockPanel();
    workPane.add(infoPanel, DockPanel.NORTH);
    workPane.add(workPanel, DockPanel.CENTER);
    workPane.setCellHeight(workPanel, "100%");
    workPane.setCellWidth(workPanel, "100%");
    initWidget(workPane);
  }

  public void onShow() {
  }

  public void onMouseDown(Widget source, int x, int y) {
    DOM.setCapture(source.getElement());
    xOffset = x;
    yOffset = y;
    inDrag = true;
  }

  public void onMouseMove(Widget source, int x, int y) {
    if (inDrag) {
      int xAbs = x + source.getAbsoluteLeft() - 135;
      int yAbs = y + source.getAbsoluteTop() - 120;
      ((AbsolutePanel) source.getParent()).setWidgetPosition(source, xAbs
          - xOffset, yAbs - yOffset);
    }
  }

  public void onMouseUp(Widget source, int x, int y) {
    DOM.releaseCapture(source.getElement());
    inDrag = false;
  }

  public void onMouseEnter(Widget sender) {

  }

  public void onMouseLeave(Widget sender) {

  }
}