package com.bank.diwa0.configuration;

import it.eng.sclibs.security.SimpleIdentityProvider;
import it.eng.sclibs.security.SimpleUserIdentity;
import it.eng.sclibs.security.UserIdentity;
import it.eng.sclibs.visage.gestionedomini.service.RoleMapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Configuration
public class AppProfilesInitializer {

	private final Logger logger = LoggerFactory.getLogger(AppProfilesInitializer.class);

	@Autowired
	AppProperties appProperties;

	@Autowired
	private String allowedProfiles;

	@Autowired
	SimpleIdentityProvider jwtIdentityProvider;

	@Autowired
	RoleMapperService roleMapperService;

	@PostConstruct
	public void init() {

		try {
			String[] csvValues = allowedProfiles.split(",");
			List<String> values = Arrays.asList(csvValues);

			String gruppo = appProperties.getGruppo();
			List<String> profili = roleMapperService.map(values, gruppo);
			logger.info("roleMapperService.map");

			UserIdentity userId = jwtIdentityProvider.currentIdentity();
			SimpleUserIdentity identity = (SimpleUserIdentity)userId;
			identity.setProfili(profili);
		} catch (Exception ex) {
			logger.error("Cannot initialize profiles", ex);
			throw new RuntimeException(ex);
		}
	}
}