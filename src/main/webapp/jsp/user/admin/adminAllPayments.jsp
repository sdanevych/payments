<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="com.epam.payments.model.dao.mapper.Column" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="custom-functions" %>
<%@ taglib prefix="ct" uri="custom-tags" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="pagecontent" />
<html>
<head>
    <title><fmt:message key="label.admin.payments.list"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/client.css" type="text/css"/>
    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<c:set var="allPayments" value="${ paginationManager.paginationList }"/>
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
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Payments</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li><a href="#"><fmt:message key="label.main"/></a></li>
                <li><a href="#"><fmt:message key="label.admin.accounts"/></a></li>
                <li><a href="#"><fmt:message key="label.admin.users"/></a></li>
                <li class="active"><a href="#"><fmt:message key="label.admin.payments.list"/></a></li>
                <li><a href="#"><fmt:message key="label.logout"/></a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row content">
        <div class="col-sm-2 sidenav hidden-xs">
            <h2 id="app-title">Payments</h2>
            <h4><fmt:message key="label.admin"/></h4>
            <ul class="nav nav-pills nav-stacked">
                <li><a href="controller?action=main"><fmt:message key="label.main"/></a></li>
                <li><a href="controller?action=admin_accounts&page=1"><fmt:message key="label.admin.accounts"/></a></li>
                <li><a href="controller?action=admin_users&page=1"><fmt:message key="label.admin.users"/></a></li>
                <li class="active"><a href="controller?action=admin_all_payments_list&page=1"><fmt:message key="label.admin.payments.list"/></a></li>
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
                <form action="controller" method="post">
                    <h4 id="page-title"><fmt:message key="label.admin.payments.list"/></h4>
                    <p class="text-danger">${noPayments}</p>
                    <table class="table">
                        <thead class="text-primary">
                        <tr>
                            <th class="col-md-auto">
                                 <div class="btn-group" role="group">                                        
                                    <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">#</button>
                                    <div class="btn-group-vertical">
                                        <a href="controller?action=admin_sort_payments&sortOrder=ASC&sortColumn=${Column.ID}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-asc"></i>
                                        </a>
                                        <a href="controller?action=admin_sort_payments&sortOrder=DESC&sortColumn=${Column.ID}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
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
                                        <a href="controller?action=admin_sort_payments&sortOrder=ASC&sortColumn=${Column.PAYMENT_RECEIVER_ACCOUNT_ID}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-asc"></i>
                                        </a>
                                        <a href="controller?action=admin_sort_payments&sortOrder=DESC&sortColumn=${Column.PAYMENT_RECEIVER_ACCOUNT_ID}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
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
                                        <a href="controller?action=admin_sort_payments&sortOrder=ASC&sortColumn=${Column.PAYMENT_AMOUNT}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-asc"></i>
                                        </a>
                                        <a href="controller?action=admin_sort_payments&sortOrder=DESC&sortColumn=${Column.PAYMENT_AMOUNT}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
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
                                        <a href="controller?action=admin_sort_payments&sortOrder=ASC&sortColumn=${Column.PAYMENT_CURRENCY}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-asc"></i>
                                        </a>
                                        <a href="controller?action=admin_sort_payments&sortOrder=DESC&sortColumn=${Column.PAYMENT_CURRENCY}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
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
                                        <a href="controller?action=admin_sort_payments&sortOrder=ASC&sortColumn=${Column.PAYMENT_CREATE_TIME}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-asc"></i>
                                        </a>
                                        <a href="controller?action=admin_sort_payments&sortOrder=DESC&sortColumn=${Column.PAYMENT_CREATE_TIME}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
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
                                        <a href="controller?action=admin_sort_payments&sortOrder=ASC&sortColumn=${Column.PAYMENT_SEND_TIME}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-asc"></i>
                                        </a>
                                        <a href="controller?action=admin_sort_payments&sortOrder=DESC&sortColumn=${Column.PAYMENT_SEND_TIME}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-desc"></i>
                                        </a>
                                    </div>
                                </div>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${ allPayments }" var="payment">
                            <tr>
                                <td>${payment.id}</td>
                                <td>${payment.receiverAccountId}</td>
                                <td>${payment.amount}</td>
                                <td>${payment.currency.code}  ${payment.currency.symbol}</td>
                                <td>${payment.paymentStatus}</td>
                                <td>${ fn:removeMillisecondPart(payment.createTime) }</td>
                                <td>${ fn:removeMillisecondPart(payment.sendTime) }</td>  
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <c:if test="${not empty allPayments}">
                        <div class="row pr-3 pl-3">
                            <ct:pagination urlPattern="${adminPaymentsPagePaginationUrlPattern}" page="${page}" totalPages="${totalPages}"/>
                        </div>
                    </c:if>
                </form>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>
