package br.net.du.myequity.model;

import br.net.du.myequity.util.NetWorthUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.SortNatural;
import org.joda.money.CurrencyUnit;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

@Entity
@Table(name = "snapshots", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "date"}))
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Snapshot implements Comparable<Snapshot> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter // for testing
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter
    private User user;

    @Column(nullable = false)
    @Getter
    @Setter
    private LocalDate date;

    @OneToMany(mappedBy = "snapshot", cascade = CascadeType.ALL, orphanRemoval = true)
    @SortNatural // Ref.: https://thorben-janssen.com/ordering-vs-sorting-hibernate-use/
    private SortedSet<AccountSnapshotMetadata> accountSnapshotMetadataSet = new TreeSet<>();

    public Snapshot(final LocalDate date,
                    @NotNull final SortedSet<AccountSnapshotMetadata> accountSnapshotMetadataSet) {
        this.date = date;
        this.accountSnapshotMetadataSet.addAll(accountSnapshotMetadataSet);
    }

    // TODO May never be used beyond unit-tests
    public SortedSet<AccountSnapshotMetadata> getAccountSnapshotMetadataSet() {
        return ImmutableSortedSet.copyOf(accountSnapshotMetadataSet);
    }

    public Map<AccountType, SortedSet<AccountSnapshotMetadata>> getAccountSnapshotMetadataByType() {
        return accountSnapshotMetadataSet.stream()
                                         .collect(collectingAndThen(groupingBy(accountSnapshotData -> accountSnapshotData
                                                                                       .getAccount()
                                                                                       .getAccountType(),
                                                                               collectingAndThen(toSet(),
                                                                                                 ImmutableSortedSet::copyOf)),
                                                                    ImmutableMap::copyOf));
    }

    public Optional<AccountSnapshotMetadata> getAccountSnapshotMetadataFor(@NonNull final Account account) {
        return accountSnapshotMetadataSet.stream().filter(entry -> account.equals(entry.getAccount())).findFirst();
    }

    public void addAccountSnapshotMetadata(@NonNull final AccountSnapshotMetadata accountSnapshotMetadata) {
        // Prevents infinite loop
        if (accountSnapshotMetadataSet.contains(accountSnapshotMetadata)) {
            return;
        }
        accountSnapshotMetadataSet.add(accountSnapshotMetadata);
        accountSnapshotMetadata.setSnapshot(this);
    }

    public void removeAccountSnapshotMetadataFor(@NonNull final Account account) {
        // Prevents infinite loop
        final Optional<AccountSnapshotMetadata> accountSnapshotDataOpt = getAccountSnapshotMetadataFor(account);
        if (!accountSnapshotDataOpt.isPresent()) {
            return;
        }

        final AccountSnapshotMetadata accountSnapshotMetadata = accountSnapshotDataOpt.get();
        accountSnapshotMetadataSet.remove(accountSnapshotMetadata);
        accountSnapshotMetadata.setSnapshot(null);
    }

    public void setUser(final User user) {
        // Prevents infinite loop
        if (sameAsFormer(user)) {
            return;
        }

        final User oldUser = this.user;
        this.user = user;

        if (oldUser != null) {
            oldUser.removeSnapshot(this);
        }

        if (user != null) {
            user.addSnapshot(this);
        }
    }

    private boolean sameAsFormer(final User newUser) {
        return user == null ?
                newUser == null :
                user.equals(newUser);
    }

    public Map<CurrencyUnit, BigDecimal> getNetWorth() {
        return NetWorthUtil.computeByCurrency(accountSnapshotMetadataSet);
    }

    public Map<CurrencyUnit, BigDecimal> getTotalForAccountType(@NonNull final AccountType accountType) {
        return NetWorthUtil.computeByCurrency(accountSnapshotMetadataSet.stream()
                                                                        .filter(entry -> entry.getAccount()
                                                                                              .getAccountType()
                                                                                              .equals(accountType))
                                                                        .collect(Collectors.toSet()));
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Snapshot)) {
            return false;
        }

        return id != null && id.equals(((Snapshot) other).getId());
    }

    @Override
    public int hashCode() {
        return 43;
    }

    @Override
    public int compareTo(final Snapshot other) {
        return other.getDate().compareTo(this.date);
    }
}
