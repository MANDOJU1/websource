package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.ActionForward;
import action.TodoCreateAction;
import action.TodoDeleteAction;
import action.TodoListAction;
import action.TodoReadAction;
import action.TodoUpdateAction;
import dao.TodoDao;
import dto.ToDoDto;
import service.TodoService;
import service.TodoServiceImpl;

@WebServlet("*.do")
public class TodoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");

        // 경로에서 요청 찾기
        String requestUri = req.getRequestURI(); // 8080 이후의 값
        String contextpath = req.getContextPath(); // 프로젝트명
        String cmd = requestUri.substring(contextpath.length()); // /create.do, list.do ~

        // System.out.println("requestUri " + requestUri);
        // System.out.println("contextPath " + contextpath);
        System.out.println("cmd " + cmd); // 어디서 온 요청인지 구분할 수 있는 코드

        Action action = null;

        if (cmd.equals("/list.do")) {
            // TodoListServlet 에서 했던 작업
            action = new TodoListAction("/view/list.jsp");
        } else if (cmd.equals("/read.do")) {
            action = new TodoReadAction("/view/read.jsp");
        } else if (cmd.equals("/modify.do")) {
            // TodoReadAction (read와 동일한 코드이기 때문에 가능)
            action = new TodoReadAction("/view/modify.jsp");
        } else if (cmd.equals("/update.do")) {
            action = new TodoUpdateAction("/list.do");
        } else if (cmd.equals("/delete.do")) {
            action = new TodoDeleteAction("list.do");
        } else if (cmd.equals("/create.do")) {
            action = new TodoCreateAction("/list.do");
        }

        ActionForward af = null;

        try {
            af = action.execute(req);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (af.isRedirect()) {
            resp.sendRedirect(af.getPath());
        } else {
            RequestDispatcher rd = req.getRequestDispatcher(af.getPath());
            rd.forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
