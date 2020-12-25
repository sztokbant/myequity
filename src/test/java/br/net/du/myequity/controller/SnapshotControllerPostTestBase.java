package br.net.du.myequity.controller;

import static br.net.du.myequity.test.ControllerTestUtils.verifyRedirect;
import static br.net.du.myequity.test.ModelTestUtils.buildUser;
import static br.net.du.myequity.test.TestConstants.now;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import br.net.du.myequity.model.Snapshot;
import br.net.du.myequity.model.User;
import br.net.du.myequity.model.account.Account;
import br.net.du.myequity.model.account.SimpleAssetAccount;
import br.net.du.myequity.service.SnapshotService;
import com.google.common.collect.ImmutableSortedSet;
import java.time.LocalDate;
import java.util.Optional;
import org.joda.money.CurrencyUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

abstract class SnapshotControllerPostTestBase extends PostControllerTestBase {

    protected static final long SNAPSHOT_ID = 99L;
    protected static final long SNAPSHOT_INDEX = 1L;
    protected static final String ACCOUNT_ID_VALUE = "42";

    @MockBean protected SnapshotService snapshotService;

    protected User anotherUser;

    protected Snapshot snapshot;

    protected Account account;

    public SnapshotControllerPostTestBase(final String url) {
        super(url);
    }

    @BeforeEach
    public void snapshotControllerPostTestBaseSetUp() throws Exception {
        anotherUser = buildUser();
        anotherUser.setId(user.getId() * 7);

        snapshot = new Snapshot(SNAPSHOT_INDEX, now, ImmutableSortedSet.of());
        snapshot.setId(SNAPSHOT_ID);

        account = new SimpleAssetAccount("Checking Account", CurrencyUnit.USD, LocalDate.now());
        account.setId(Long.parseLong(ACCOUNT_ID_VALUE));

        snapshot.addAccountSnapshot(account.newEmptySnapshot());
    }

    @Test
    public void post_snapshotNotFound_redirect() throws Exception {
        // GIVEN
        when(userService.findByEmail(user.getEmail())).thenReturn(user);
        when(snapshotService.findById(eq(SNAPSHOT_ID))).thenReturn(Optional.empty());

        // WHEN
        final ResultActions resultActions =
                mvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .with(csrf())
                                .with(user(user.getEmail()))
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED));

        // THEN
        verifyRedirect(resultActions, "/");
    }

    @Test
    public void post_snapshotDoesNotBelongToUser_redirect() throws Exception {
        // GIVEN
        snapshot.setUser(anotherUser);

        when(userService.findByEmail(user.getEmail())).thenReturn(user);
        when(snapshotService.findById(eq(SNAPSHOT_ID))).thenReturn(Optional.of(snapshot));

        // WHEN
        final ResultActions resultActions =
                mvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .with(csrf())
                                .with(user(user.getEmail()))
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED));

        // THEN
        verifyRedirect(resultActions, "/");
    }
}
