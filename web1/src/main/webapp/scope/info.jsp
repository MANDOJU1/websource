<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>

<%
    request.setCharacterEncoding("utf-8");

    // 사용자가 보낸 값 가져오기 (id, name, age)
    String id = request.getParameter("id");
    String name = request.getParameter("name");
    String age = request.getParameter("age");
    

    // HttpServletRequest : 유효범위
    // request.getParameter() : 사용자의 입력값을 가지고 올 때 범위 제한
    // form action의 값으로 사용된 페이지까지만 가능

    // info.jsp 가 알고 있는 값(사용자 입력값, DB값)을 다른 페이지랑 공유
    // 1) get/post 방식으로 넘겨준다
    // 2) scope 이용
    //    (1) page : 현재 page
    //    (2) request : HttpServletRequest 유효범위
    //    (3) session : HttpSession 유효범위 (브라우저를 닫기 전까지)
    //    (4) application : 톰캣 서버 범위(X)
    //     requset.setAttribute("key" 값), requset.getAttribute("key")
    //     session.setAttribute(), session.getAttribute()

%>
<%-- <h3>id : <%=id%></h3>
<h3>name : <%=name%></h3>
<h3>age : <%=age%></h3> --%>
<% 
    // request scope 사용
    request.setAttribute("id",id);
    request.setAttribute("name",name);
    request.setAttribute("age",age);

    // forward : 주소가 가르키는 페이지 != 화면에 보여지는 내용 페이지
    // info.jsp 에 부여된 request 를 next.jsp 에 넘겨주는 것
    // info.jsp 에서 할 수 있는 작업들을 next.jsp 에서 할 수 있게 됨
    pageContext.forward("next.jsp"); // request 만 사용했을때 next 의 값이 null 이 뜸 
                                     // .forward() 사용 후 next.jsp 값이 잘 나오게 됨 
                                     // http://localhost:8080/scope/info.jsp(주소줄)
                                     // 화면에 보여지는 페이지 : next.jsp

    // session scope 사용
    // session.setAttribute("id",id);
    // session.setAttribute("name",name);
    // session.setAttribute("age",age);

%>
    <%-- <form action="next.jsp" method="post">
      <div>
        <label for="id">id</label>
        <input type="text" name="id" id="id" value="<%=id%>" readonly/>
      </div>
      <div>
        <label for="name">name</label>
        <input type="text" name="name" id="name" value="<%=name%>" readonly/>
      </div>
      <div>
        <label for="age">age</label>
        <input type="text" name="age" id="age" value="<%=age%>" readonly/>
      </div>
      <div>
        <button type="submit">전송</button>
      </div>
    </form> --%>

<%-- <h3>
<a href="next.jsp?id=<%=id%>&name=<%=name%>&age=<%=age%>">다음 페이지</a>
</h3> --%>

<%-- <h3>
    <a href="next2.jsp">다음 페이지</a>
</h3> --%>