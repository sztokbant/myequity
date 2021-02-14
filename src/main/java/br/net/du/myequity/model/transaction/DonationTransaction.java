package br.net.du.myequity.model.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue(DonationTransaction.TRANSACTION_TYPE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class DonationTransaction extends Transaction {
    static final String TRANSACTION_TYPE = "DONATION";

    @Transient @Getter
    private final TransactionType transactionType = TransactionType.valueOf(TRANSACTION_TYPE);

    @Column @Getter @Setter private boolean isTaxDeductible;

    public DonationTransaction(
            final LocalDate date,
            final String currency,
            final BigDecimal amount,
            final String description,
            final boolean isRecurring,
            final boolean isTaxDeductible) {
        super(date, currency, amount, description, isRecurring);
        this.isTaxDeductible = isTaxDeductible;
    }

    @Override
    public DonationTransaction copy() {
        return new DonationTransaction(
                date, currency, amount, description, isRecurring, isTaxDeductible);
    }

    @Override
    public boolean equalsIgnoreId(final Object other) {
        return super.equalsIgnoreId(other)
                && (other instanceof DonationTransaction)
                && isTaxDeductible == ((DonationTransaction) other).isTaxDeductible();
    }
}