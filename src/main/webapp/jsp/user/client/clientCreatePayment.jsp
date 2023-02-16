<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="pagecontent" />
<html>
<head>
    <title><fmt:message key="title.page.create.payment"/></title>
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
                <li><a href="#"><fmt:message key="label.client.accounts"/></a></li>
                <li class="active"><a href="#"><fmt:message key="label.client.payments"/></a></li>
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
                <li><a href="controller?action=client_accounts&page=1"><fmt:message key="label.client.accounts"/></a></li>
                <li class="active"><a href="controller?action=client_payments&page=1"><fmt:message key="label.client.payments"/></a></li>
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
                    <h4><fmt:message key="label.payment.fill.fields"/></h4>
                    <p class="text-danger">${receiverAccountNotExists}</p>
                    <p class="text-danger">${receiverBankCardNotExists}</p>
                    <p class="text-danger">${receiverAccountNotActive}</p>
                    <p class="text-danger">${notEnoughAccountAmount}</p>
                    <p class="text-danger">${receiverAndSenderAccountCurrenciesNotMatch}</p>
                    <p class="text-danger">${invalidBankCardExpirationDateFormat}</p>
                    <p class="text-danger">${bankCardExpirationDatesNotMatch}</p>
                    <p class="text-danger">${bankCardHasExpired}</p>
                    <p class="text-danger">${invalidBankCardCvv}</p>
                    <p class="text-danger">${incorrectEnteredCvv}</p>
                    <table class="table">
                        <tbody>
                        <tr>
                          <th><fmt:message key="label.account"/></th>
                          <th>
                            ${ paymentSenderAccount.name }
                          </th>
                        </tr>
                        <tr>
                          <th><fmt:message key="label.payment.receiver.account.id"/></th>
                          <th><input type="text" name="paymentReceiverAccountId" required/></th>
                        </tr>
                        <tr>
                            <th><fmt:message key="label.payment.amount"/></th>
                            <th><input type="text" name="paymentAmount" required/></th>
                          </tr>
                        <tr>
                            <th><fmt:message key="label.payment.currency"/></th>
                            <th>${ paymentSenderAccountCurrency.code }  ${ paymentSenderAccountCurrency.symbol }</th>
                        </tr>
                        <tr>
                            <th><fmt:message key="label.bankcard.expiration.date"/></th>
                            <th>
                                <input type="text" name="paymentBankcardExpirationDate" required
                                placeholder=<fmt:message key="label.bankcard.expiration.date.format" />
                            </th>
                        </tr>
                        <tr>
                            <th><fmt:message key="label.bankcard.cvv"/></th>
                            <th><input type="number" name="paymentBankcardCvv" required/></th>
                        </tr>
                        </tbody>
                    </table>
                    <input type="hidden" name="page" value="${page}">
                    <input type="hidden" name="paymentSenderAccountId" value="${paymentSenderAccount.id}">
                    <button type="submit" name="action" value="prepare_payment" class="btn btn-primary">
                        <fmt:message key="label.payment.create"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
