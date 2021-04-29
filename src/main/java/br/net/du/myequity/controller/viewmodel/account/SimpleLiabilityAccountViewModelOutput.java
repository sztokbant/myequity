package br.net.du.myequity.controller.viewmodel.account;

import br.net.du.myequity.model.account.Account;
import lombok.Getter;

@Getter
public class SimpleLiabilityAccountViewModelOutput extends AccountViewModelOutput {
    public SimpleLiabilityAccountViewModelOutput(final AccountViewModelOutput other) {
        super(other);
    }

    public static SimpleLiabilityAccountViewModelOutput of(
            final Account account, final boolean includeTotals) {
        return new SimpleLiabilityAccountViewModelOutput(
                AccountViewModelOutput.of(account, includeTotals));
    }

    public static SimpleLiabilityAccountViewModelOutput of(final Account account) {
        return of(account, false);
    }
}
