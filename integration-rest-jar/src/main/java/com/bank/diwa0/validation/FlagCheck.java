bank com.intesasanpaolo.diwa0.validation;

import net.sf.oval.constraint.CheckWithCheck;

public class FlagCheck implements CheckWithCheck.SimpleCheck {

	public boolean isSatisfied(Object validatedObject, Object value) {

		return value.equals("S") || value.equals("N");
	}
}