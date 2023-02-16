<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="language" value="${not empty language ? language : 'ua'}" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="pagecontent" var="lang" />

<html lang="${language}">
  <head>
    <meta charset="UTF-8">
    <title><fmt:message key="title.page.login" bundle="${lang}"/></title>
    <link href="assets/css/loginPage.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <div>Payments</div>
    <form id="login" action="controller" name="action" method="post">
      <h1><fmt:message key="label.welcome" bundle="${lang}"/></h1>
      <fieldset id="inputs">
        <input id="email" name="email" type="text" placeholder="example@gmail.com" autofocus required>
        <input id="password" name="password" type="password" placeholder="<fmt:message key="label.password"
        bundle="${lang}"/>" required>
      </fieldset>
      <error>${signInFail}</error>
      <fieldset id="actions">
        <input type="hidden" name="action" value="login"/>
        <input type="submit" id="submit" value="<fmt:message key="label.signIn" bundle="${lang}"/>"/>
        <a href="controller?action=sign_up"><fmt:message key="label.signUp" bundle="${lang}"/></a>
      </fieldset>
      <br>
      <a id="change_lang_ua" href="controller?action=set_language&selectedLanguage=ua">UA</a>
      <a id="change_lang_en" href="controller?action=set_language&selectedLanguage=en">EN</a>
    </form>
  </body>
</html>