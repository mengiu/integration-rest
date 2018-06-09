package com.bank.diwa0.mapper;

import com.bank.diwa0.model.afvr.AFVROUTAAC;
import it.eng.sclibs.visage.contatti.model.AccountDescrizioni;
import org.modelmapper.PropertyMap;

public class AccountDescrizioniToAFVROUTAACMap extends PropertyMap<AccountDescrizioni, AFVROUTAAC> {

	protected void configure() {
		map(source.getAzienda()).setAzienda(null);
		map(source.getIdAccount()).setIdAccount(null);
		map(source.getNdg()).setCdn(null);
	}
}