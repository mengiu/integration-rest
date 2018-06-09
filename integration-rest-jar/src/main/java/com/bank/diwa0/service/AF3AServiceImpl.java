bank com.bank.diwa0.service;

import com.bank.diwa0.configuration.AppProperties;
import com.bank.diwa0.model.ModelHelper;
import com.bank.diwa0.model.af3a.AF3AINPBST;
import com.bank.diwa0.model.af3a.AF3AOUTBST;
import com.bank.diwa0.model.af3a.AF3AOUTLEI;
import it.eng.sclibs.visage.controparti.model.CodiciLeiControparte;
import it.eng.sclibs.visage.controparti.model.CodiciLeiControparteResponse;
import it.eng.sclibs.visage.controparti.model.EspansioneContenutoInformativoControparte;
import it.eng.sclibs.visage.controparti.service.EspansioneContenutoInformativoContropartePrepareService;
import it.eng.sclibs.visage.controparti.service.EspansioneContenutoInformativoControparteService;
import it.eng.sclibs.visage.services.ControparteService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AF3AServiceImpl extends BaseServiceImpl implements AF3AService {
	private final Logger logger = LoggerFactory.getLogger(AF3AServiceImpl.class);

	@Autowired
	AppProperties appProperties;

	@Autowired
	EspansioneContenutoInformativoControparteService espansioneContenutoInformativoControparteService;

	@Autowired
	EspansioneContenutoInformativoContropartePrepareService espansioneContenutoInformativoContropartePrepareService;

	@Autowired
	ModelMapper modelMapper;

	@Transactional
	public void createResource(AF3AINPBST input) {
		try {
			ModelHelper.trascodifica(trascodificaNdgService, input, ModelHelper.Verso.NDGtoCDN);
			logger.info("trascodificaNdgService.ndgToCdn");
			CodiciLeiControparte codiciLeiControparte= modelMapper.map(input, CodiciLeiControparte.class);
			espansioneContenutoInformativoContropartePrepareService.insertCodiciLeiControparte(codiciLeiControparte);
			logger.info("espansioneContenutoInformativoContropartePrepareService.insertCodiciLeiControparte");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new RuntimeException(ex);
		}
	}

	@Transactional
	public void updateResource(AF3AINPBST input) {
		try {
			ModelHelper.trascodifica(trascodificaNdgService, input, ModelHelper.Verso.NDGtoCDN);
			logger.info("trascodificaNdgService.ndgToCdn");
			CodiciLeiControparte codiciLeiControparte= modelMapper.map(input, CodiciLeiControparte.class);
			codiciLeiControparte.setFlagForzatura("S".equals(input.getFlagForzatura()));
			espansioneContenutoInformativoContropartePrepareService.insertUpdateCodiciLeiControparte(codiciLeiControparte);
			logger.info("espansioneContenutoInformativoContropartePrepareService.insertUpdateCodiciLeiControparte");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new RuntimeException(ex);
		}
	}

	@Transactional
	public void deleteResource(AF3AINPBST input) {
		try {
			Long azienda = Long.parseLong(input.getAzienda());
			String ndg = trascodificaNdgService.ndgToCdn(azienda, input.getNdg());
			logger.info("trascodificaNdgService.ndgToCdn");
			espansioneContenutoInformativoContropartePrepareService.deleteCodiciLeiControparte(azienda, ndg);
			logger.info("espansioneContenutoInformativoContropartePrepareService.deleteCodiciLeiControparte");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new RuntimeException(ex);
		}
	}

	@Transactional
	public AF3AOUTBST retrieveResource(AF3AINPBST input) {

		try {
			Long azienda = Long.parseLong(input.getAzienda());
			String ndg = trascodificaNdgService.ndgToCdn(azienda, input.getNdg());
			logger.info("trascodificaNdgService.ndgToCdn");
			ModelHelper.trascodifica(trascodificaNdgService, input, ModelHelper.Verso.NDGtoCDN);
			CodiciLeiControparteResponse resp = espansioneContenutoInformativoControparteService.getCodiciLeiControparte(azienda, ndg, input.getCodiceLei());
			logger.info("espansioneContenutoInformativoControparteService.getCodiciLeiControparte");
			AF3AOUTBST result = new AF3AOUTBST();
			result.setGruppo(input.getGruppo());
			result.setAzienda(input.getAzienda());
			result.setRuolo(input.getRuolo());
			result.setLingua(input.getLingua());
			result.setNdg(ndg);
			result.setCdn(ndg);
			result.setOutLei(new AF3AOUTLEI());
			if (resp.getPresenzaInAnagrafe())
				result.getOutLei().setNagPresenzaDati("S");
			else
				result.getOutLei().setNagPresenzaDati("N");

			if (resp.getCodiciLeiAnagrafe() != null) {
				result.getOutLei().setNagCodiceLei(resp.getCodiciLeiAnagrafe().getCodiceLei());
				result.getOutLei().setNagDescrizioneEnte(resp.getCodiciLeiAnagrafe().getIntestazioneControparte());
				result.getOutLei().setNagEnte(resp.getCodiciLeiAnagrafe().getCodiceInfoProvider());
				result.getOutLei().setNagStatus(resp.getCodiciLeiAnagrafe().getStatusDetail());
				result.getOutLei().setNagDataValidita(resp.getCodiciLeiAnagrafe().getDataValidita());
			}

			if (resp.getPresenzaInInfoProvider())
				result.getOutLei().setPrPresenzaDati("S");
			else
				result.getOutLei().setPrPresenzaDati("N");

			if (resp.getCodiciLeiInfoProvider() != null) {
				result.getOutLei().setPrCodiceLei(resp.getCodiciLeiInfoProvider().getCodiceLei());
				result.getOutLei().setPrDescrizioneEnte(resp.getCodiciLeiInfoProvider().getIntestazioneControparte());
				result.getOutLei().setPrEnte(resp.getCodiciLeiInfoProvider().getCodiceInfoProvider());
				result.getOutLei().setPrStatus(resp.getCodiciLeiInfoProvider().getStatusDetail());
				result.getOutLei().setPrDataValidita(resp.getCodiciLeiInfoProvider().getDataValidita());
			}
			ModelHelper.trascodifica(trascodificaNdgService, result, ModelHelper.Verso.CDNtoNDG);
			return result;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new RuntimeException(ex);
		}
	}
}
