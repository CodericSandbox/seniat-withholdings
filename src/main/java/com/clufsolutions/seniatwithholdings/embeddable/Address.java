package com.clufsolutions.seniatwithholdings.embeddable;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

	private String line1;
	private String line2;
	private String phone1;
	private String phone2;
	private String email1;
	private String email2;
	private State state;
	private City city;

	public Address(String line1, String line2, String phone1, State state, City city) {
		this.line1 = line1;
		this.line2 = line2;
		this.phone1 = phone1;
		this.state = state;
		this.city = city;
	}

	public Address() {
	}

	public enum State {
		ZU("Zulia");

		private final String name;

		private State(String name) {
			this.name = name;
		}

		public boolean equalsName(String anotherName) {
			return anotherName == null ? false : name.equals(anotherName);
		}

		public String toString() {
			return this.name;
		}
	}

	public enum City {
		OJEDA("Ojeda");

		private final String name;

		private City(String name) {
			this.name = name;
		}

		public boolean equalsName(String anotherName) {
			return anotherName == null ? false : name.equals(anotherName);
		}

		public String toString() {
			return this.name;
		}
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return line1 + " " + line2 + ", " + city + ", " + state;
	}

}
