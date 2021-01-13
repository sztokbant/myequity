<div class="row">
    <div class="col" style="background: lightpink;">
        <div class="row border-1px-bottom">
            <div class="col col-cell text-center"><b>ASSETS</b></div>
        </div>
        <c:choose>
            <c:when test="${not empty snapshot.assetsBalance}">
                <c:forEach items="${snapshot.assetsBalance}" var="entry">
                    <div class="row border-1px-bottom bg-light-yellow">
                        <div class="col col-cell col-account-name">TOTAL ${entry.key}</div>
                        <div class="col col-cell">&nbsp;</div>
                        <div class="col col-cell">&nbsp;</div>
                        <div class="col col-cell">&nbsp;</div>
                        <div class="col col-cell">&nbsp;</div>
                        <div class="col col-cell align-right"><b><span
                                id="total_ASSET_${entry.key}">${entry.value}</span></b></div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="row border-1px-bottom bg-light-yellow">
                    <div class="col col-cell col-account-name">TOTAL</div>
                    <div class="col col-cell">&nbsp;</div>
                    <div class="col col-cell">&nbsp;</div>
                    <div class="col col-cell">&nbsp;</div>
                    <div class="col col-cell">&nbsp;</div>
                    <div class="col col-cell align-right"><b>0.00</b></div>
                </div>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${not empty snapshot.simpleAssetAccounts}">
                <div class="row border-1px-bottom">
                    <div class="col col-cell col-title"><i>Simple Assets</i></div>
                    <div class="col col-cell col-title">&nbsp;</div>
                    <div class="col col-cell col-title">&nbsp;</div>
                    <div class="col col-cell col-title">&nbsp;</div>
                    <div class="col col-cell col-title">&nbsp;</div>
                    <div class="col col-cell col-title">Balance</div>
                </div>
                <c:forEach var="account" items="${snapshot.simpleAssetAccounts}">
                    <%@ include file="_snapshot_simple_asset_line_item.jsp" %>
                </c:forEach>
            </c:when>
        </c:choose>

        <c:choose>
            <c:when test="${not empty snapshot.receivableAccounts}">
                <div class="row border-1px-bottom">
                    <div class="col col-cell col-title"><i>Receivables</i></div>
                    <div class="col col-cell col-title">Due Date</div>
                    <div class="col col-cell col-title">&nbsp;</div>
                    <div class="col col-cell col-title">&nbsp;</div>
                    <div class="col col-cell col-title">&nbsp;</div>
                    <div class="col col-cell col-title">Balance</div>
                </div>
                <c:forEach var="account" items="${snapshot.receivableAccounts}">
                    <%@ include file="_snapshot_receivable_line_item.jsp" %>
                </c:forEach>
            </c:when>
        </c:choose>

        <c:choose>
            <c:when test="${not empty snapshot.investmentAccounts}">
                <div class="row border-1px-bottom">
                    <div class="col col-cell col-title"><i>Investments</i></div>
                    <div class="col col-cell col-title">Shares</div>
                    <div class="col col-cell col-title">Original Share Value</div>
                    <div class="col col-cell col-title">Current Share Value</div>
                    <div class="col col-cell col-title">Profit</div>
                    <div class="col col-cell col-title">Balance</div>
                </div>
                <c:forEach var="account" items="${snapshot.investmentAccounts}">
                    <%@ include file="_snapshot_investment_line_item.jsp" %>
                </c:forEach>
            </c:when>
        </c:choose>
    </div>
    <div class="col" style="background: lightblue;">
        <div class="row border-1px-bottom">
            <div class="col col-cell text-center"><b>LIABILITIES</b></div>
        </div>

        <c:choose>
            <c:when test="${not empty snapshot.liabilitiesBalance}">
                <c:forEach items="${snapshot.liabilitiesBalance}" var="entry">
                    <div class="row border-1px-bottom bg-light-yellow">
                        <div class="col col-cell col-account-name">TOTAL ${entry.key}</div>
                        <div class="col col-cell">&nbsp;</div>
                        <div class="col col-cell">&nbsp;</div>
                        <div class="col col-cell">&nbsp;</div>
                        <div class="col col-cell">&nbsp;</div>
                        <div class="col col-cell">&nbsp;</div>
                        <div class="col col-cell align-right"><b><span
                                id="total_LIABILITY_${entry.key}">${entry.value}</span></b>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="row border-1px-bottom bg-light-yellow">
                    <div class="col col-cell col-account-name">TOTAL</div>
                    <div class="col col-cell">&nbsp;</div>
                    <div class="col col-cell">&nbsp;</div>
                    <div class="col col-cell">&nbsp;</div>
                    <div class="col col-cell">&nbsp;</div>
                    <div class="col col-cell">&nbsp;</div>
                    <div class="col col-cell align-right"><b>0.00</b></div>
                </div>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${not empty snapshot.simpleLiabilityAccounts}">
                <div class="row border-1px-bottom">
                    <div class="col col-cell col-title"><i>Simple Liabilities</i></div>
                    <div class="col col-cell col-title">&nbsp;</div>
                    <div class="col col-cell col-title">&nbsp;</div>
                    <div class="col col-cell col-title">&nbsp;</div>
                    <div class="col col-cell col-title">&nbsp;</div>
                    <div class="col col-cell col-title">&nbsp;</div>
                    <div class="col col-cell col-title">Balance</div>
                </div>
                <c:forEach var="account" items="${snapshot.simpleLiabilityAccounts}">
                    <%@ include file="_snapshot_simple_liability_line_item.jsp" %>
                </c:forEach>
            </c:when>
        </c:choose>

        <c:choose>
            <c:when test="${not empty snapshot.payableAccounts}">
                <div class="row border-1px-bottom">
                    <div class="col col-cell col-title"><i>Payables</i></div>
                    <div class="col col-cell col-title">Due Date</div>
                    <div class="col col-cell col-title">&nbsp;</div>
                    <div class="col col-cell col-title">&nbsp;</div>
                    <div class="col col-cell col-title">&nbsp;</div>
                    <div class="col col-cell col-title">&nbsp;</div>
                    <div class="col col-cell col-title">Balance</div>
                </div>
                <c:forEach var="account" items="${snapshot.payableAccounts}">
                    <%@ include file="_snapshot_payable_line_item.jsp" %>
                </c:forEach>
            </c:when>
        </c:choose>

        <c:choose>
            <c:when test="${not empty snapshot.creditCardAccounts}">
                <div class="row border-1px-bottom">
                    <div class="col col-cell col-title"><i>Credit Cards</i></div>
                    <div class="col col-cell col-title">Total Credit</div>
                    <div class="col col-cell col-title">Available Credit</div>
                    <div class="col col-cell col-title">Used Credit</div>
                    <div class="col col-cell col-title">Statement</div>
                    <div class="col col-cell col-title">Remaining Balance</div>
                    <div class="col col-cell col-title">Balance</div>
                </div>

                <c:set var="currentCurrency" value=""/>
                <c:forEach var="account" items="${snapshot.creditCardAccounts}">
                    <c:if test="${currentCurrency ne '' && currentCurrency ne account.currencyUnit}">
                        <%@ include file="_snapshot_credit_card_total_for_currency.jsp" %>
                    </c:if>
                    <c:set var="currentCurrency" value="${account.currencyUnit}"/>
                    <%@ include file="_snapshot_credit_card_line_item.jsp" %>
                </c:forEach>
                <%@ include file="_snapshot_credit_card_total_for_currency.jsp" %>
            </c:when>
        </c:choose>
    </div>
</div>
