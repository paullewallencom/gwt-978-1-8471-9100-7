package com.packtpub.gwtbook.widgets.server;

import java.net.MalformedURLException;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.jaxen.JaxenException;
import org.jaxen.XPath;
import org.jaxen.dom4j.Dom4jXPath;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.packtpub.gwtbook.widgets.client.Weather;
import com.packtpub.gwtbook.widgets.client.WeatherService;

public class WeatherServiceImpl extends RemoteServiceServlet implements
		WeatherService {

	public Weather getWeather(String zipCode) {
		SAXReader reader = new SAXReader();
		Weather weather = new Weather();
		Document document;
		try {
			document = reader.read(new URL(
					"http://xml.weather.yahoo.com/forecastrss?p=" + zipCode));

			XPath expression = new Dom4jXPath("/rss/channel");

			expression.addNamespace("yweather",
					"http://xml.weather.yahoo.com/ns/rss/1.0");
			expression.addNamespace("geo",
					"http://www.w3.org/2003/01/geo/wgs84_pos#");

			Node result = (Node) expression.selectSingleNode(document);
			String error = result.valueOf("/rss/channel/description");
			if (error.equals("Yahoo! Weather Error")) {
				weather
						.setError("Invalid zipcode "
								+ zipCode
								+ " provided. No weather information available for this location.");
				return weather;
			}

			String descriptionSection = result
					.valueOf("/rss/channel/item/description");
			weather.setImageUrl(descriptionSection.substring(descriptionSection
					.indexOf("src=") + 5,
					descriptionSection.indexOf(".gif") + 4));
			weather.setCity(result.valueOf("//yweather:location/@city"));
			weather.setState(result.valueOf("//yweather:location/@region"));

			weather.setChill(result.valueOf("//yweather:wind/@chill"));
			weather.setDirection(result.valueOf("//yweather:wind/@direction"));
			weather.setSpeed(result.valueOf("//yweather:wind/@speed"));

			weather.setHumidity(result
					.valueOf("//yweather:atmosphere/@humidity"));
			weather.setVisibility(result
					.valueOf("//yweather:atmosphere/@visibility"));
			weather.setPressure(result
					.valueOf("//yweather:atmosphere/@pressure"));
			weather.setRising(result.valueOf("//yweather:atmosphere/@rising"));

			weather.setSunrise(result.valueOf("//yweather:astronomy/@sunrise"));
			weather.setSunset(result.valueOf("//yweather:astronomy/@sunset"));

			weather.setCurrentCondition(result
					.valueOf("//yweather:condition/@text"));
			weather
					.setCurrentTemp(result
							.valueOf("//yweather:condition/@temp"));

			weather.setLatitude(result.valueOf("//geo:lat"));
			weather.setLongitude(result.valueOf("//geo:long"));

		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (JaxenException e) {
			e.printStackTrace();
		}
		return weather;
	}

}
