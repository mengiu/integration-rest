package com.bank.diwa0;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.bank.diwa0.configuration.AppConfiguration;
import com.bank.diwa0.configuration.AppProperties;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")
@ContextConfiguration(classes = {AppConfiguration.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WithMockUser(roles = { "ANONYMOUS" })
public class SGIITest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private final static ObjectMapper MAPPER = new ObjectMapper();
    private static final String REST_NAME = "sgii";
    private static final String REST_PATH = AppProperties.APPLICATION_REST_PREFIX + REST_NAME;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @Test
    public void test_createResource() throws Exception {
        // todo
    }
}