package com.packtpub.gwtbook.samples.client.util;

public class Customer {
	private String id;

	private String name;

	private String city;

	private String zip;

	private String state;

	private String phone;

	public Customer(String id, String name, String city, String zip,
			String state, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.zip = zip;
		this.state = state;
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}
