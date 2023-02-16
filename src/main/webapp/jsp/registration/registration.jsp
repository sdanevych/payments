<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="pagecontent" var="lang" />

<html>
  <head>
    <meta charset="UTF-8">
      <title><fmt:message key="title.page.registration" bundle="${lang}"/></title>
      <link href="assets/css/registrationPage.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <form id="login" action="controller" name="action" method="post">
      <h1><fmt:message key="title.page.registration" bundle="${lang}"/></h1>
      <h4 id="fail">${registrationFail}</h4>
      <br>
      <fieldset id="inputs">
        <input name="login" type="text" placeholder="<fmt:message key="label.login"
        bundle="${lang}"/>" required>
        <c:if test="${not empty invalidLogin}">
          <error>${invalidLogin}</error>
        </c:if>
        <c:if test="${not empty loginAlreadyExists}">
        <br>  
        <error>${loginAlreadyExists}</error>
        </c:if>

        <input id="email" name="email" type="text" placeholder="example@gmail.com" autofocus required>
        <c:if test="${not empty invalidEmail}">
          <error>${invalidEmail}</error>
        </c:if>
        <c:if test="${not empty emailAlreadyExists}">
          <br>
          <error>${emailAlreadyExists}</error>
        </c:if>
        
        <input id="password" name="password" type="password" placeholder="<fmt:message key="label.password"
        bundle="${lang}"/>" required>
        <error>${invalidPassword}</error>
        
        <input name="samePassword" type="password" placeholder="<fmt:message key="label.password.repeat"
        bundle="${lang}"/>" required>
        <br>
        <error>${notSamePasswords}</error>

        <input name="firstName" type="text" placeholder="<fmt:message key="label.first.name" bundle="${lang}"/>"
        required/>
        <error>${invalidFirstName}</error>

        <input name="secondName" type="text" placeholder="<fmt:message key="label.second.name" bundle="${lang}"/>"
        required/>
        <error>${invalidSecondName}</error>
        
        <input name="phoneNumber" type="text" placeholder="<fmt:message key="label.phone.number" bundle="${lang}"/>"
        required/>
        <c:if test="${not empty invalidPhoneNumber}">
          <error>${invalidPhoneNumber}</error>
        </c:if>
        <c:if test="${not empty phoneNumberAlreadyExists}">
          <br>
          <error>${phoneNumberAlreadyExists}</error>
        </c:if>

        <input type="hidden" name="action" value="user_register" />
        <input id="submit" type="submit" value="<fmt:message key="label.register" bundle="${lang}"/>"/>
      </fieldset>
    </form>
  </body>
</html>