bank com.intesasanpaolo.diwa0.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.intesasanpaolo.diwa0.model.OUTSEG;
import net.sf.oval.ConstraintViolation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ValidationHelper {

	private final static Logger logger = LoggerFactory.getLogger(ValidationHelper.class);
	private final static ObjectMapper MAPPER = new ObjectMapper();

	public static void addViolationsToErrors(List<OUTSEG> errors, List<ConstraintViolation> violations) {

		MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		for (ConstraintViolation violation : violations)
			if (violation.getCauses() == null)
				addError(errors, violation.getErrorCode());
			else
				for (ConstraintViolation cause : violation.getCauses())
					addError(errors, cause.getErrorCode());

		// Gli errori sopra riscontrati vengono esposti in sequenza tutti insieme
		if (errors.size() > 0)
			errors.add(0, new OUTSEG("E", "ERRORI DA SEGNALAZIONI SEGUENTI !!!"));
	}

	private static void addError(List<OUTSEG> errors, String errorCode) {

		MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		String jsonFormattedCode = errorCode.replace('\'', '\"');
		try {
			errors.add(MAPPER.readValue(jsonFormattedCode, OUTSEG.class));
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new RuntimeException(ex);
		}
	}
}