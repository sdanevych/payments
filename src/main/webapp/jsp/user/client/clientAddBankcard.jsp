<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="pagecontent" />
<html>
<head>
    <title><fmt:message key="label.bankcard.add"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="/assets/css/client/client.css" type="text/css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse visible-xs">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Payments</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li><a href="#"><fmt:message key="label.main"/></a></li>
                <li class="active"><a href="#"><fmt:message key="label.client.accounts"/></a></li>
                <li><a href="#"><fmt:message key="label.client.payments"/></a></li>
                <li><a href="#"><fmt:message key="label.logout"/></a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row content">
        <div class="col-sm-2 sidenav hidden-xs">
            <h2 id="app-title">Payments</h2>
            <h4><fmt:message key="label.client"/></h4>
            <ul class="nav nav-pills nav-stacked">
                <li><a href="controller?action=main"><fmt:message key="label.main"/></a></li>
                <li class="active"><a href="controller?action=client_accounts&page=1"><fmt:message key="label.client.accounts"/></a></li>
                <li><a href="controller?action=client_payments&page=1"><fmt:message key="label.client.payments"/></a></li>
                <li><a href="controller?action=log_out"><fmt:message key="label.logout"/></a></li>
            </ul><br>
        </div>
        <br>
        <div class="col-sm-9">
            <div class="hello-user pull-left">
                <h4><fmt:message key="label.welcome"/>, ${login}</h4>
            </div>
        </div>
        <div class="col-sm-9">
            <div class="well">
                <form action="controller" method="post">
                    <h4><fmt:message key="label.bankcard.fill.fields"/></h4>
                    <p class="text-danger">${addBankCardFail}</p>
                    <p class="text-danger">${invalidBankCardExpirationDate}</p>
                    <p class="text-danger">${invalidBankCardNumber}</p>
                    <p class="text-danger">${invalidBankCardCvv}</p>
                    <table class="table">
                        <thead class="text-primary">
                        <tr>
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <th><fmt:message key="label.bankcard.number"/></th>
                            <th><input type="number" name="bankcardNumber" required/></th>
                        </tr>
                        <tr>
                            <th><fmt:message key="label.bankcard.type"/></th>
                            <th>    
                                <input type="radio" name="bankcardType" value="VISA" checked="checked">Visa 
                                <input type="radio" name="bankcardType" value="MASTERCARD">Mastercard
                                <input type="radio" name="bankcardType" value="AMERICAN_EXPRESS">American Express
                            </th>
                        </tr>
                        <tr>
                            <th><fmt:message key="label.bankcard.currency"/></th>
                            <th>
                                <input type="radio" name="bankcardCurrency" value="UKRAINE_HRYVNIA" checked="checked"><fmt:message key="label.bankcard.type.hryvnia"/>
                                <input type="radio" name="bankcardCurrency" value="USA_DOLLAR"><fmt:message key="label.bankcard.type.dollar.usa"/>
                                <input type="radio" name="bankcardCurrency" value="EURO"><fmt:message key="label.bankcard.type.euro"/>
                            </th>
                        </tr>
                        <tr>
                            <th><fmt:message key="label.bankcard.expiration.date"/></th>
                            <th><input type="text" name="bankcardExpirationDate"
                                placeholder=<fmt:message key="label.bankcard.expiration.date.format"/> required/></th>
                        </tr>
                        <tr>
                            <th><fmt:message key="label.bankcard.cardholder.name"/></th>
                            <th><input type="text" name="bankcardCardholderName" required/></th>
                        </tr>
                        <tr>
                            <th><fmt:message key="label.bankcard.balance"/></th>
                            <th><input type="number" name="bankcardBalance" required/></th>
                        </tr>
                        <tr>
                            <th><fmt:message key="label.bankcard.cvv"/></th>
                            <th><input type="number" name="bankcardCvv" required/></th>
                        </tr>
                        </tbody>
                    </table>
                    <input type="hidden" name="page" value="${param.page}">
                    <input type="hidden" name="clientAccountId" value="${param.clientAccountId}">
                    <button type="submit" name="action" value="add_bank_card_confirmation">
                        <fmt:message key="label.bankcard.add"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
