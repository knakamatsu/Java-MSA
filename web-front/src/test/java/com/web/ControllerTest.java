package com.web;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.rules.AbstractController;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest extends AbstractController {

	@MockBean
	private UserService userService;

	@Test
	public void apiCheck() throws Exception {
		given(this.userService.example())
			.willReturn("Civic");

		mvc.setAuthentication();
		mvc.mockMvc.perform(get("/hello"))
			.andExpect(status().isOk()).andExpect(content().string("Civic"));
	}

	@Test
	public void unAuthorized() throws Exception {
		mvc.mockMvc.perform(get("/hello"))
			.andExpect(status().is3xxRedirection());
	}

}