<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="com.epam.payments.model.dao.mapper.Column" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="custom-functions" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="pagecontent" />
<html>
<head>
    <title><fmt:message key="label.admin.users"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/client.css" type="text/css"/>
    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<c:set var="users" value="${ paginationManager.paginationList }"/>
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
                <li class="active"><a href="#"><fmt:message key="label.admin.users"/></a></li>
                <li><a href="#"><fmt:message key="label.admin.payments.list"/></a></li>
                <li><a href="#"><fmt:message key="label.logout"/></a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row content">
        <div class="col-sm-1 sidenav hidden-xs">
            <h3 style="color: #2a2ada; font-weight: 500">Payments</h3>
            <h4><fmt:message key="label.admin"/></h4>
            <ul class="nav nav-pills nav-stacked">
                <li><a href="controller?action=main"><fmt:message key="label.main"/></a></li>
                <li><a href="controller?action=admin_accounts&page=1"><fmt:message key="label.admin.accounts"/></a></li>
                <li class="active"><a href="controller?action=admin_users&page=1"><fmt:message key="label.admin.users"/></a></li>
                <li><a href="controller?action=admin_all_payments_list&page=1"><fmt:message key="label.admin.payments.list"/></a></li>
                <li><a href="controller?action=log_out"><fmt:message key="label.logout"/></a></li>
            </ul><br>
        </div>
        <br>
        <div class="col-sm-11">
            <div class="hello-user pull-left">
                <h4><fmt:message key="label.welcome"/>, ${login}</h4>
            </div>
        </div>
        <div class="col-sm-11">
            <div class="well">
                <form action="payments/controller" method="post">
                    <h4 id="page-title"><fmt:message key="label.admin.users"/></h4>
                    <p class="text-danger">${noUsers}</p>
                    <p class="text-success">${userUpdateSuccess} ${processingUser.id}</p>
                    <table class="table">
                        <thead class="text-primary">
                        <tr>
                            <th class="col-md-auto">
                                <div class="btn-group" role="group">                                        
                                    <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">#</button>
                                    <div class="btn-group-vertical">
                                        <a href="controller?action=admin_sort_users&sortOrder=ASC&sortColumn=${Column.ID}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-asc"></i>
                                        </a>
                                        <a href="controller?action=admin_sort_users&sortOrder=DESC&sortColumn=${Column.ID}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-desc"></i>
                                        </a>
                                    </div>
                                </div>
                            </th>
                            <th class="col-md-auto">
                                <div class="btn-group" role="group">                                        
                                    <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">
                                        <fmt:message key="label.login"/>
                                    </button>
                                    <div class="btn-group-vertical">
                                        <a href="controller?action=admin_sort_users&sortOrder=ASC&sortColumn=${Column.USER_LOGIN}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-asc"></i>
                                        </a>
                                        <a href="controller?action=admin_sort_users&sortOrder=DESC&sortColumn=${Column.USER_LOGIN}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-desc"></i>
                                        </a>
                                    </div>
                                </div>  
                            </th>
                            <th class="col-md-auto">
                                <div class="btn-group" role="group">                                        
                                    <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">
                                        <fmt:message key="label.role"/>
                                    </button>
                                    <div class="btn-group-vertical">
                                        <a href="controller?action=admin_sort_users&sortOrder=ASC&sortColumn=${Column.USER_ROLE}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-asc"></i>
                                        </a>
                                        <a href="controller?action=admin_sort_users&sortOrder=DESC&sortColumn=${Column.USER_ROLE}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
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
                                        <a href="controller?action=admin_sort_users&sortOrder=ASC&sortColumn=${Column.USER_STATUS}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-asc"></i>
                                        </a>
                                        <a href="controller?action=admin_sort_users&sortOrder=DESC&sortColumn=${Column.USER_STATUS}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-desc"></i>
                                        </a>
                                    </div>
                                </div>  
                            </th>
                            <th class="col-md-auto">
                                <div class="btn-group" role="group">                                        
                                    <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">
                                        <fmt:message key="label.first.name"/>
                                    </button>
                                    <div class="btn-group-vertical">
                                        <a href="controller?action=admin_sort_users&sortOrder=ASC&sortColumn=${Column.USER_FIRST_NAME}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-asc"></i>
                                        </a>
                                        <a href="controller?action=admin_sort_users&sortOrder=DESC&sortColumn=${Column.USER_FIRST_NAME}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-desc"></i>
                                        </a>
                                    </div>
                                </div>  
                            </th>
                            <th class="col-md-auto">
                                <div class="btn-group" role="group">                                        
                                    <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">
                                        <fmt:message key="label.second.name"/>
                                    </button>
                                    <div class="btn-group-vertical">
                                        <a href="controller?action=admin_sort_users&sortOrder=ASC&sortColumn=${Column.USER_SECOND_NAME}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-asc"></i>
                                        </a>
                                        <a href="controller?action=admin_sort_users&sortOrder=DESC&sortColumn=${Column.USER_SECOND_NAME}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-desc"></i>
                                        </a>
                                    </div>
                                </div>  
                            </th>
                            <th class="col-md-auto">
                                <div class="btn-group" role="group">                                        
                                    <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">
                                        <fmt:message key="label.phone.number"/>
                                    </button>
                                    <div class="btn-group-vertical">
                                        <a href="controller?action=admin_sort_users&sortOrder=ASC&sortColumn=${Column.USER_PHONE_NUMBER}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-asc"></i>
                                        </a>
                                        <a href="controller?action=admin_sort_users&sortOrder=DESC&sortColumn=${Column.USER_PHONE_NUMBER}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-desc"></i>
                                        </a>
                                    </div>
                                </div>  
                            </th>
                            <th class="col-md-auto">
                                <div class="btn-group" role="group">                                        
                                    <button type="button" class="btn btn-xs btn-link py-0 pl-0 pr-1">
                                        <fmt:message key="label.email"/>
                                    </button>
                                    <div class="btn-group-vertical">
                                        <a href="controller?action=admin_sort_users&sortOrder=ASC&sortColumn=${Column.USER_EMAIL}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
                                            <i class="fa fa-sort-asc"></i>
                                        </a>
                                        <a href="controller?action=admin_sort_users&sortOrder=DESC&sortColumn=${Column.USER_EMAIL}&jspPath=${jspPath}&page=1" class="btn btn-xs btn-link p-0">
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
                                </div>  
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${users}" var="user">
                            <tr>
                                <td>${user.id}</td>
                                <td>${user.login}</td>
                                <td>${user.role}</td>
                                <td>${user.status}</td>
                                <td>${user.firstName}</td>
                                <td>${user.secondName}</td>
                                <td>${user.phoneNumber}</td>
                                <td>${user.email}</td>
                                <td>
                                    <c:if test="${user.status eq 'ACTIVE'}">
                                        <a href="controller?action=block_user&userId=${user.id}&page=${page}" class="btn btn-primary">
                                        <fmt:message key="label.block"/></a>
                                    </c:if>    
                                    <c:if test="${user.status eq 'BLOCKED'}">
                                        <a href="controller?action=unblock_user&userId=${user.id}&page=${page}" class="btn btn-primary">
                                            <fmt:message key="label.unblock"/></a>
                                    </c:if>
                                </td>    
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <c:if test="${not empty clientAccounts}">
                        <div class="row pr-3 pl-3">
                            <ct:pagination urlPattern="${adminUsersPagePaginationUrlPattern}" page="${page}" totalPages="${totalPages}"/>
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
