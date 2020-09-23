package br.net.du.myequity.model.account;

import br.net.du.myequity.model.AccountType;
import br.net.du.myequity.model.snapshot.AccountSnapshot;
import br.net.du.myequity.model.snapshot.InvestmentSnapshot;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.joda.money.CurrencyUnit;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@DiscriminatorValue(InvestmentAccount.ACCOUNT_SUB_TYPE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class InvestmentAccount extends Account {

    public static final String ACCOUNT_SUB_TYPE = "INVESTMENT";

    public InvestmentAccount(final String name, final CurrencyUnit currencyUnit, final LocalDate createDate) {
        super(name, AccountType.ASSET, currencyUnit, createDate);
    }

    public InvestmentAccount(final String name, final CurrencyUnit currencyUnit) {
        this(name, currencyUnit, LocalDate.now());
    }

    @Override
    public AccountSnapshot newEmptySnapshot() {
        return new InvestmentSnapshot(this, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
