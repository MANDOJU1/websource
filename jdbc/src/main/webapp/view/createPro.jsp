<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<%@ page import="dao.TodoDao"%>
<%@ page import="dto.ToDoDto"%>
<%
    //한글처리
    request.setCharacterEncoding("utf-8");
    //사용자가 입력한 todo 가져오기
    String title = request.getParameter("title");
    String description = request.getParameter("description");

    //DB작업  
    TodoDao dao = new TodoDao();

    ToDoDto inserDto = new ToDoDto();
    inserDto.setTitle(title);
    inserDto.setDescription(description);

    int result = dao.insert(inserDto);
    
    //화면이동(list)
    response.sendRedirect("list.jsp");

%>