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
    <title><fmt:message key="title.page.accounts"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/client.css" type="text/css"/>
    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<c:set var="allAccounts" value="${ paginationManager.paginationList }"/>
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
                <li class="active"><a href="#"><fmt:message key="label.admin.accounts"/></a></li>
                <li><a href="#"><fmt:message key="label.admin.users"/></a></li>
                <li><a href="#"><fmt:message key="label.admin.payments.list"/></a></li>
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
                <li class="active"><a href="controller?action=admin_accounts&page=1"><fmt:message key="label.admin.accounts"/></a></li>
                <li><a href="controller?action=admin_users&page=1"><fmt:message key="label.admin.users"/></a></li>
                <li><a href="controller?action=admin_all_payments_list&page=1"><fmt:message key="label.admin.payments.list"/></a></li>
                <li><a href="controller?action=log_out"><fmt:message key="label.logout"/></a></li>
            </ul><br>
        </div>
        <br>
        <div class="col-sm-8">
            <div class="hello-user pull-left">
                <h4><fmt:message key="label.welcome"/>, ${login}</h4>
            </div>
        </div>
        <div class="col-sm-8">
            <div class="well">
                <h4 id="page-title"><fmt:message key="label.admin.accounts"/></h4>
                <p class="text-danger">${noClientAccounts}</p>
                
                <!-- todo: implement -->
                <c:if test="${not empty accountUpdateSuccess}">
                    <p class="text-success">
                        ${accountUpdateSuccess} ${processingClientAccount.id} "${processingClientAccount.name}"
                    </p>
                </c:if>               
                <c:if test="${not empty allAccounts}">
                    <form action="controller" method="post">                        
                        <table class="table">
                            <thead class="text-primary">
                            <tr>
                                <th class="col-md-auto">
                                    <div class="btn-group" role="group">                                        
                                        <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">#</button>
                                        <div class="btn-group-vertical">
                                            <a href="controller?action=admin_sort_accounts&sortOrder=ASC&sortColumn=${Column.ID}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-asc"></i>
                                            </a>
                                            <a href="controller?action=admin_sort_accounts&sortOrder=DESC&sortColumn=${Column.ID}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-desc"></i>
                                            </a>
                                        </div>
                                    </div>  
                                </th>
                                <th class="col-md-auto">
                                    <div class="btn-group" role="group">                                        
                                        <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">
                                            <fmt:message key="label.name"/>
                                        </button>
                                        <div class="btn-group-vertical">
                                            <a href="controller?action=admin_sort_accounts&sortOrder=ASC&sortColumn=${Column.ACCOUNT_NAME}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-asc"></i>
                                            </a>
                                            <a href="controller?action=admin_sort_accounts&sortOrder=DESC&sortColumn=${Column.ACCOUNT_NAME}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-desc"></i>
                                            </a>
                                        </div>
                                    </div>  
                                </th>
                                <th class="col-md-auto">
                                    <div class="btn-group" role="group">                                        
                                        <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">
                                            <fmt:message key="label.balance"/>
                                        </button>
                                        <div class="btn-group-vertical">
                                            <a href="controller?action=admin_sort_accounts&sortOrder=ASC&sortColumn=${Column.ACCOUNT_BALANCE}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-asc"></i>
                                            </a>
                                            <a href="controller?action=admin_sort_accounts&sortOrder=DESC&sortColumn=${Column.ACCOUNT_BALANCE}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-desc"></i>
                                            </a>
                                        </div>
                                    </div>  
                                </th>
                                <th class="col-md-auto">                                
                                    <div class="btn-group" role="group">                                        
                                        <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">
                                            <fmt:message key="label.status"/>
                                        </button>
                                        <div class="btn-group-vertical">
                                            <a href="controller?action=admin_sort_accounts&sortOrder=ASC&sortColumn=${Column.ACCOUNT_STATUS}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-asc"></i>
                                            </a>
                                            <a href="controller?action=admin_sort_accounts&sortOrder=DESC&sortColumn=${Column.ACCOUNT_STATUS}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-desc"></i>
                                            </a>
                                        </div>
                                    </div>                                  
                                </th>
                                <th class="col-md-auto">
                                    <div class="btn-group" role="group">                                        
                                        <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">
                                            <fmt:message key="label.creation.time"/>
                                        </button>
                                        <div class="btn-group-vertical">
                                            <a href="controller?action=admin_sort_accounts&sortOrder=ASC&sortColumn=${Column.ACCOUNT_CREATE_TIME}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-asc"></i>
                                            </a>
                                            <a href="controller?action=admin_sort_accounts&sortOrder=DESC&sortColumn=${Column.ACCOUNT_CREATE_TIME}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-desc"></i>
                                            </a>
                                        </div>
                                    </div>    
                                </th>
                                <th class="col-md-auto">
                                    <div class="btn-group" role="group">                                        
                                        <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">
                                            <fmt:message key="label.control"/>
                                        </button>
                                        <div class="btn-group-vertical">
                                            <a href="controller?action=admin_sort_accounts&sortOrder=ASC&sortColumn=${Column.ACCOUNT_STATUS}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-asc"></i>
                                            </a>
                                            <a href="controller?action=admin_sort_accounts&sortOrder=DESC&sortColumn=${Column.ACCOUNT_STATUS}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                                <i class="fa fa-sort-desc"></i>
                                            </a>
                                        </div>
                                    </div>    
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${allAccounts}" var="account">
                                    <tr>
                                        <td>${account.id}</td>
                                        <td>${account.name}</td>
                                        <td>${account.balance}</td>
                                        <td>${account.status.label}</td>
                                        <td>${ fn:removeMillisecondPart(account.createTime) }</td>
                                        <td>
                                            <c:if test="${account.status eq 'ACTIVE'}">
                                                <a href="controller?action=block_account&clientAccountId=${account.id}&page=${page}" class="btn btn-primary">
                                                    <fmt:message key="label.block"/>
                                                </a>
                                            </c:if>    
                                    
                                            <c:if test="${ (account.status eq 'BLOCKED') or (account.status eq 'WAITING_FOR_UNLOCK')}">
                                                <a href="controller?action=unblock_account&clientAccountId=${account.id}&page=${page}" class="btn btn-primary">
                                                    <fmt:message key="label.unblock"/>
                                                </a>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div>
                            <ct:pagination urlPattern="${adminAccountsPagePaginationUrlPattern}" page="${page}" totalPages="${totalPages}"/>
                        </div>   
                    </form>                         
                </c:if>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>
