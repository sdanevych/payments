<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${not empty user}">
  <c:if test="${user.role eq 'CLIENT'}">
    <jsp:forward page="/jsp/user/client/clientMain.jsp"/>
  </c:if>
  <c:if test="${user.role eq 'ADMIN'}">
    <jsp:forward page="/jsp/user/admin/adminMain.jsp"/>
  </c:if>
</c:if>
<c:if test="${empty user}">
    <jsp:forward page="/jsp/user/login.jsp"/>
</c:if>