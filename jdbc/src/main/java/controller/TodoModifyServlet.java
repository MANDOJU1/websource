package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TodoDao;
import dto.ToDoDto;

@WebServlet("/modify")
public class TodoModifyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 한글처리
        req.setCharacterEncoding("utf-8");

        // 제목 클릭 시 no 가져오기
        String no = req.getParameter("no");

        // DB작업
        TodoDao dao = new TodoDao();
        ToDoDto todo = dao.getRow(no);

        // todo 를 modify.jsp에 보여주기
        req.setAttribute("todo", todo);

        RequestDispatcher rd = req.getRequestDispatcher("/view/modify.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
