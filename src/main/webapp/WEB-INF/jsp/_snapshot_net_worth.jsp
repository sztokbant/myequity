<div>
    <b>Net Worth</b>
</div>
<div>
    <c:choose>
        <c:when test="${not empty snapshot.netWorth}">
            <c:forEach items="${snapshot.netWorth}" var="entry">
                ${entry.key} <span id="snapshot_networth_${entry.key}">${entry.value}</span><br>
            </c:forEach>
        </c:when>
        <c:otherwise>
            0.00
        </c:otherwise>
    </c:choose>
</div>
