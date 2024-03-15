<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<%
    // JSP 내장객체
    // 1) HtppServletRequest 변수명 : request 
    // 2) HttpServletResponse 변수명 : response
    // 3) JspWriter 변수명 : out 
    // 4) PageContext : jsp 페이지에 대한 정보를 저장하고 있는 객체 / 변수명 :pageContext
    //       메서드 1) forward()
    //       메서드 2) include(포함할 페이지 경로) : ex) 디자인 템플릿 구성 시 사용

// http://localhost:8080/object/forward.jsp 요청
// forward("content.jsp") 에 있는 페이지가 화면에 보여짐
// 주소는 요청 주소인 상태
 pageContext.forward("content.jsp");

 // ★sendRedirect(경로)
 // http://localhost:8080/response/response.jsp 요청
 // response.sendRedirect("/basic/input.html"); 페이지가 보여짐
 // url 도 이동된 주소로 변경됨

 // sendRedirect 와 forward 구분하기 ! 
%>