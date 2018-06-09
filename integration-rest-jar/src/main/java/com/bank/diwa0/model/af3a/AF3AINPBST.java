package com.bank.diwa0.model.af3a;

import com.bank.diwa0.annotation.Azienda;
import com.bank.diwa0.annotation.Ndg;
import com.bank.diwa0.annotation.Ruolo;
import com.bank.diwa0.annotation.Trascodifica;
import com.bank.diwa0.validation.FlagCheck;
import com.bank.diwa0.validation.OptionalFlagCheck;
import net.sf.oval.constraint.*;

@Trascodifica
public class AF3AINPBST {

	private String gruppo;

	@MatchPattern(
			pattern = "[0-9]*",
			errorCode = "{'livelloSegnalazione':'W', 'txtSegnalazione':'AZIENDA NON NUMERICO'}")
	@Azienda
	private String azienda;

	@Ruolo
	private String ruolo;

	private String lingua;

	@NotNull(errorCode = "{'livelloSegnalazione':'E', 'txtSegnalazione':'NDG OBBLIGATORIO'}")
	@NotEmpty(errorCode = "{'livelloSegnalazione':'E', 'txtSegnalazione':'NDG OBBLIGATORIO'}")
	@MatchPattern(
			pattern = "[0-9]*",
			errorCode = "{'livelloSegnalazione':'E', 'txtSegnalazione':'VALORIZZARE NDG NUMERICO'}")
	@Length(
			max = 16,
			errorCode = "{'livelloSegnalazione':'E', 'txtSegnalazione':'NDG AL MASSIMO CONSENTITO (16 NUMERI)'}")
	@Ndg
	private String ndg;

	private String cdn;

	private String codiceLei;

	@CheckWith(
			value = OptionalFlagCheck.class,
			errorCode = "{'livelloSegnalazione':'E', 'txtSegnalazione':'FLAG FORZATURA  NON VALORIZZATO CORRETTAMENTE  VALORIZZARE CON S/N'}")
	private String flagForzatura;

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getLingua() {
		return lingua;
	}

	public void setLingua(String lingua) {
		this.lingua = lingua;
	}

	public String getNdg() {
		return ndg;
	}

	public void setNdg(String ndg) {
		this.ndg = ndg;
	}

	public String getCodiceLei() {
		return codiceLei;
	}

	public void setCodiceLei(String codiceLei) {
		this.codiceLei = codiceLei;
	}

	public String getFlagForzatura() {
		return flagForzatura;
	}

	public void setFlagForzatura(String flagForzatura) {
		this.flagForzatura = flagForzatura;
	}

	public String getGruppo() {
		return gruppo;
	}

	public void setGruppo(String gruppo) {
		this.gruppo = gruppo;
	}

	public String getAzienda() {
		return azienda;
	}

	public void setAzienda(String azienda) {
		this.azienda = azienda;
	}

	public String getCdn() {
		return cdn;
	}

	public void setCdn(String cdn) {
		this.cdn = cdn;
	}
}
