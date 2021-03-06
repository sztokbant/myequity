package br.net.du.myequity.model.account;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.joda.money.CurrencyUnit;

@Entity
@DiscriminatorValue(SimpleAssetAccount.ACCOUNT_SUB_TYPE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class SimpleAssetAccount extends Account implements BalanceUpdateable, FutureTithingCapable {

    public static final String ACCOUNT_SUB_TYPE = "SIMPLE_ASSET";

    @Column(precision = 19, scale = 8)
    @Getter
    private BigDecimal balance;

    // Used by {@link br.net.du.myequity.controller.viewmodel.account.AccountFactory}
    public SimpleAssetAccount(
            @NonNull final String name,
            @NonNull final CurrencyUnit currencyUnit,
            @NonNull final FutureTithingPolicy futureTithingPolicy) {
        this(name, currencyUnit, futureTithingPolicy, LocalDate.now(), BigDecimal.ZERO);
    }

    public SimpleAssetAccount(
            @NonNull final String name,
            @NonNull final CurrencyUnit currencyUnit,
            final FutureTithingPolicy futureTithingPolicy,
            @NonNull final LocalDate createDate,
            @NonNull final BigDecimal balance) {
        super(name, AccountType.ASSET, currencyUnit, createDate);
        this.futureTithingPolicy = futureTithingPolicy;
        this.balance = balance;
    }

    @Override
    public SimpleAssetAccount copy() {
        return new SimpleAssetAccount(
                name, CurrencyUnit.of(currency), futureTithingPolicy, LocalDate.now(), balance);
    }

    @Override
    public void setBalance(@NonNull final BigDecimal newBalance) {
        if (balance.compareTo(newBalance) == 0) {
            return;
        }

        final BigDecimal oldBalance = balance;

        balance = newBalance;

        final BigDecimal balanceDiff = newBalance.subtract(oldBalance);
        getSnapshot().updateNetWorth(getAccountType(), getCurrencyUnit(), balanceDiff);

        if (!getFutureTithingPolicy().equals(FutureTithingPolicy.NONE)) {
            getSnapshot().updateFutureTithingAmount(getCurrencyUnit(), balanceDiff);
        }
    }

    @Override
    public FutureTithingPolicy getFutureTithingPolicy() {
        return futureTithingPolicy == null ? FutureTithingPolicy.NONE : futureTithingPolicy;
    }

    @Override
    public void setFutureTithingPolicy(@NonNull final FutureTithingPolicy futureTithingPolicy) {
        if (getFutureTithingPolicy().equals(futureTithingPolicy)) {
            return;
        }

        final BigDecimal oldReferenceAmount = getFutureTithingReferenceAmount();

        this.futureTithingPolicy = futureTithingPolicy;

        final BigDecimal newReferenceAmount = getFutureTithingReferenceAmount();

        final BigDecimal referenceAmountDiff = newReferenceAmount.subtract(oldReferenceAmount);
        getSnapshot().updateFutureTithingAmount(getCurrencyUnit(), referenceAmountDiff);
    }

    @Override
    public BigDecimal getFutureTithingReferenceAmount() {
        if (getFutureTithingPolicy().equals(FutureTithingPolicy.NONE)) {
            return BigDecimal.ZERO;
        }
        return getBalance();
    }
}
