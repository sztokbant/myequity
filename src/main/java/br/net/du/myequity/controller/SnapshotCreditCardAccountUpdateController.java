package br.net.du.myequity.controller;

import br.net.du.myequity.controller.model.SnapshotAccountUpdateJsonRequest;
import br.net.du.myequity.controller.model.SnapshotAccountUpdateJsonResponse;
import br.net.du.myequity.model.Snapshot;
import br.net.du.myequity.model.snapshot.AccountSnapshot;
import br.net.du.myequity.model.snapshot.CreditCardSnapshot;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static br.net.du.myequity.controller.util.ControllerConstants.DECIMAL_FORMAT;

@RestController
public class SnapshotCreditCardAccountUpdateController extends SnapshotAccountUpdateControllerBase {
    @PostMapping("/updateCreditCardTotalCredit")
    public SnapshotAccountUpdateJsonResponse updateCreditCardTotalCredit(final Model model,
                                                                         @RequestBody final SnapshotAccountUpdateJsonRequest snapshotAccountUpdateJsonRequest) {
        final Snapshot snapshot = getSnapshot(model, snapshotAccountUpdateJsonRequest);
        assert snapshot != null;

        final CreditCardSnapshot creditCardSnapshot = getCreditCardSnapshot(snapshotAccountUpdateJsonRequest);

        creditCardSnapshot.setTotalCredit(snapshotAccountUpdateJsonRequest.getNewValue());

        accountSnapshotRepository.save(creditCardSnapshot);

        return getDefaultResponseBuilder(snapshot, creditCardSnapshot).totalCredit(DECIMAL_FORMAT.format(
                creditCardSnapshot.getTotalCredit().setScale(2))).build();
    }

    @PostMapping("/updateCreditCardAvailableCredit")
    public SnapshotAccountUpdateJsonResponse updateCreditCardAvailableCredit(final Model model,
                                                                             @RequestBody final SnapshotAccountUpdateJsonRequest snapshotAccountUpdateJsonRequest) {
        final Snapshot snapshot = getSnapshot(model, snapshotAccountUpdateJsonRequest);
        assert snapshot != null;

        final CreditCardSnapshot creditCardSnapshot = getCreditCardSnapshot(snapshotAccountUpdateJsonRequest);

        creditCardSnapshot.setAvailableCredit(snapshotAccountUpdateJsonRequest.getNewValue());

        accountSnapshotRepository.save(creditCardSnapshot);

        return getDefaultResponseBuilder(snapshot, creditCardSnapshot).availableCredit(DECIMAL_FORMAT.format(
                creditCardSnapshot.getAvailableCredit().setScale(2))).build();
    }

    private CreditCardSnapshot getCreditCardSnapshot(@RequestBody final SnapshotAccountUpdateJsonRequest snapshotAccountUpdateJsonRequest) {
        final AccountSnapshot accountSnapshot =
                accountSnapshotRepository.findByAccountId(snapshotAccountUpdateJsonRequest.getAccountId()).get();

        if (!(accountSnapshot instanceof CreditCardSnapshot)) {
            throw new IllegalArgumentException("accountSnapshot not an instance of " + CreditCardSnapshot.class.getSimpleName());
        }

        return (CreditCardSnapshot) accountSnapshot;
    }
}