package com.bank.diwa0.mapper;

import com.bank.diwa0.mapper.converter.TimestampConverter;
import com.bank.diwa0.model.afdy.AFDYOUTAAC;
import it.eng.sclibs.visage.contatti.model.Account;
import org.modelmapper.PropertyMap;

public class AccountToAFDYOUTAACMap extends PropertyMap<Account, AFDYOUTAAC> {

	protected void configure() {
		using(new TimestampConverter()).map(source.getTimeStampAggiornamento()).setTsAgg(null);
		map(source.getNdg()).setCdn(null);
	}
}