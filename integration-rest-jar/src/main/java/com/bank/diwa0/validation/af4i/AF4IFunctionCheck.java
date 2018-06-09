package com.bank.diwa0.validation.af4i;

import net.sf.oval.constraint.CheckWithCheck;

public class AF4IFunctionCheck implements CheckWithCheck.SimpleCheck {

	public boolean isSatisfied(Object validatedObject, Object value) {

		return value.equals("I") || value.equals("V") || value.equals("A");
	}
}