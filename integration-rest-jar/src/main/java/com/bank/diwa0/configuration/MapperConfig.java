package com.bank.diwa0.configuration;

import com.bank.diwa0.mapper.*;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class MapperConfig {

	private final static DateFormat DF = new SimpleDateFormat("yyyyMMdd");

	@Bean
	public ModelMapper modelMapper() {

		Converter<String, Date> fromStringToDate = new AbstractConverter<String, Date>() {
			protected Date convert(String source) {
				try {
					return DF.parse(source);
				} catch (Exception ex) {
					return null;
				}
			}
		};

		Converter<Date, String> fromDateToString = new AbstractConverter<Date, String>() {
			protected String convert(Date source) {
				if (source != null)
					return DF.format(source);
				else
					return null;
			}
		};

		ModelMapper mapper = new ModelMapper();

		// Add application-wide converters
		mapper.addConverter(fromStringToDate);
		mapper.addConverter(fromDateToString);

		// Add application-wide mappings
		mapper.addMappings(new AccountDescrizioniToAFVROUTAACMap());
		mapper.addMappings(new AccountToAFDYOUTAACMap());
		mapper.addMappings(new AccountToAFIIOUTAACMap());
		mapper.addMappings(new AccountToAFRYOUTAACMap());
		mapper.addMappings(new AF3AINPBSTToCodiciLeiControparteMap());
		mapper.addMappings(new AF4IINPAPYToDatiPrivacyControparteForListMap());
		mapper.addMappings(new AF53INPBSTToRelazioniAnagraficheMap());
		mapper.addMappings(new AFACINPBSTToEspansioneContenutoInformativoControparteMap());
		mapper.addMappings(new AFCEINPACMToLegamiNormativiEInformativiMap());
		mapper.addMappings(new AFCXINPBSTToRelazioniAnagraficheMap());
		mapper.addMappings(new AFFJINPAPYToDatiPrivacyControparteForListMap());
		mapper.addMappings(new AFFJINPDOCToDocumentiMap());
		mapper.addMappings(new AFFPINPAPYToDatiPrivacyControparteForListMap());
		mapper.addMappings(new AFGJINPAPYToDatiPrivacyControparteForListMap());
		mapper.addMappings(new CodiciVariControparteDetailToAFDYOUTAQKMap());
		mapper.addMappings(new CodiciVariControparteToAFVCOUTBSTMap());
		mapper.addMappings(new CollegamentiTitolareEffettivoToAFCTOUTBSTMap());
		mapper.addMappings(new DatiAnagraficiControparteToAFDYOUTAQLMap());
		mapper.addMappings(new DatiComplementariControparteForListToAFDYOUTAQCMap());
		mapper.addMappings(new DatiPrivacyControparteToAFDYOUTAPYMap());
		mapper.addMappings(new DocumentiAllegatiToAFDYOUTAIDMap());
		mapper.addMappings(new DocumentiAllegatiToAFDYOUTAQGMap());
		mapper.addMappings(new DocumentiAllegatiToAFIIOUTAQGMap());
		mapper.addMappings(new DocumentiToAFDNOUTBSTMap());
		mapper.addMappings(new EmailControparteDescrizioniToAFVROUTAQMMap());
		mapper.addMappings(new EmailControparteDescrizioniToAFDYOUTAQMMap());
		mapper.addMappings(new EmailControparteToAFIIOUTAQMMap());
		mapper.addMappings(new EmailControparteToAFRYOUTAQMMap());
		mapper.addMappings(new EmailControparteToAFSVOUTEMLMap());
		mapper.addMappings(new EspansioneContenutoInformativoControparteDetailToAFACOUTBSTMap());
		mapper.addMappings(new EspansioneContenutoInformativoControparteForListToAFACOUTBSTMap());
		mapper.addMappings(new EspansioneContenutoInformativoControparteForListToAFDYOUTAENMap());
		mapper.addMappings(new EspansioneContenutoInformativoControparteToAFACOUTBSTMap());
		mapper.addMappings(new IndirizziControparteDescrizioniToAFVDOUTBSTMap());
		mapper.addMappings(new IndirizziControparteDescrizioniToAFDYOUTAQVMap());
		mapper.addMappings(new IndirizziControparteToAFS7OUTAQVMap());
		mapper.addMappings(new IndirizziControparteToAFVDOUTBSTMap());
		mapper.addMappings(new IntestazioniControparteToAFDYOUTAQIMap());
		mapper.addMappings(new IntestazioniControparteToAFS7OUTAQIMap());
		mapper.addMappings(new NotizieNegativeControparteForListToAFABOUTBSTMap());
		mapper.addMappings(new NotizieNegativeControparteToAFABOUTBSTMap());
		mapper.addMappings(new OccorrenzaRelazioneToAFCXOUTBSTMap());
		mapper.addMappings(new OccorrenzaRelazioneToAFRJOUTBSTMap());
		mapper.addMappings(new OmonimoToAF4IOUTBSTMap());
		mapper.addMappings(new OmonimoToAFDYOUTDCTMap());
		mapper.addMappings(new OmonimoToAFESOUTBSTMAP());
		mapper.addMappings(new OmonimoToAFESOUTNOMMap());
		mapper.addMappings(new OmonimoToAFEXOUTBSTMap());
		mapper.addMappings(new OmonimoToAFFPOUTBSTMap());
		mapper.addMappings(new OmonimoToAFIIOUTBSTMap());
		mapper.addMappings(new OmonimoToAFMAOUTBSTMap());
		mapper.addMappings(new OmonimoToAFRJOUTDELMap());
		mapper.addMappings(new OmonimoToAFRJOUTTITMap());
		mapper.addMappings(new OmonimoToAFRYOUTBSTMap());
		mapper.addMappings(new OmonimoToAFSVOUTBSTMap());
		mapper.addMappings(new OmonimoToAFSZOUTBSTMap());
		mapper.addMappings(new RapportiListaToAFCMOUTBSTMap());
		mapper.addMappings(new RapportiListaToAFCYOUTBSTMap());
		mapper.addMappings(new RapportiListaToAFEXOUTRAPMap());
		mapper.addMappings(new RapportiListaToAFRYOUTARTMap());
		mapper.addMappings(new RapportiToAFCYOUTBSTMap());
		mapper.addMappings(new RapportiToAFDYOUTAATMap());
		mapper.addMappings(new RapportiToAFDYOUTARTMap());
		mapper.addMappings(new RapportiToAFRYOUTARTMap());
		mapper.addMappings(new RelazioniAnagraficheListaToAFCXOUTBSTMap());
		mapper.addMappings(new RelazioniAnagraficheToAFCXOUTBSTMap());
		mapper.addMappings(new RisultatoCompletezzaCampoToAFMAOUTCMPMap());
		mapper.addMappings(new RuoliControparteToAFDYOUTARUMap());
		mapper.addMappings(new SettoriEconomiciControparteForListToAFDYOUTAQEMap());
		mapper.addMappings(new StatusDiRapportoDetailToAF4MOUTBSTMap());
		mapper.addMappings(new StatusDiRapportoDetailToAFDYOUTARSMap());
		mapper.addMappings(new StatusDiRapportoToAF4MOUTBSTMap());
		mapper.addMappings(new StatusNdgControparteDetailToAF4MOUTBSTMap());
		mapper.addMappings(new StatusNdgControparteDetailToAFDYOUTAQTMap());
		mapper.addMappings(new StatusNdgControparteDetailToAFRYOUTSTTMap());
		mapper.addMappings(new StatusNdgControparteToAF4MOUTBSTMap());
		mapper.addMappings(new TelefoniControparteDescrizioniToAFVROUTAQFMap());
		mapper.addMappings(new TelefoniControparteToAFDYOUTAQFMap());
		mapper.addMappings(new TelefoniControparteToAFIIOUTAQFMap());
		mapper.addMappings(new TelefoniControparteToAFRYOUTAQFMap());

		return mapper;
	}
}