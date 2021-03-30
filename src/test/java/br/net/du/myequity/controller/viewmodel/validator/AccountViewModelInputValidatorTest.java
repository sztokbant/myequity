package br.net.du.myequity.controller.viewmodel.validator;

import static br.net.du.myequity.test.TestConstants.FIRST_SNAPSHOT_NAME;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import br.net.du.myequity.controller.viewmodel.AccountViewModelInput;
import br.net.du.myequity.model.Snapshot;
import br.net.du.myequity.model.account.Account;
import br.net.du.myequity.model.account.SimpleAssetAccount;
import br.net.du.myequity.service.AccountService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.joda.money.CurrencyUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

class AccountViewModelInputValidatorTest {

    private static final String ACCOUNT_NAME = "My Account";
    private static final String TYPE_NAME = "SimpleAssetAccount";
    private static final String ANOTHER_ACCOUNT_NAME = "Another Account";
    private static final String CURRENCY_UNIT = "USD";

    @Mock private AccountService accountService;

    private AccountViewModelInputValidator accountViewModelInputValidator;

    private AccountViewModelInput accountViewModelInput;

    private Snapshot snapshot;

    private Errors errors;

    @BeforeEach
    public void setUp() {
        initMocks(this);

        snapshot = new Snapshot(FIRST_SNAPSHOT_NAME, ImmutableSortedSet.of(), ImmutableList.of());
        snapshot.setId(42L);

        accountViewModelInputValidator = new AccountViewModelInputValidator(accountService);

        accountViewModelInput = new AccountViewModelInput();

        errors = new BeanPropertyBindingResult(accountViewModelInput, "accountForm");
    }

    @Test
    public void supports_happy() {
        assertTrue(accountViewModelInputValidator.supports(AccountViewModelInput.class));
    }

    @Test
    public void supports_anotherClass_false() {
        assertFalse(accountViewModelInputValidator.supports(String.class));
    }

    @Test
    public void validate_happyFirstAccount() {
        // GIVEN
        populateAccountForm(ACCOUNT_NAME, TYPE_NAME, CURRENCY_UNIT);
        defineExistingAccounts(ImmutableList.of());

        // WHEN
        accountViewModelInputValidator.validate(accountViewModelInput, errors, snapshot);

        // THEN
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validate_happyExintingAccountWithDifferentName() {
        // GIVEN
        populateAccountForm(ACCOUNT_NAME, TYPE_NAME, CURRENCY_UNIT);
        defineExistingAccounts(
                ImmutableList.of(new SimpleAssetAccount(ANOTHER_ACCOUNT_NAME, CurrencyUnit.USD)));

        // WHEN
        accountViewModelInputValidator.validate(accountViewModelInput, errors, snapshot);

        // THEN
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validate_emptyObject_hasErrors() {
        // GIVEN
        populateAccountForm(null, null, null);
        defineExistingAccounts(ImmutableList.of());

        // WHEN
        accountViewModelInputValidator.validate(accountViewModelInput, errors, snapshot);

        // THEN
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_nullName_hasErrors() {
        // GIVEN
        populateAccountForm(null, TYPE_NAME, CURRENCY_UNIT);
        defineExistingAccounts(ImmutableList.of());

        // WHEN
        accountViewModelInputValidator.validate(accountViewModelInput, errors, snapshot);

        // THEN
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_nullType_hasErrors() {
        // GIVEN
        populateAccountForm(ACCOUNT_NAME, null, CURRENCY_UNIT);
        defineExistingAccounts(ImmutableList.of());

        // WHEN
        accountViewModelInputValidator.validate(accountViewModelInput, errors, snapshot);

        // THEN
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_nullCurrency_hasErrors() {
        // GIVEN
        populateAccountForm(ACCOUNT_NAME, TYPE_NAME, null);
        defineExistingAccounts(ImmutableList.of());

        // WHEN
        accountViewModelInputValidator.validate(accountViewModelInput, errors, snapshot);

        // THEN
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_emptyName_hasErrors() {
        // GIVEN
        populateAccountForm(StringUtils.EMPTY, TYPE_NAME, CURRENCY_UNIT);
        defineExistingAccounts(ImmutableList.of());

        // WHEN
        accountViewModelInputValidator.validate(accountViewModelInput, errors, snapshot);

        // THEN
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_emptyCurrency_hasErrors() {
        // GIVEN
        populateAccountForm(ACCOUNT_NAME, TYPE_NAME, StringUtils.EMPTY);
        defineExistingAccounts(ImmutableList.of());

        // WHEN
        accountViewModelInputValidator.validate(accountViewModelInput, errors, snapshot);

        // THEN
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_invalidCurrency_hasErrors() {
        // GIVEN
        populateAccountForm(ACCOUNT_NAME, TYPE_NAME, "xyz");
        defineExistingAccounts(ImmutableList.of());

        // WHEN
        accountViewModelInputValidator.validate(accountViewModelInput, errors, snapshot);

        // THEN
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_existingName_hasErrors() {
        // GIVEN
        populateAccountForm(ACCOUNT_NAME, TYPE_NAME, CURRENCY_UNIT);
        defineExistingAccounts(
                ImmutableList.of(new SimpleAssetAccount(ACCOUNT_NAME, CurrencyUnit.USD)));

        // WHEN
        accountViewModelInputValidator.validate(accountViewModelInput, errors, snapshot);

        // THEN
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_existingNameExtraSpaces_hasErrors() {
        // GIVEN
        populateAccountForm(" " + ACCOUNT_NAME + " ", TYPE_NAME, CURRENCY_UNIT);
        defineExistingAccounts(
                ImmutableList.of(new SimpleAssetAccount(ACCOUNT_NAME, CurrencyUnit.USD)));

        // WHEN
        accountViewModelInputValidator.validate(accountViewModelInput, errors, snapshot);

        // THEN
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_noValidationHints_throws() {
        // GIVEN
        populateAccountForm(ACCOUNT_NAME, TYPE_NAME, CURRENCY_UNIT);

        // WHEN/THEN
        assertThrows(
                UnsupportedOperationException.class,
                () -> {
                    accountViewModelInputValidator.validate(accountViewModelInput, errors);
                });
    }

    @Test
    public void validate_emptyValidationHints_throws() {
        // GIVEN
        populateAccountForm(ACCOUNT_NAME, TYPE_NAME, CURRENCY_UNIT);

        // WHEN/THEN
        assertThrows(
                UnsupportedOperationException.class,
                () -> {
                    accountViewModelInputValidator.validate(
                            accountViewModelInput, errors, new Object[] {});
                });
    }

    @Test
    public void validate_notUserInValidationHints_throws() {
        // GIVEN
        populateAccountForm(ACCOUNT_NAME, TYPE_NAME, CURRENCY_UNIT);

        // WHEN/THEN
        assertThrows(
                UnsupportedOperationException.class,
                () -> {
                    accountViewModelInputValidator.validate(
                            accountViewModelInput, errors, "A String!");
                });
    }

    private void populateAccountForm(final String name, final String type, final String currency) {
        accountViewModelInput.setName(name);
        accountViewModelInput.setTypeName(type);
        accountViewModelInput.setCurrencyUnit(currency);
    }

    private void defineExistingAccounts(final List<Account> accounts) {
        when(accountService.findBySnapshot(snapshot)).thenReturn(accounts);
    }
}