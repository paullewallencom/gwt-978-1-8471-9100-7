package com.packtpub.gwtbook.widgets.client;

import java.util.Date;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.packtpub.gwtbook.samples.client.util.Rico;

public class CalendarWidget extends Composite implements ClickListener {

	private DockPanel navigationBar = new DockPanel();

	private Button previousMonth = new Button("&lt;", this);

	private Button nextMonth = new Button("&gt;", this);

	private Button todayButton = new Button("Today");

	private HTML calendarTitle = new HTML();

	private Label dayOfWeek = new Label("");

	private Label dateOfWeek = new Label("");

	private Date currentDate = new Date();

	private String[] daysInWeek = new String[] { "Sunday", "Monday", "Tuesday",
			"Wednesday", "Thursday", "Friday", "Saturday" };

	private String[] monthsInYear = new String[] { "January", "February",
			"March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };

	private final DockPanel outerDockPanel = new DockPanel();

	private final Grid calendarGrid = new Grid(7, 7) {
		public boolean clearCell(int row, int column) {
			boolean retValue = super.clearCell(row, column);

			Element td = getCellFormatter().getElement(row, column);
			DOM.setInnerHTML(td, "");
			return retValue;
		}
	};

	private static class calendarCell extends HTML {
		private int day;

		public calendarCell(String cellText, int day) {
			super(cellText);
			this.day = day;
		}

		public int getDay() {
			return day;
		}
	}

	public CalendarWidget() {
		HorizontalPanel hpanel = new HorizontalPanel();

		navigationBar.setStyleName("navbar");
		calendarTitle.setStyleName("header");

		HorizontalPanel prevButtons = new HorizontalPanel();
		prevButtons.add(previousMonth);

		HorizontalPanel nextButtons = new HorizontalPanel();
		nextButtons.add(nextMonth);

		navigationBar.add(prevButtons, DockPanel.WEST);
		navigationBar.setCellHorizontalAlignment(prevButtons,
				DockPanel.ALIGN_LEFT);
		navigationBar.add(nextButtons, DockPanel.EAST);
		navigationBar.setCellHorizontalAlignment(nextButtons,
				DockPanel.ALIGN_RIGHT);
		navigationBar.add(calendarTitle, DockPanel.CENTER);
		navigationBar.setVerticalAlignment(DockPanel.ALIGN_MIDDLE);
		navigationBar.setCellHorizontalAlignment(calendarTitle,
				HasAlignment.ALIGN_CENTER);
		navigationBar.setCellVerticalAlignment(calendarTitle,
				HasAlignment.ALIGN_MIDDLE);
		navigationBar.setCellWidth(calendarTitle, "100%");

		initWidget(hpanel);
		calendarGrid.setStyleName("table");
		calendarGrid.setCellSpacing(0);

		DOM.setAttribute(hpanel.getElement(), "id", "calDiv");
		DOM.setAttribute(hpanel.getElement(), "className",
				"CalendarWidgetHolder");
		Rico.corner(hpanel.getElement(), null);

		hpanel.add(outerDockPanel);
		VerticalPanel calendarPanel = new VerticalPanel();
		calendarPanel.add(navigationBar);
		VerticalPanel vpanel = new VerticalPanel();
		calendarPanel.add(calendarGrid);
		calendarPanel.add(todayButton);

		todayButton.setStyleName("todayButton");
		todayButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				currentDate = new Date();
				renderCalendar();
			}
		});

		dayOfWeek.setStyleName("dayOfWeek");
		dateOfWeek.setStyleName("dateOfWeek");
		vpanel.add(dayOfWeek);
		vpanel.add(dateOfWeek);
		vpanel.setStyleName("vpanel");
		outerDockPanel.add(vpanel, DockPanel.CENTER);
		outerDockPanel.add(calendarPanel, DockPanel.EAST);
		renderCalendar();
		setStyleName("CalendarWidget");
		this.sinkEvents(Event.ONCLICK);
	}

	private void renderCalendar() {
		int year = getYear();
		int month = getMonth();
		int day = getDay();
		calendarTitle.setText(monthsInYear[month] + " " + year);
		calendarGrid.getRowFormatter().setStyleName(0, "weekheader");
		for (int i = 0; i < daysInWeek.length; i++) {
			calendarGrid.getCellFormatter().setStyleName(0, i, "days");
			calendarGrid.setText(0, i, daysInWeek[i].substring(0, 1));
		}

		Date now = new Date();
		int sameDay = now.getDate();
		int today = (now.getMonth() == month && now.getYear() + 1900 == year) ? sameDay
				: 0;
		int firstDay = new Date(year - 1900, month, 1).getDay();
		int numOfDays = getDaysInMonth(year, month);
		int weekDay = now.getDay();

		dayOfWeek.setText(daysInWeek[weekDay]);
		dateOfWeek.setText("" + day);

		int j = 0;
		for (int i = 1; i < 6; i++) {
			for (int k = 0; k < 7; k++, j++) {
				int displayNum = (j - firstDay + 1);
				if (j < firstDay || displayNum > numOfDays) {
					calendarGrid.getCellFormatter().setStyleName(i, k, "empty");
					calendarGrid.setHTML(i, k, "&nbsp;");
				} else {
					HTML html = new calendarCell("<span>"
							+ String.valueOf(displayNum) + "</span>",
							displayNum);
					html.addClickListener(this);
					calendarGrid.getCellFormatter().setStyleName(i, k, "cell");
					if (displayNum == today) {
						calendarGrid.getCellFormatter().addStyleName(i, k,
								"today");
					} else if (displayNum == sameDay) {
						calendarGrid.getCellFormatter().addStyleName(i, k,
								"day");
					}
					calendarGrid.setWidget(i, k, html);
				}
			}
		}
	}

	private int getDaysInMonth(int year, int month) {
		switch (month) {
		case 1:
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				return 29;
			else
				return 28;
		case 3:
			return 30;
		case 5:
			return 30;
		case 8:
			return 30;
		case 10:
			return 30;
		default:
			return 31;
		}
	}

	public void computeCalendarForPreviousMonth() {
		int month = getMonth() - 1;
		if (month < 0) {
			setDate(getYear() - 1, 11, getDay());
		} else {
			setMonth(month);
		}
		renderCalendar();
	}

	public void computeCalendarForNextMonth() {
		int month = getMonth() + 1;
		if (month > 11) {
			setDate(getYear() + 1, 0, getDay());
		} else {
			setMonth(month);
		}
		renderCalendar();
	}

	private void setDate(int year, int month, int day) {
		currentDate = new Date(year - 1900, month, day);
	}

	private void setYear(int year) {
		currentDate.setYear(year - 1900);
	}

	private void setMonth(int month) {
		currentDate.setMonth(month);
	}

	public int getYear() {
		return 1900 + currentDate.getYear();
	}

	public int getMonth() {
		return currentDate.getMonth();
	}

	public int getDay() {
		return currentDate.getDate();
	}

	public Date getDate() {
		return currentDate;
	}

	public void onClick(Widget sender) {
		if (sender == previousMonth) {
			computeCalendarForPreviousMonth();
		} else if (sender == nextMonth) {
			computeCalendarForNextMonth();
		} else {
			calendarCell cell = (calendarCell) sender;
			setDate(getYear(), getMonth(), cell.getDay());
			renderCalendar();
		}
	}
}