package com.bank.diwa0;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bank.diwa0.configuration.AppConfiguration;
import com.bank.diwa0.service.TestSecurityService;
import org.springframework.test.context.ActiveProfiles;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { AppConfiguration.class })
@ActiveProfiles("test")
@TestExecutionListeners(listeners = { ServletTestExecutionListener.class,
		DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, WithSecurityContextTestExecutionListener.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WithMockUserTests {
    static {
        System.setProperty("Y1.Stage", "CENG");
    }

	@Autowired
	private TestSecurityService testSecurityService;

	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void getMessageUnauthenticated() {
		testSecurityService.createResource();
	}

	@Test
	@WithMockUser
	public void getMessageWithMockUser() {
		String message = testSecurityService.createResource();
		assertThat(message).contains("user");
	}

	@Test
	@WithMockUser("customUsername")
	public void getMessageWithMockUserCustomUsername() {
		String message = testSecurityService.createResource();
		assertThat(message).contains("customUsername");
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ANONYMOUS" })
	public void getMessageWithMockUserCustomUser() {
		String message = testSecurityService.createResource();
		assertThat(message).contains("admin").contains("ROLE_USER").contains("ROLE_ADMIN");
	}

/*	@Configuration
	@Order(1)
	@EnableWebSecurity
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	@ComponentScan(basePackageClasses = TestSecurityServiceImpl.class)
	static class Config extends WebSecurityConfigurerAdapter {
		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication().withUser("*").password("*").roles("*");

		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/anagrafica/api/v1/**")
					.access("hasRole('ANONYMOUS') or hasRole('ADMIN')").and().csrf().disable();

			//http.httpBasic().authenticationEntryPoint(entryPoint());
			http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());

		}

		@Bean("LTPAAuthenticationEntryPoint")
		public AuthenticationEntryPoint authenticationEntryPoint() {
			return new LTPAAuthenticationEntryPoint();
		}

		@Bean("EntryPoint")
		public AuthenticationEntryPoint entryPoint() {
			return new EntryPoint();
		}
	}
*/

}