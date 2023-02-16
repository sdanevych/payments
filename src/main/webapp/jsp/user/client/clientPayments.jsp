<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="com.epam.payments.model.dao.mapper.Column" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="ct" uri="custom-tags" %>
<%@ taglib prefix="fn" uri="custom-functions" %>

<fmt:setLocale value="${language}" />
<fmt:setBundle basename="pagecontent" />
<html>
<head>
    <title><fmt:message key="label.payments"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/client.css" type="text/css"/>
    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<c:set var="clientPayments" value="${ paginationManager.paginationList }"/>
<c:set var="page" value="${ paginationManager.page }"/>
<c:set var="totalPages" value="${ paginationManager.totalPages }"/>
<c:set var="request" value="${ pageContext.request }"/>
<c:set var="jspPath" value="${ fn:getCurrentJsp(request) }"/>

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
        <div class="col-sm-10">
            <div class="hello-user pull-left">
                <h4><fmt:message key="label.welcome"/>, ${login}</h4>
            </div>
        </div>
        <div class="col-sm-10">
            <div class="well">
                <h4 id="page-title"><fmt:message key="label.client.payments"/></h4>
                <c:if test="${not empty createPaymentSuccess}">
                    <p class="text-success">${createPaymentSuccess} ${createdPaymentId}</p>
                </c:if>    
                <p class="text-success">${sendPaymentSuccess} ${sentPaymentId}</p>
                <p class="text-danger">${createPaymentFail}</p>
                <c:if test="${not empty paymentNotEligibleToSend}">
                    <p class="text-danger">${paymentNotEligibleToSend}  ID: ${paymentIdToSend}</p>
                </c:if>
                <p class="text-danger">${receiverAccountNotExists}</p>
                <p class="text-danger">${notEnoughAccountAmount}</p>
                <p class="text-danger">${receiverAndSenderAccountCurrenciesNotMatch}</p>
                <p class="text-danger">${noClientPayments}</p>
                <p class="text-danger">${userIsBlocked}</p>
                <c:if test="${not empty clientPayments}">
                    <form action="controller" method="post">
                        <table class="table">
                            <thead class="text-primary">
                            <tr>
                                <th class="col-md-auto">
                                     <div class="btn-group" role="group">                                        
                                        <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">#</button>
                                        <div class="btn-group-vertical">
                                            <a href="controller?action=client_sort_payments&sortOrder=ASC&sortColumn=${Column.ID}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-asc"></i>
                                            </a>
                                            <a href="controller?action=client_sort_payments&sortOrder=DESC&sortColumn=${Column.ID}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-desc"></i>
                                            </a>
                                        </div>
                                    </div>  
                                </th>
                                <th class="col-md-auto">
                                    <div class="btn-group" role="group">                                        
                                        <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">
                                            <fmt:message key="label.receiver.account"/>
                                        </button>
                                        <div class="btn-group-vertical">
                                            <a href="controller?action=client_sort_payments&sortOrder=ASC&sortColumn=${Column.PAYMENT_RECEIVER_ACCOUNT_ID}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-asc"></i>
                                            </a>
                                            <a href="controller?action=client_sort_payments&sortOrder=DESC&sortColumn=${Column.PAYMENT_RECEIVER_ACCOUNT_ID}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-desc"></i>
                                            </a>
                                        </div>
                                    </div>
                                </th>
                                <th class="col-md-auto">
                                    <div class="btn-group" role="group">                                        
                                        <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">
                                            <fmt:message key="label.amount"/>
                                        </button>
                                        <div class="btn-group-vertical">
                                            <a href="controller?action=client_sort_payments&sortOrder=ASC&sortColumn=${Column.PAYMENT_AMOUNT}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-asc"></i>
                                            </a>
                                            <a href="controller?action=client_sort_payments&sortOrder=DESC&sortColumn=${Column.PAYMENT_AMOUNT}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-desc"></i>
                                            </a>
                                        </div>
                                    </div>
                                </th>
                                <th  class="col-md-auto">
                                    <div class="btn-group" role="group">                                        
                                        <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">
                                            <fmt:message key="label.currency"/>
                                        </button>
                                        <div class="btn-group-vertical">
                                            <a href="controller?action=client_sort_payments&sortOrder=ASC&sortColumn=${Column.PAYMENT_CURRENCY}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-asc"></i>
                                            </a>
                                            <a href="controller?action=client_sort_payments&sortOrder=DESC&sortColumn=${Column.PAYMENT_CURRENCY}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-desc"></i>
                                            </a>
                                        </div>
                                    </div>
                                </th>
                                <th class="col-md-auto">
                                    <div class="btn-group align-middle" role="group">                                        
                                        <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">
                                            <fmt:message key="label.status"/>
                                        </button>
                                    </div>
                                </th>
                                <th class="col-md-auto">
                                    <div class="btn-group" role="group">                                        
                                        <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">
                                            <fmt:message key="label.create.time"/>
                                        </button>
                                        <div class="btn-group-vertical">
                                            <a href="controller?action=client_sort_payments&sortOrder=ASC&sortColumn=${Column.PAYMENT_CREATE_TIME}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-asc"></i>
                                            </a>
                                            <a href="controller?action=client_sort_payments&sortOrder=DESC&sortColumn=${Column.PAYMENT_CREATE_TIME}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-desc"></i>
                                            </a>
                                        </div>
                                    </div>
                                </th>
                                <th class="col-md-auto">
                                    <div class="btn-group" role="group">                                        
                                        <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">
                                            <fmt:message key="label.send.time"/>
                                        </button>
                                        <div class="btn-group-vertical">
                                            <a href="controller?action=client_sort_payments&sortOrder=ASC&sortColumn=${Column.PAYMENT_SEND_TIME}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-asc"></i>
                                            </a>
                                            <a href="controller?action=client_sort_payments&sortOrder=DESC&sortColumn=${Column.PAYMENT_SEND_TIME}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-desc"></i>
                                            </a>
                                        </div>
                                    </div>
                                </th>
                                <th class="col-md-auto"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${ clientPayments }" var="clientPayment">
                                <c:set var="isPaymentReceived" value="${ fn:contains(clientReceivedPayments, clientPayment) }"/>
                                <tr>
                                    <td>${clientPayment.id}</td>
                                    <td>${clientPayment.receiverAccountId}</td>
                                    <td>${clientPayment.amount}</td>
                                    <td>${clientPayment.currency.code}  ${clientPayment.currency.symbol}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${ isPaymentReceived }"> 
                                                <fmt:message key="label.payment.received"/></a>
                                            </c:when> 
                                            <c:otherwise>
                                                ${clientPayment.paymentStatus}  
                                            </c:otherwise>
                                        </c:choose>     
                                    </td>
                                    <td>${ fn:removeMillisecondPart(clientPayment.createTime) }</td>
                                    <td>${ fn:removeMillisecondPart(clientPayment.sendTime) }</td>
                                    <td>
                                        <c:if test="${ (clientPayment.paymentStatus eq 'PREPARED') 
                                                            and (fn:contains(clientAccountIdsWithBankCards, clientPayment.senderAccountId))}">
                                            <a href="controller?action=send_payment&paymentIdToSend=${clientPayment.id}&page=${totalPages}" class="btn btn-primary">
                                            <fmt:message key="label.payment.send"/></a>
                                        </c:if>    
                                    </td>    
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="row pr-3 pl-3">
                            <ct:pagination urlPattern="${clientPaymentsPagePaginationUrlPattern}" page="${page}"
                                totalPages="${totalPages}"/>
                        </div>                     
                    </form>
                </c:if>
                <form action="controller" method="post">
                    <h4 id="form-header"><fmt:message key="label.payment.create"/></h4>
                    <h5><fmt:message key="label.select.account"/></h5>
                    <c:choose>
                        <c:when test="${not empty clientActiveAccountsWithBankCards}"> 
                            <select name="paymentSenderAccountId" required>
                                <c:forEach items="${ clientActiveAccountsWithBankCards }" var="clientActiveAccountWithBankCards">
                                    <option value="${ clientActiveAccountWithBankCards.id }">${ clientActiveAccountWithBankCards.name }</option>
                                </c:forEach>
                            </select>
                            <input type="hidden" name="page" value="${totalPages}">
                            <button class="btn btn-primary" type="submit" name="action" value="create_payment">
                                <fmt:message key="label.payment.create"/>
                            </button>
                        </c:when> 
                        <c:otherwise>
                            <p class="text-danger"><fmt:message key="label.should.have.one.account"/></p>
                        </c:otherwise>
                    </c:choose>
                </form>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>
