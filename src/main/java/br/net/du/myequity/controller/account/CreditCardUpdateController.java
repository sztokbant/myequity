package br.net.du.myequity.controller.account;

import br.net.du.myequity.controller.viewmodel.ValueUpdateJsonRequest;
import br.net.du.myequity.controller.viewmodel.account.AccountViewModelOutput;
import br.net.du.myequity.controller.viewmodel.account.CreditCardAccountViewModelOutput;
import br.net.du.myequity.model.account.Account;
import br.net.du.myequity.model.account.CreditCardAccount;
import java.math.BigDecimal;
import java.util.function.BiFunction;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreditCardUpdateController extends AccountUpdateControllerBase {
    @PostMapping("/snapshot/updateCreditCardTotalCredit")
    public AccountViewModelOutput updateCreditCardTotalCredit(
            final Model model, @RequestBody final ValueUpdateJsonRequest valueUpdateJsonRequest) {

        final BiFunction<ValueUpdateJsonRequest, Account, AccountViewModelOutput>
                updateCreditCardTotalCreditFunction =
                        (jsonRequest, account) -> {
                            final CreditCardAccount creditCardSnapshot =
                                    (CreditCardAccount) account;

                            final BigDecimal newValue = new BigDecimal(jsonRequest.getNewValue());
                            creditCardSnapshot.setTotalCredit(newValue);

                            return CreditCardAccountViewModelOutput.of(creditCardSnapshot, true);
                        };

        return updateAccountField(
                model,
                valueUpdateJsonRequest,
                CreditCardAccount.class,
                updateCreditCardTotalCreditFunction);
    }

    @PostMapping("/snapshot/updateCreditCardAvailableCredit")
    public AccountViewModelOutput updateCreditCardAvailableCredit(
            final Model model, @RequestBody final ValueUpdateJsonRequest valueUpdateJsonRequest) {
        final BiFunction<ValueUpdateJsonRequest, Account, AccountViewModelOutput>
                updateCreditCardAvailableCreditFunction =
                        (jsonRequest, account) -> {
                            final CreditCardAccount creditCardSnapshot =
                                    (CreditCardAccount) account;

                            final BigDecimal newValue = new BigDecimal(jsonRequest.getNewValue());
                            creditCardSnapshot.setAvailableCredit(newValue);

                            return CreditCardAccountViewModelOutput.of(creditCardSnapshot, true);
                        };

        return updateAccountField(
                model,
                valueUpdateJsonRequest,
                CreditCardAccount.class,
                updateCreditCardAvailableCreditFunction);
    }

    @PostMapping("/snapshot/updateCreditCardStatement")
    public AccountViewModelOutput updateCreditCardStatement(
            final Model model, @RequestBody final ValueUpdateJsonRequest valueUpdateJsonRequest) {
        final BiFunction<ValueUpdateJsonRequest, Account, AccountViewModelOutput>
                updateCreditCardStatementFunction =
                        (jsonRequest, account) -> {
                            final CreditCardAccount creditCardSnapshot =
                                    (CreditCardAccount) account;

                            final BigDecimal newValue = new BigDecimal(jsonRequest.getNewValue());
                            creditCardSnapshot.setStatement(newValue);

                            return CreditCardAccountViewModelOutput.of(creditCardSnapshot, true);
                        };

        return updateAccountField(
                model,
                valueUpdateJsonRequest,
                CreditCardAccount.class,
                updateCreditCardStatementFunction);
    }
}