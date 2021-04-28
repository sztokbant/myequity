<spring:bind path="description">
    <div class="row form-group">
        <div class="col col-form-label">
            <label for="description">Description</label>
        </div>
        <div class="col">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="text" id="description" path="description" class="form-control" placeholder="Description"
                            autofocus="true"></form:input>
                <form:errors path="description"/>
            </div>
        </div>
    </div>
</spring:bind>

<spring:bind path="date">
    <div class="row form-group">
        <div class="col col-form-label">
            <label for="date">Date</label>
        </div>
        <div class="col">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="date" id="date" path="date" class="form-control" placeholder="Date"
                            autofocus="true"></form:input>
                <form:errors path="date"/>
            </div>
        </div>
    </div>
</spring:bind>

<spring:bind path="currencyUnit">
    <div class="row form-group">
        <div class="col col-form-label">
            <label for="currencyUnit">Currency</label>
        </div>
        <%@ include file="/WEB-INF/jsp/_currency_select.jsp" %>
    </div>
</spring:bind>

<spring:bind path="amount">
    <div class="row form-group">
        <div class="col col-form-label">
            <label for="amount">Amount</label>
        </div>
        <div class="col">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="number" step="0.00000001" id="amount" path="amount" class="form-control" placeholder="Amount"
                            autofocus="true"></form:input>
                <form:errors path="amount"/>
            </div>
        </div>
    </div>
</spring:bind>

<spring:bind path="isRecurring">
    <div class="row form-group">
        <div class="col col-form-label">
            Recurrence
        </div>
        <div class="col">
            <div class="${status.error ? 'has-error' : ''}">
                <div>
                    <form:radiobutton path="isRecurring" value="true" id="recurringRadio"/>
                    <label for="recurringRadio">Recurring</label>
                </div>
                <div>
                    <form:radiobutton path="isRecurring" value="false" id="nonrecurringRadio"
                                      checked="checked"/>
                    <label for="nonrecurringRadio">Nonrecurring</label>
                </div>
                <form:errors path="isRecurring"/>
            </div>
        </div>
    </div>
</spring:bind>

<spring:bind path="isResettable">
    <div class="row form-group">
        <div class="col col-form-label">
            Reset monthly?
        </div>
        <div class="col">
            <div class="${status.error ? 'has-error' : ''}">
                <div>
                    <form:radiobutton path="isResettable" value="true" id="resettableRadio"/>
                    <label for="resettableRadio">Yes</label>
                </div>
                <div>
                    <form:radiobutton path="isResettable" value="false" id="nonResettableRadio"
                                      checked="checked"/>
                    <label for="nonResettableRadio">No</label>
                </div>
                <form:errors path="isResettable"/>
            </div>
        </div>
    </div>
</spring:bind>
