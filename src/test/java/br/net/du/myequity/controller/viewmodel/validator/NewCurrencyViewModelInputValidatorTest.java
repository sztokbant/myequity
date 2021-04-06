package br.net.du.myequity.controller.viewmodel.validator;

import static br.net.du.myequity.test.ModelTestUtils.SNAPSHOT_ID;
import static br.net.du.myequity.test.TestConstants.CONVERSION_RATE_FIELD;
import static br.net.du.myequity.test.TestConstants.CURRENCY_UNIT;
import static br.net.du.myequity.test.TestConstants.CURRENCY_UNIT_FIELD;
import static br.net.du.myequity.test.TestConstants.FIRST_SNAPSHOT_NAME;
import static br.net.du.myequity.test.TestConstants.TITHING_PERCENTAGE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.net.du.myequity.controller.viewmodel.NewCurrencyViewModelInput;
import br.net.du.myequity.model.Snapshot;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;
import java.math.BigDecimal;
import org.joda.money.CurrencyUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

class NewCurrencyViewModelInputValidatorTest {

    private NewCurrencyViewModelInputValidator validator;

    private NewCurrencyViewModelInput newCurrencyViewModelInput;

    private Snapshot snapshot;

    private Errors errors;

    @BeforeEach
    public void setUp() {
        snapshot =
                new Snapshot(
                        FIRST_SNAPSHOT_NAME,
                        CURRENCY_UNIT,
                        TITHING_PERCENTAGE,
                        ImmutableSortedSet.of(),
                        ImmutableList.of(),
                        ImmutableMap.of());
        snapshot.setId(SNAPSHOT_ID);

        snapshot.putCurrencyConversionRate(CurrencyUnit.EUR, new BigDecimal("0.85"));
        snapshot.putCurrencyConversionRate(CurrencyUnit.AUD, new BigDecimal("1.31"));

        newCurrencyViewModelInput = new NewCurrencyViewModelInput();

        validator = new NewCurrencyViewModelInputValidator();

        errors = new BeanPropertyBindingResult(newCurrencyViewModelInput, "newCurrencyForm");
    }

    @Test
    public void supports_happy() {
        assertTrue(validator.supports(NewCurrencyViewModelInput.class));
    }

    @Test
    public void supports_anotherClass_false() {
        assertFalse(validator.supports(String.class));
    }

    @Test
    public void validate_happy() {
        // GIVEN
        newCurrencyViewModelInput.setCurrencyUnit("BRL");
        newCurrencyViewModelInput.setConversionRate("5.71");

        // WHEN
        validator.validate(newCurrencyViewModelInput, errors, snapshot);

        // THEN
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validate_currencyUnitAlreadyPresent_hasErrors() {
        // GIVEN
        newCurrencyViewModelInput.setCurrencyUnit("EUR");
        newCurrencyViewModelInput.setConversionRate("1.00");

        // WHEN
        validator.validate(newCurrencyViewModelInput, errors, snapshot);

        // THEN
        assertTrue(errors.hasFieldErrors(CURRENCY_UNIT_FIELD));
    }

    @Test
    public void validate_baseCurrency_hasErrors() {
        // GIVEN
        newCurrencyViewModelInput.setCurrencyUnit("USD");
        newCurrencyViewModelInput.setConversionRate("1.00");

        // WHEN
        validator.validate(newCurrencyViewModelInput, errors, snapshot);

        // THEN
        assertTrue(errors.hasFieldErrors(CURRENCY_UNIT_FIELD));
    }

    @Test
    public void validate_invalidCurrency_hasErrors() {
        // GIVEN
        newCurrencyViewModelInput.setCurrencyUnit("XYZ");
        newCurrencyViewModelInput.setConversionRate("1.00");

        // WHEN
        validator.validate(newCurrencyViewModelInput, errors, snapshot);

        // THEN
        assertTrue(errors.hasFieldErrors(CURRENCY_UNIT_FIELD));
    }

    @Test
    public void validate_invalidConversionRate_hasErrors() {
        // GIVEN
        newCurrencyViewModelInput.setCurrencyUnit("BRL");
        newCurrencyViewModelInput.setConversionRate("NotANumber");

        // WHEN
        validator.validate(newCurrencyViewModelInput, errors, snapshot);

        // THEN
        assertTrue(errors.hasFieldErrors(CONVERSION_RATE_FIELD));
    }
}