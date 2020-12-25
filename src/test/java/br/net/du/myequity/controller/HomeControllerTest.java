package br.net.du.myequity.controller;

import static br.net.du.myequity.test.ControllerTestUtils.verifyRedirect;
import static br.net.du.myequity.test.ModelTestUtils.buildUser;
import static br.net.du.myequity.test.TestConstants.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.net.du.myequity.controller.viewmodel.SnapshotViewModelOutput;
import br.net.du.myequity.controller.viewmodel.UserViewModelOutput;
import br.net.du.myequity.model.Snapshot;
import br.net.du.myequity.model.User;
import br.net.du.myequity.service.UserService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    private static final String HOME_URL = "/";

    private static final Long SNAPSHOT_ID = 99L;
    private static final long SNAPSHOT_INDEX = 1L;

    @Autowired private MockMvc mvc;

    @MockBean private UserService userService;

    private User user;

    private Snapshot snapshot;

    @BeforeEach
    public void setUp() throws Exception {
        user = buildUser();
        snapshot = new Snapshot(SNAPSHOT_INDEX, now, ImmutableSortedSet.of());
        snapshot.setId(SNAPSHOT_ID);
    }

    @Test
    public void get_userNotLoggedIn_redirectToLogin() throws Exception {
        // WHEN
        final ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get(HOME_URL));

        // THEN
        verifyRedirect(resultActions, "/login");
    }

    @Test
    public void get_happy() throws Exception {
        // GIVEN
        user.addSnapshot(snapshot);
        when(userService.findByEmail(user.getEmail())).thenReturn(user);

        // WHEN
        final ResultActions resultActions =
                mvc.perform(MockMvcRequestBuilders.get(HOME_URL).with(user(user.getEmail())));

        // THEN
        resultActions.andExpect(status().isOk());

        final MvcResult mvcResult = resultActions.andReturn();
        assertEquals("home", mvcResult.getModelAndView().getViewName());
        assertEquals(
                UserViewModelOutput.of(user), mvcResult.getModelAndView().getModel().get("user"));
        assertEquals(
                ImmutableList.of(SnapshotViewModelOutput.of(snapshot)),
                mvcResult.getModelAndView().getModel().get("snapshots"));
    }
}
