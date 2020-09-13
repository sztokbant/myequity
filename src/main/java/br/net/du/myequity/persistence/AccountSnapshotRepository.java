package br.net.du.myequity.persistence;

import br.net.du.myequity.model.snapshot.AccountSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// TODO Create a Service
public interface AccountSnapshotRepository extends JpaRepository<AccountSnapshot, Long> {
    Optional<AccountSnapshot> findBySnapshotIdAndAccountId(Long snapshot, Long accountId);
}
