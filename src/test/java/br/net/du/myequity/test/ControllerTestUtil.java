package br.net.du.myequity.test;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ControllerTestUtil {
    public static void verifyRedirect(final ResultActions resultActions, final String location) throws Exception {
        resultActions.andExpect(status().is3xxRedirection());

        final MvcResult mvcResult = resultActions.andReturn();
        assertTrue(mvcResult.getResponse().getHeader("Location").endsWith(location));
    }
}