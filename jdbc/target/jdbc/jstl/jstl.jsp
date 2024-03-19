<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="../include/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    EL : jsp에서 사용
         getAttribute("name") = ${name}
         getAttribute("loginDto"), <%=loginDto.getName()%> = ${loginDto.name}
 --%>
<%-- <c:url value="/jstl/jstl.jsp"/> --%>
<%-- c:if (else 개념 없음) --%>
<%-- $ 조건 표시 --%>
<h1 class="mt-5">JSTL</h1>
<c:if test="${5<10}"> 
    <h4>5는 10보다 작다</h4>
</c:if>
<c:if test="${6+3 == 9}">
    <h4>6 + 3 은 9 이다</h4>
</c:if>
<%-- c:choose : if ~ else 개념 --%>
<c:choose>
    <c:when test="${5+10 == 50}">
        <h4>5 + 10 은 50이다</h4>
    </c:when>
    <c:otherwise>
        <h4>5 + 10 은 50이 아니다</h4>
    </c:otherwise>
</c:choose>
<%-- c:forEach --%>
<c:forEach var="test" begin="1" end="10" step="2">
    <b>${test}</b>
</c:forEach>

<%-- c:out : 체크박스 체크 표시 --%>
<c:out value="${flag?"OK":"Error"}"></c:out>
<c:out value="${flag?'OK':'Error'}" />

<%@ include file="../include/footer.jsp"%>
