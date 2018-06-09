package com.bank.diwa0.api;

import com.bank.diwa0.configuration.AppProperties;
import com.bank.diwa0.model.AppError;
import com.bank.diwa0.model.OUTSEG;
import com.bank.diwa0.model.af3a.AF3AINPBST;
import com.bank.diwa0.model.af3a.AF3AOUTBST;
import com.bank.diwa0.service.AF3AService;
import com.bank.diwa0.validation.ValidationHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.LinkedList;
import java.util.List;

@Api(tags = "AF3A", description = "Gestione Codice Lei")
@RestController
public class AF3AApi {
	private final Logger logger = LoggerFactory.getLogger(AF3AApi.class);

	private static final String REST_NAME = "af3a";
	private static final String REST_PATH = AppProperties.APPLICATION_REST_PREFIX + REST_NAME;

	@Autowired
	Validator validator;

	@Autowired
	AF3AService af3aService;

	@ApiOperation(value = REST_NAME)
	@RequestMapping(value = REST_PATH, method = RequestMethod.POST)
	public ResponseEntity<Object> createResource(@RequestBody AF3AINPBST input) {
		setDefaults(input);
		try {
			List errors = validateInput(input);
			if (errors.size() > 0) {
				logger.info(HttpStatus.BAD_REQUEST.toString());
				AppError error = new AppError(HttpStatus.BAD_REQUEST.toString(), errors);
				return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
			} else {
				af3aService.checkRole(input);
				af3aService.createResource(input);
				logger.info(HttpStatus.CREATED.toString());
				return new ResponseEntity<Object>(HttpStatus.CREATED);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			AppError error = new AppError(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex);
			return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = REST_NAME)
	@RequestMapping(value = REST_PATH, method = RequestMethod.PUT)
	public ResponseEntity<Object> updateResource(@RequestBody AF3AINPBST input) {
		setDefaults(input);
		try {
			List errors = validateInput(input);
			if (errors.size() > 0) {
				logger.info(HttpStatus.BAD_REQUEST.toString());
				AppError error = new AppError(HttpStatus.BAD_REQUEST.toString(), errors);
				return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
			} else {
				af3aService.checkRole(input);
				af3aService.updateResource(input);
				logger.info(HttpStatus.OK.toString());
				return new ResponseEntity<Object>(HttpStatus.OK);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			AppError error = new AppError(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex);
			return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = REST_NAME)
	@RequestMapping(value = REST_PATH, method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteResource(@RequestBody AF3AINPBST input) {
		setDefaults(input);
		try {
			List errors = validateInput(input);
			if (errors.size() > 0) {
				logger.info(HttpStatus.BAD_REQUEST.toString());
				AppError error = new AppError(HttpStatus.BAD_REQUEST.toString(), errors);
				return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
			} else {
				af3aService.checkRole(input);
				af3aService.deleteResource(input);
				logger.info(HttpStatus.NO_CONTENT.toString());
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			AppError error = new AppError(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex);
			return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = REST_NAME)
	@RequestMapping(value = REST_PATH + "/{gruppo}/{azienda}/{ndg}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> retrieveResource(
			@ApiParam(required = true) @PathVariable(value = "gruppo", required = true) String gruppo,
			@ApiParam(required = true) @PathVariable(value = "azienda", required = true) String azienda,
			@ApiParam(required = false) @RequestParam(value = "ruolo", required = false) String ruolo,
			@ApiParam(required = false) @RequestParam(value = "lingua", required = false) String lingua,
			@ApiParam(required = true) @PathVariable(value = "ndg", required = true) String ndg,
			@ApiParam(required = false) @PathVariable(value = "cdn", required = false) String cdn,
			@ApiParam(required = false) @PathVariable(value = "codiceLei", required = false) String codiceLei,
			@ApiParam(required = false) @PathVariable(value = "flagForzato", required = false) String flagForzato) {
		AF3AINPBST input = new AF3AINPBST();
		input.setGruppo(gruppo);
		input.setAzienda(azienda);
		input.setRuolo(ruolo);
		input.setLingua(lingua);
		input.setNdg(ndg);
		input.setCdn(cdn);
		input.setCodiceLei(codiceLei);
		input.setFlagForzatura(flagForzato);
		setDefaults(input);
		try {
			List errors = validateInput(input);
			if (errors.size() > 0) {
				logger.info(HttpStatus.BAD_REQUEST.toString());
				AppError error = new AppError(HttpStatus.BAD_REQUEST.toString(), errors);
				return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
			} else {
				af3aService.checkRole(input);
				AF3AOUTBST result = af3aService.retrieveResource(input);
				logger.info(af3aService.getJsonString(result));
				if (result != null) {
					logger.info(HttpStatus.OK.toString());
					return new ResponseEntity<Object>(result, HttpStatus.OK);

				} else {
					logger.info(HttpStatus.NO_CONTENT.toString());
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			AppError error = new AppError(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex);
			return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private List<OUTSEG> validateInput(AF3AINPBST input) {
		List<OUTSEG> errors = new LinkedList<OUTSEG>();
		List<ConstraintViolation> violations = validator.validate(input);
		ValidationHelper.addViolationsToErrors(errors, violations);
		return errors;
	}

	private void setDefaults(AF3AINPBST input) {

		if (StringUtils.isEmpty(input.getRuolo()))
			input.setRuolo("CUST");
		if (StringUtils.isEmpty(input.getLingua()))
			input.setLingua("IT");
	}
}