package com.packtpub.gwtbook.samples.client.panels;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.TreeListener;
import com.google.gwt.user.client.ui.Widget;
import com.packtpub.gwtbook.samples.client.SamplePanel;

public class EditableTreeNodesPanel extends SamplePanel {
  private AbsolutePanel workPanel = new AbsolutePanel();

  private DockPanel workPane = new DockPanel();

  private Tree editableTree = new Tree();

  private TreeItem currentSelection = new TreeItem();

  TextBox textbox = new TextBox();

  public static SinkInfo init() {
    return new SinkInfo("Editable Tree", "Edit tree nodes inline.") {
      public SamplePanel createInstance() {
        return new EditableTreeNodesPanel();
      }
    };
  }

  public EditableTreeNodesPanel() {
    HorizontalPanel infoPanel = new HorizontalPanel();
    infoPanel
        .add(new HTML(
            "<div class='infoProse'>This sample shows a tree whose nodes can be edited by clicking on a tree node.</div>"));
    initTree();

    editableTree.addTreeListener(new TreeListener() {
      public void onTreeItemSelected(TreeItem item) {
        if (textbox.isAttached()) {
          if (!currentSelection.getText().equals(textbox.getText())) {
            currentSelection.setText(textbox.getText());
          }
          workPanel.remove(textbox);
        }

        textbox = new TextBox();
        textbox.setStyleName("editableTree-textBox");
        textbox.setHeight(item.getOffsetHeight() + "px");
        textbox.setWidth("90px");
        int xpos = item.getAbsoluteLeft() - 133;
        int ypos = item.getAbsoluteTop() - 115;
        workPanel.add(textbox, xpos, ypos);
        textbox.setText(item.getText());
        textbox.setFocus(true);
        currentSelection = item;
        textbox.addFocusListener(new FocusListener() {
          public void onFocus(Widget sender) {
          }

          public void onLostFocus(Widget sender) {
            if (sender.isAttached()) {
              if (!currentSelection.getText().equals(textbox.getText())) {
                currentSelection.setText(textbox.getText());
              }
              workPanel.remove(textbox);
            }
          }
        });
      }

      public void onTreeItemStateChanged(TreeItem item) {
      }
    });

    workPanel.add(editableTree);
    workPane.add(infoPanel, DockPanel.NORTH);
    workPane.add(workPanel, DockPanel.CENTER);
    workPane.setCellHeight(workPanel, "100%");
    workPane.setCellWidth(workPanel, "100%");
    initWidget(workPane);
  }

  private void initTree() {
    TreeItem root = new TreeItem("root");
    root.setState(true);
    int index = 100;
    for (int j = 0; j < 10; j++) {
      TreeItem item = new TreeItem();
      item.setText("File " + index++);
      root.addItem(item);
    }
    editableTree.addItem(root);
  }

  public void onShow() {
  }

}