package com.bank.diwa0.api;

import com.bank.diwa0.configuration.AppProperties;
import com.bank.diwa0.model.AppError;
import com.bank.diwa0.model.OUTSEG;
import com.bank.diwa0.model.PagedResult;
import com.bank.diwa0.model.af4f.AF4FINPBST;
import com.bank.diwa0.service.AF4FService;
import com.bank.diwa0.service.MonitoringService;
import com.bank.diwa0.validation.ValidationHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Api(tags = "AF4F", description = "Gestione dato facta")
@RestController
public class AF4FApi {

	private static final String REST_NAME = "af4f";
	private static final String REST_PATH = AppProperties.APPLICATION_REST_PREFIX + REST_NAME;
	private final Logger logger = LoggerFactory.getLogger(AF4FApi.class);

	@Autowired
	Validator validator;

	@Autowired
	AF4FService af4fService;

	@Autowired
	MonitoringService monitoringService;

	@ApiOperation(value = REST_NAME)
	@RequestMapping(
			value = REST_PATH + "/{gruppo}/{azienda}/{ndg}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> retrieveResource(
			@ApiParam(required = true) @PathVariable(value = "gruppo", required = true) String gruppo,
			@ApiParam(required = true) @PathVariable(value = "azienda", required = true) String azienda,
			@ApiParam(required = false) @RequestParam(value = "ruolo", required = false) String ruolo,
			@ApiParam(required = false) @RequestParam(value = "lingua", required = false) String lingua,
			@ApiParam(required = true) @PathVariable(value = "ndg", required = true) String ndg,
			@ApiParam(required = false) @RequestParam(value = "cdn", required = false) String cdn,
			@ApiParam(required = false) @RequestParam(value = "righePagina", required = false) String righePagina,
			@ApiParam(required = false) @RequestParam(value = "chiaveRipartenza", required = false) String chiaveRipartenza,
			@ApiIgnore @RequestHeader(value = "ISPWebservicesHeader.TechnicalInfo.ChannelIDCode", required = false) String channelIDCode,
			@ApiIgnore @RequestHeader(value = "ISPWebservicesHeader.AdditionalBusinessInfo.CodABI", required = false) String aCodiceABI) {

		String requestID = UUID.randomUUID().toString();
		MDC.put("requestID", requestID);
		MDC.put("sourceID", REST_PATH);
		long startTime = System.currentTimeMillis();
		AF4FINPBST input = new AF4FINPBST();
		input.setGruppo(gruppo);
		input.setAzienda(azienda);
		input.setRuolo(ruolo);
		input.setLingua(lingua);
		input.setNdg(ndg);
		input.setCdn(cdn);
		input.setRighePagina(righePagina);
		input.setChiaveRipartenza(chiaveRipartenza);

		try {
			setDefaults(input);
			List errors = validateInputForRetrieve(input);
			if (errors.size() > 0) {
				logger.info(HttpStatus.BAD_REQUEST.toString());
				AppError error = new AppError(HttpStatus.BAD_REQUEST.toString(), errors);
				monitoringService.logService(aCodiceABI, channelIDCode, REST_NAME, startTime, false);
				return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
			} else {
				af4fService.checkRole(input);
				logger.info(af4fService.getJsonString(input));
				PagedResult result = af4fService.retrieveResource(input);
				logger.info(af4fService.getJsonString(result));
				if (result != null && result.isNotEmpty()) {
					logger.info(HttpStatus.OK.toString());
					monitoringService.logService(aCodiceABI, channelIDCode, REST_NAME, startTime, true);
					return new ResponseEntity<Object>(result, HttpStatus.OK);
				} else {
					logger.info(HttpStatus.NO_CONTENT.toString());
					monitoringService.logService(aCodiceABI, channelIDCode, REST_NAME, startTime, true);
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			AppError error = new AppError(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex);
			monitoringService.logService(aCodiceABI, channelIDCode, REST_NAME, startTime, false);
			return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = REST_NAME)
	@RequestMapping(
			value = REST_PATH + "/{gruppo}/{azienda}/{ndg}",
			method = RequestMethod.PUT)
	public ResponseEntity<Object> updateResource(
			@ApiParam(required = true) @PathVariable(value = "gruppo", required = true) String gruppo,
			@ApiParam(required = true) @PathVariable(value = "azienda", required = true) String azienda,
			@ApiParam(required = false) @RequestParam(value = "ruolo", required = false) String ruolo,
			@ApiParam(required = false) @RequestParam(value = "lingua", required = false) String lingua,
			@ApiParam(required = true) @PathVariable(value = "ndg", required = true) String ndg,
			@ApiParam(required = false) @RequestParam(value = "cdn", required = false) String cdn,
			@RequestBody AF4FINPBST input,
			@ApiIgnore @RequestHeader(value = "ISPWebservicesHeader.TechnicalInfo.ChannelIDCode", required = false) String channelIDCode,
			@ApiIgnore @RequestHeader(value = "ISPWebservicesHeader.AdditionalBusinessInfo.CodABI", required = false) String aCodiceABI) {

		String requestID = UUID.randomUUID().toString();
		MDC.put("requestID", requestID);
		MDC.put("sourceID", REST_PATH);
		long startTime = System.currentTimeMillis();
		input.setGruppo(gruppo);
		input.setAzienda(azienda);
		input.setRuolo(ruolo);
		input.setLingua(lingua);
		input.setNdg(ndg);
		input.setCdn(cdn);

		try {
			setDefaults(input);
			List errors = validateInputForUpdate(input);
			if (errors.size() > 0) {
				logger.info(HttpStatus.BAD_REQUEST.toString());
				AppError error = new AppError(HttpStatus.BAD_REQUEST.toString(), errors);
				monitoringService.logService(aCodiceABI, channelIDCode, REST_NAME, startTime, false);
				return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
			} else {
				af4fService.checkRole(input);
				logger.info(af4fService.getJsonString(input));
				af4fService.updateResource(input);
				logger.info(HttpStatus.OK.toString());
				monitoringService.logService(aCodiceABI, channelIDCode, REST_NAME, startTime, true);
				return new ResponseEntity<>(HttpStatus.OK);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			AppError error = new AppError(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex);
			monitoringService.logService(aCodiceABI, channelIDCode, REST_NAME, startTime, false);
			return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void setDefaults(AF4FINPBST input) {

		if (input.getRuolo() == null || input.getRuolo().equals(""))
			input.setRuolo("CUST");
	}

	private List<OUTSEG> validateInputForRetrieve(AF4FINPBST input) {

		List<OUTSEG> errors = new LinkedList<>();
		validator.disableAllProfiles();
		validator.enableProfile("default");
		validator.enableProfile("Q");
		List<ConstraintViolation> violations = validator.validate(input);
		ValidationHelper.addViolationsToErrors(errors, violations);
		return errors;
	}

	private List<OUTSEG> validateInputForUpdate(AF4FINPBST input) {

		List<OUTSEG> errors = new LinkedList<>();
		validator.disableAllProfiles();
		validator.enableProfile("default");
		validator.enableProfile("V");
		List<ConstraintViolation> violations = validator.validate(input);
		ValidationHelper.addViolationsToErrors(errors, violations);
		return errors;
	}
}