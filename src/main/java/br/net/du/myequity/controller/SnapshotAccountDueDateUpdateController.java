package br.net.du.myequity.controller;

import br.net.du.myequity.controller.model.SnapshotAccountUpdateJsonRequest;
import br.net.du.myequity.controller.model.SnapshotAccountUpdateJsonResponse;
import br.net.du.myequity.model.snapshot.AccountSnapshot;
import br.net.du.myequity.model.snapshot.DueDateUpdateable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.function.BiFunction;

@RestController
public class SnapshotAccountDueDateUpdateController extends SnapshotAccountUpdateControllerBase {

    @PostMapping("/snapshot/updateAccountDueDate")
    public SnapshotAccountUpdateJsonResponse post(final Model model,
                                                  @RequestBody final SnapshotAccountUpdateJsonRequest snapshotAccountUpdateJsonRequest) {

        final BiFunction<SnapshotAccountUpdateJsonRequest, AccountSnapshot, SnapshotAccountUpdateJsonResponse>
                updateDueDateFunction =
                new BiFunction<SnapshotAccountUpdateJsonRequest, AccountSnapshot, SnapshotAccountUpdateJsonResponse>() {
                    @Override
                    public SnapshotAccountUpdateJsonResponse apply(final SnapshotAccountUpdateJsonRequest snapshotAccountUpdateJsonRequest,
                                                                   final AccountSnapshot accountSnapshot) {
                        final LocalDate dueDate = LocalDate.parse(snapshotAccountUpdateJsonRequest.getNewValue());
                        ((DueDateUpdateable) accountSnapshot).setDueDate(dueDate);

                        return getDefaultResponseBuilder(accountSnapshot).dueDate(dueDate.toString()).build();
                    }
                };

        return updateAccountSnapshotField(model,
                                          snapshotAccountUpdateJsonRequest,
                                          DueDateUpdateable.class,
                                          updateDueDateFunction);
    }
}
