package com.packtpub.gwtbook.samples.client.util;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class Rico {
	public static void corner(Widget widget) {
		corner(widget.getElement(), null);
	}

	public static void corner(Widget widget, String opts) {
		corner(widget.getElement(), buildOptions(opts));
	}

	public native static void corner(Element element, JavaScriptObject opts) /*-{
	 $wnd._nativeExtensions = false;
	 $wnd.Rico.Corner.round(element, opts); 
	 }-*/;

	public native static void color(Element element, int red, int green,
			int blue) /*-{
	 $wnd._nativeExtensions = false;
	 eval('' + element.id  +' = new $wnd.Rico.Color(' + red +',' + green +',' + blue + ')'); 
	 element.style.backgroundColor=eval(element.id + '.asHex()');
	 }-*/;

	public native static String getColorAsHex(Element element) /*-{
	 $wnd._nativeExtensions = false;
	 return (eval(element.id + '.asHex()'));
	 }-*/;
	
	private static native JavaScriptObject buildOptions(String opts) /*-{
	 eval("var optionObject = new Object()");
	 var options = opts.split(',');
	 for (var i =0; i < options.length; i++)
	 {
	 var opt = options[i].split(':');
	 eval("optionObject." + opt[0] + "=" +  opt[1]);
	 }
	 return optionObject; 
	 }-*/;
}
