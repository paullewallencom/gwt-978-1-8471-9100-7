package com.packtpub.gwtbook.samples.client.panels;

import com.google.gwt.user.client.HTTPRequest;
import com.google.gwt.user.client.ResponseTextHandler;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.packtpub.gwtbook.samples.client.SamplePanel;

public class ParseXMLPanel extends SamplePanel {

	private VerticalPanel workPanel = new VerticalPanel();

	private FlexTable booksTable = new FlexTable();

	public static SinkInfo init() {
		return new SinkInfo("Parse XML", "Parse XML.") {
			public SamplePanel createInstance() {
				return new ParseXMLPanel();
			}
		};
	}

	public ParseXMLPanel() {
		HorizontalPanel infoPanel = new HorizontalPanel();
		infoPanel
				.add(new HTML(
						"<div class='infoProse'>Parse XML and populate a table with that data.</div>"));

		booksTable.setWidth(500 + "px");
		booksTable.setStyleName("xmlParse-Table");
		booksTable.setBorderWidth(1);
		booksTable.setCellPadding(4);
		booksTable.setCellSpacing(1);

		booksTable.setText(0, 0, "Title");
		booksTable.setText(0, 1, "Author");
		booksTable.setText(0, 2, "Publication Year");

		RowFormatter rowFormatter = booksTable.getRowFormatter();
		rowFormatter.setStyleName(0, "xmlParse-TableHeader");

		HTTPRequest.asyncGet("books.xml", new ResponseTextHandler() {
			public void onCompletion(String responseText) {
				// In the real world, this text would come as a RPC response.
				// This
				// technique is great for testing and samples though!
				Document bookDom = XMLParser.parse(responseText);
				Element booksElement = bookDom.getDocumentElement();
				// Must do this if you ever use a raw node list that you expect
				// to be all elements.
				XMLParser.removeWhitespace(booksElement);
				NodeList bookElements = booksElement
						.getElementsByTagName("book");
				for (int i = 0; i < bookElements.getLength(); i++) {
					Element bookElement = (Element) bookElements.item(i);
					booksTable.setText(i + 1, 0, getElementTextValue(
							bookElement, "title"));
					booksTable.setText(i + 1, 1, getElementTextValue(
							bookElement, "author"));
					booksTable.setText(i + 1, 2, getElementTextValue(
							bookElement, "year"));
				}
			}
		});

		DockPanel workPane = new DockPanel();
		workPanel.add(booksTable);
		workPane.add(infoPanel, DockPanel.NORTH);
		workPane.add(workPanel, DockPanel.CENTER);
		workPane.setCellHeight(workPanel, "100%");
		workPane.setCellWidth(workPanel, "100%");
		initWidget(workPane);
	}

	public void onShow() {
	}

	private String getElementTextValue(Element parent, String elementTag) {
		return parent.getElementsByTagName(elementTag).item(0).getFirstChild()
				.getNodeValue();
	}
}