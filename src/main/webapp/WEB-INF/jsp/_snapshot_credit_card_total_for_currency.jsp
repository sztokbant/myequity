<c:set var="ccTotals" value="${snapshot.creditCardTotals[currentCurrency]}"/>
<div class="row border-1px-bottom bg-light-yellow">
    <div class="col col-cell col-account-name">Credit Card TOTAL ${currentCurrency}</div>
    <div class="col col-cell align-right"><b><span id="snapshot_credit_card_total_credit_${currentCurrency}">${ccTotals.totalCredit}</span></b>
    </div>
    <div class="col col-cell align-right"><b><span id="snapshot_credit_card_available_credit_${currentCurrency}">${ccTotals.availableCredit}</span></b>
    </div>
    <div class="col col-cell align-right"><b><span id="snapshot_credit_card_used_credit_percentage_${currentCurrency}">${ccTotals.usedCreditPercentage}</span></b>
    </div>
    <div class="col col-cell align-right"><b><span id="snapshot_credit_card_statement_${currentCurrency}">${ccTotals.statement}</span></b>
    </div>
    <div class="col col-cell align-right"><b><span id="snapshot_credit_card_remaining_balance_${currentCurrency}">${ccTotals.remainingBalance}</span></b>
    </div>
    <div class="col col-cell align-right"><b><span id="snapshot_credit_card_balance_${currentCurrency}">${ccTotals.balance}</span></b>
    </div>
</div>