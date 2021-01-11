package br.net.du.myequity.model;

import static br.net.du.myequity.test.TestConstants.CHARITY_DONATION;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DonationTest {

    @Test
    public void equals_differentIds() {
        // GIVEN`
        final Donation first = CHARITY_DONATION.copy();
        first.setId(42L);
        final Donation second = CHARITY_DONATION.copy();
        second.setId(77L);

        // THEN
        assertFalse(first.equals(second));
    }

    @Test
    public void equals_sameIds() {
        // GIVEN`
        final Donation first = CHARITY_DONATION.copy();
        first.setId(42L);
        final Donation second = CHARITY_DONATION.copy();
        second.setId(42L);

        // THEN
        assertTrue(first.equals(second));
    }
}
