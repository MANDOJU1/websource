<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<%-- jsp 주석 --%>
<%-- 
    자바코드는 <%  %> 안에 작성(위치는 상관없음)
    자바코드 화면 출력 <%=%>
--%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
<%
    // for(int i=1;i<11;i++){
    //     out.print(i);
    //   }

    // JSP 내장객체
    // 1) HtppServletRequest 변수명 : request 
    // 2) HttpServletResponse 변수명 : response
    // 3) JspWriter out 

    request.setCharacterEncodint("utf-8");

    String id = request.getParameter("id");
    String name = request.getParameter("name");
    String[] dogs = request.getParameterValues("dog");
%>

    <ul>
    <li>id : <%=id%></li>
    <li>name : <%=name%></li>
    
    <%
    for (Stirng dog : dogs ) {
        out.print("<li>dog :" + dog + "</li>")
    }
    %>
    </ul>
</body>
</html>