package br.net.du.myequity.model;

import static br.net.du.myequity.test.TestConstants.SALARY_INCOME;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class IncomeTest {

    @Test
    public void equals_differentIds() {
        // GIVEN`
        final Income first = SALARY_INCOME.copy();
        first.setId(42L);
        final Income second = SALARY_INCOME.copy();
        second.setId(77L);

        // THEN
        assertFalse(first.equals(second));
    }

    @Test
    public void equals_sameIds() {
        // GIVEN`
        final Income first = SALARY_INCOME.copy();
        first.setId(42L);
        final Income second = SALARY_INCOME.copy();
        second.setId(42L);

        // THEN
        assertTrue(first.equals(second));
    }
}
