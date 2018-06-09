bank com.intesasanpaolo.diwa0.mapper.converter;

import it.eng.sclibs.visage.contatti.model.IndirizziControparte;
import org.modelmapper.AbstractConverter;

public class AFVDViaIndirizzoCompletoConverter extends AbstractConverter<IndirizziControparte, String> {

	protected String convert(IndirizziControparte source) {

		return String.format("%s %s %s", source.getToponimoInd(), source.getViaInd(), source.getCapInd());
	}
}