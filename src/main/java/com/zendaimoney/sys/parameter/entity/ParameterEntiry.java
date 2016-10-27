package com.zendaimoney.sys.parameter.entity;

import java.io.Serializable;


public class ParameterEntiry implements Serializable{
	public String getPrName() {
		return prName;
	}
	public void setPrName(String prName) {
		this.prName = prName;
	}
	public String getPrValue() {
		return prValue;
	}
	public void setPrValue(String prValue) {
		this.prValue = prValue;
	}
	public String getPrState() {
		return prState;
	}
	public void setPrState(String prState) {
		this.prState = prState;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 4556818670847489286L;
	private String prName;
	private String prValue;
	private String prState;

}
