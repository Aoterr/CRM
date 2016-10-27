package com.zendaimoney.utils;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 
 * @author bianxj
 * 
 */
public class StringToDateConverter extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (!StringUtils.hasText(text)) {
			// Treat empty String as null value.
			setValue(null);
		} else {
			setValue(new Date(text));
		}
	}
}