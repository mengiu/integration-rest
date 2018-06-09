package com.bank.diwa0.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.oval.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import com.bank.diwa0.configuration.AppProperties;
import com.bank.diwa0.model.AppError;
import com.bank.diwa0.service.TestSecurityService;
import springfox.documentation.annotations.ApiIgnore;

@Api
@ApiIgnore
@RestController
public class TestSecurityApi2 {
	private final Logger logger = LoggerFactory.getLogger(TestSecurityApi2.class);

	private static final String REST_NAME = "testsecurity";
	private static final String REST_PATH = AppProperties.APPLICATION_REST_PREFIX + REST_NAME;

	@Autowired
	Validator validator;

	@Autowired
	TestSecurityService testSecurityService;

	@ApiOperation(value = REST_NAME)
	@RequestMapping(value = REST_PATH, method = RequestMethod.POST)
	public ResponseEntity<Object> createResource() {
		try {

			return new ResponseEntity<Object>(testSecurityService.createResource(), HttpStatus.OK);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			AppError error = new AppError(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex);
			return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}