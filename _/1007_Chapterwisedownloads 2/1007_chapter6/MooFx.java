package com.packtpub.gwtbook.samples.client.util;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;

public class MooFx {
	public native static Element opacity(Element element) /*-{
	 $wnd._nativeExtensions = false;
	 return new $wnd.fx.Opacity(element); 
	 }-*/;

	public native static void toggleOpacity(Element element) /*-{
	 $wnd._nativeExtensions = false;
	 element.toggle();
	 }-*/;

	public static Element height(Element element, String opts) {
		return height(element, buildOptions(opts));
	}

	private native static Element height(Element element, JavaScriptObject opts) /*-{
	 $wnd._nativeExtensions = false;
	 return new $wnd.fx.Height(element, opts);
	 }-*/;

	public native static void toggleHeight(Element element) /*-{
	 $wnd._nativeExtensions = false;
	 element.toggle(); 
	 }-*/;

	public static Element width(Element element, String opts) {
		return width(element, buildOptions(opts));
	}

	private native static Element width(Element element, JavaScriptObject opts) /*-{
	 $wnd._nativeExtensions = false;
	 return new $wnd.fx.Width(element, opts);
	 }-*/;

	public native static void toggleWidth(Element element) /*-{
	 $wnd._nativeExtensions = false;
	  element.toggle();
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