package br.net.du.myequity.model.transaction;

import br.net.du.myequity.model.Snapshot;
import br.net.du.myequity.model.util.SnapshotUtils;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.joda.money.CurrencyUnit;

@Entity
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = Transaction.DISCRIMINATOR_COLUMN)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class Transaction implements Comparable<Transaction> {
    static final String DISCRIMINATOR_COLUMN = "transaction_type";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter
    private Snapshot snapshot;

    @Column @Getter @Setter protected LocalDate date;

    @Column(nullable = false)
    @Getter
    protected String currency;

    @Column(nullable = false)
    @Getter
    protected BigDecimal amount;

    @Column(nullable = false)
    @Getter
    @Setter
    protected String description;

    @Column(nullable = false)
    @Getter
    @Setter
    protected boolean isRecurring;

    @Column(nullable = false)
    @Getter
    @Setter
    protected boolean isResettable;

    @Column protected String category;

    public Transaction(
            @NonNull final LocalDate date,
            @NonNull final String currency,
            @NonNull final BigDecimal amount,
            @NonNull final String description,
            final boolean isRecurring,
            final boolean isResettable) {
        this.date = date;
        this.currency = currency;
        this.amount = amount;
        this.description = description;
        this.isRecurring = isRecurring;
        this.isResettable = isResettable;
    }

    public abstract Transaction copy();

    public abstract TransactionType getTransactionType();

    public abstract void setAmount(BigDecimal amount);

    public CurrencyUnit getCurrencyUnit() {
        return CurrencyUnit.of(currency);
    }

    public void setSnapshot(final Snapshot newSnapshot) {
        // Prevents infinite loop
        if (SnapshotUtils.equals(snapshot, newSnapshot)) {
            return;
        }

        final Snapshot oldSnapshot = snapshot;
        snapshot = newSnapshot;

        if (oldSnapshot != null) {
            oldSnapshot.removeTransaction(this);
        }

        if (newSnapshot != null) {
            newSnapshot.addTransaction(this);
        }
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Transaction)) {
            return false;
        }

        final Transaction otherTransaction = (Transaction) other;

        return (id != null) && id.equals(otherTransaction.getId());
    }

    @Override
    public int hashCode() {
        return 61;
    }

    @Override
    public int compareTo(@NonNull final Transaction other) {
        if (currency.equals(other.getCurrency())) {
            if (date.equals(other.getDate())) {
                if (description.equals(other.getDescription())) {
                    return id.compareTo(other.getId());
                }
                return description.compareTo(other.getDescription());
            }
            return date.compareTo(other.getDate());
        }
        return currency.compareTo(other.getCurrency());
    }

    protected void updateSnapshotTransactionTotal(
            @NonNull final BigDecimal newAmount, @NonNull final BigDecimal oldAmount) {
        updateSnapshotTransactionTotal(newAmount, oldAmount, false);
    }

    protected void updateSnapshotTransactionTotal(
            @NonNull final BigDecimal newAmount,
            @NonNull final BigDecimal oldAmount,
            final boolean isTaxDeductibleDonation) {
        final BigDecimal diffAmount = newAmount.subtract(oldAmount);
        snapshot.updateTransactionsTotal(
                getTransactionType(), getCurrencyUnit(), diffAmount, isTaxDeductibleDonation);
    }
}
