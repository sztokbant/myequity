package br.net.du.myequity.persistence;

import br.net.du.myequity.model.Snapshot;
import br.net.du.myequity.model.SnapshotSummary;
import br.net.du.myequity.model.User;
import br.net.du.myequity.model.totals.CumulativeTransactionTotals;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface SnapshotRepository extends JpaRepository<Snapshot, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<Snapshot> findById(Long id);

    @Lock(LockModeType.PESSIMISTIC_READ)
    Snapshot findTopByUserOrderByYearDescMonthDesc(User user);

    @Query(
            value =
                    "SELECT \n"
                            + "base_currency baseCurrency, \n"
                            + "SUM(s.incomes_total) incomesTotal, \n"
                            + "SUM(s.investments_total) investmentsTotal, \n"
                            + "SUM(s.donations_total) donationsTotal, \n"
                            + "SUM(s.tax_deductible_donations_total) taxDeductibleDonationsTotal, \n"
                            + "CASE SUM(s.incomes_total) WHEN 0 THEN 0 ELSE SUM(s.investments_total) * 100.0 / SUM(s.incomes_total) END investmentAvg, \n"
                            + "CASE SUM(s.incomes_total) WHEN 0 THEN 0 ELSE SUM(s.donations_total) * 100.0 / SUM(s.incomes_total) END donationAvg \n"
                            + "FROM (SELECT * FROM snapshots WHERE id <= ?1 AND user_id = ?2 ORDER BY year DESC, month DESC LIMIT 12) s \n"
                            + "GROUP BY baseCurrency \n",
            nativeQuery = true)
    List<CumulativeTransactionTotals> findPastTwelveMonthsCumulativeTransactionTotals(
            Long refSnapshotId, Long userId);

    @Query(
            value =
                    "SELECT \n"
                            + "base_currency baseCurrency, \n"
                            + "SUM(s.incomes_total) incomesTotal, \n"
                            + "SUM(s.investments_total) investmentsTotal, \n"
                            + "SUM(s.donations_total) donationsTotal, \n"
                            + "SUM(s.tax_deductible_donations_total) taxDeductibleDonationsTotal, \n"
                            + "CASE SUM(s.incomes_total) WHEN 0 THEN 0 ELSE SUM(s.investments_total) * 100.0 / SUM(s.incomes_total) END investmentAvg, \n"
                            + "CASE SUM(s.incomes_total) WHEN 0 THEN 0 ELSE SUM(s.donations_total) * 100.0 / SUM(s.incomes_total) END donationAvg \n"
                            + "FROM (SELECT * FROM snapshots WHERE year = ?1 AND month <= ?2 AND user_id = ?3) s \n"
                            + "GROUP BY baseCurrency \n",
            nativeQuery = true)
    List<CumulativeTransactionTotals> findYearToDateCumulativeTransactionTotals(
            Integer refYear, Integer refMonth, Long userId);

    List<SnapshotSummary> findAllByUserOrderByYearDescMonthDesc(User user);
}
