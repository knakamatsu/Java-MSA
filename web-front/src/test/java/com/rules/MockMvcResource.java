package com.rules;

import org.junit.rules.ExternalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Component
public class MockMvcResource extends ExternalResource {

    @Autowired
    private WebApplicationContext webAppContext;

    private SecurityContext securityContext;

    public MockMvc mockMvc;

    @Override
    protected void before() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Override
    protected void after() {
        SecurityContextHolder.clearContext();
    }

    public void setAuthentication(){
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        "sampleUser",
                        "samplePassword",
                        AuthorityUtils.createAuthorityList("ROLE_USER"));
        securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    public void setAuthentication(String testuser){
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                		testuser,
                        "samplePassword",
                        AuthorityUtils.createAuthorityList("ROLE_USER"));
        securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }
}