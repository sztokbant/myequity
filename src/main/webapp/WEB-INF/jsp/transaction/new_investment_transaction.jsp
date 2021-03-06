<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>New Investment</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>

<body>

<%@ include file="/WEB-INF/jsp/_header.jsp" %>

<div class="full-width">
    <div class="center-w640">
        <form:form method="POST" action="${contextPath}/snapshot/${snapshotId}/newTransaction"
                   modelAttribute="transactionForm" class="form-signin">
            <h4 class="form-signin-heading">New Investment</h4>

            <form:input type="hidden" path="typeName" value="${transactionType}"/>

            <%@ include file="_new_transaction_common.jsp" %>

            <spring:bind path="category">
                <div class="row form-group">
                    <div class="col col-form-label">
                        Investment Category
                    </div>
                    <div class="col">
                        <div class="${status.error ? 'has-error' : ''}">
                            <div>
                                <form:radiobutton path="category" value="LONG_TERM" id="longTermRadio"/>
                                <label for="longTermRadio">Long Term</label>
                            </div>
                            <div>
                                <form:radiobutton path="category" value="MID_TERM" id="midTermRadio"/>
                                <label for="midTermRadio">Mid Term</label>
                            </div>
                            <div>
                                <form:radiobutton path="category" value="SHORT_TERM" id="shortTermRadio" checked="checked"/>
                                <label for="shortTermRadio">Short Term</label>
                            </div>
                            <form:errors path="category"/>
                        </div>
                    </div>
                </div>
            </spring:bind>

            <button class="btn btn-lg btn-primary btn-block" type="submit"
                    onClick="this.form.submit(); this.disabled=true; this.innerText='Saving...';">Save</button>
            <div class="text-center"><a href="${contextPath}/snapshot/${snapshotId}">Back</a></div>
        </form:form>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/_footer.jsp" %>

</body>
</html>
