<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>New Donation</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>

<body>

<%@ include file="/WEB-INF/jsp/_header.jsp" %>

<div class="full-width">
    <div class="center-w640">
        <form:form method="POST" action="${contextPath}/snapshot/${snapshotId}/newTransaction"
                   modelAttribute="transactionForm" class="form-signin">
            <h4 class="form-signin-heading">New Donation</h4>

            <form:input type="hidden" path="typeName" value="${transactionType}"/>

            <%@ include file="_new_transaction_common.jsp" %>

            <spring:bind path="isTaxDeductible">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <div>
                        <form:radiobutton path="isTaxDeductible" value="true" id="taxDeductibleRadio" checked="checked"/>
                        <label for="taxDeductibleRadio">Tax Deductible</label>
                    </div>
                    <div>
                        <form:radiobutton path="isTaxDeductible" value="false" id="nondeductibleRadio"/>
                        <label for="nondeductibleRadio">Nondeductible</label>
                    </div>
                    <form:errors path="isTaxDeductible"/>
                </div>
            </spring:bind>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
            <div class="text-center"><a href="${contextPath}/snapshot/${snapshotId}">Back</a></div>
        </form:form>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>