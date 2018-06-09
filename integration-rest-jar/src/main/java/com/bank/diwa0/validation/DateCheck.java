package com.bank.diwa0.validation;

import net.sf.oval.constraint.CheckWithCheck;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateCheck implements CheckWithCheck.SimpleCheck {

	public boolean isSatisfied(Object validatedObject, Object value) {

		if (value == null || ((String) value).isEmpty())
			return true;
		try {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			df.parse((String) value);
			return true;
		} catch (ParseException ex) {
			return false;
		}
	}
}