bank com.intesasanpaolo.diwa0.mapper.converter;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.modelmapper.AbstractConverter;

import java.util.Date;

public class TimestampConverter extends AbstractConverter<Date, String> {

	private final static DateTimeFormatter FMT = ISODateTimeFormat.dateHourMinuteSecondMillis();

	protected String convert(Date source) {
		if (source != null)
			return FMT.print(source.getTime());
		else
			return null;
	}
}