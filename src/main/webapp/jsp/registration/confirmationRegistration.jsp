<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="pagecontent" />
<html>
  <head>
    <meta charset="UTF-8">
    <title><fmt:message key="title.page.registration.confirmation"/></title>
    <link href="assets/css/confirmationRegistrationPage.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <form id="login" action="controller" name="action" method="post">
      <h1><fmt:message key="label.enter.confirmation.code"/></h1>
      <h4 id="fail">${registrationFail}</h4>
      <fieldset id="inputs">
        <input id="code" name="userConfirmationCode" type="text" placeholder="<fmt:message key="label.enter.confirmation.code"/>" autofocus
        required>
        <error>${errorIncorrectConfirmationCode}</error>
        <input type="hidden" name="action" value="user_register_confirmation"/>
        <input id="submit" type="submit" value="<fmt:message key="label.confirm.code"/>"/>
      </fieldset>
    </form>
  </body>
</html>