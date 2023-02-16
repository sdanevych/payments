<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="pagecontent" />
<html>
<head>
  <title><fmt:message key="label.replenish"/></title>
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
        <li"><a href="#"><fmt:message key="label.main"/></a></li>
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
        <li class="active"><a href="controller?action=main"><fmt:message key="label.main"/></a></li>
        <li><a href="controller?action=client_accounts&page=1"><fmt:message key="label.client.accounts"/></a></li>
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
        <h4 id="page-title"><fmt:message key="label.client.replenish"/></h4>
          <form action="controller" method="post">
            <p class="text-success">${replenishSuccess}</p>
            <p class="text-danger">${replenishFail}</p>
            <table class="table">
              <tbody>
                <tr>
                  <th><fmt:message key="label.account.name"/></th>
                  <th><input type="text" value="${processingClientAccount.name}" readonly/></th>
                </tr>
                <tr>
                  <th><fmt:message key="label.amount"/></th>
                  <th><input type="text" name="replenishAmount" /></th>
                </tr>
                <tr>
                  <th><fmt:message key="label.bankcard.cvv"/></th>
                  <th><input type="text" name="enteredCvv" /></th>
                </tr>
              </tbody>
            </table>
            <input type="hidden" name="page" value="${param.page}">
            <input type="hidden" name="clientAccountId" value="${processingClientAccount.id}">
            <button type="submit" name="action" value="replenish_account_confirmation">
              <fmt:message key="label.replenish"/>
            </button>
          </form>
      </div>
    </div>
</div>
</div>
</div>
</body>
</html>
