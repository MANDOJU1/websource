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
import action.TodoListAction;
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

        TodoDao dao = new TodoDao();
        TodoService service = new TodoServiceImpl();

        Action action = null;

        if (cmd.equals("/list.do")) {
            // TodoListServlet 에서 했던 작업
            action = new TodoListAction("/view/list.jsp");
        } else if (cmd.equals("/read.do")) {
            // 제목 클릭 시 no 가져오기
            String no = req.getParameter("no");
            // DB작업
            ToDoDto todo = service.getRow(no);
            req.setAttribute("todo", todo);
            // RequestDispatcher rd = req.getRequestDispatcher("/view/read.jsp");
            // rd.forward(req, resp);
        } else if (cmd.equals("/modify.do")) {
            // 제목 클릭 시 no 가져오기
            String no = req.getParameter("no");
            // DB작업
            ToDoDto todo = service.getRow(no);
            req.setAttribute("todo", todo);
            // todo 를 modify.jsp에 보여주기
            // RequestDispatcher rd = req.getRequestDispatcher("/view/modify.jsp");
            // rd.forward(req, resp);
        } else if (cmd.equals("/update.do")) {
            String completed = req.getParameter("completed");
            String description = req.getParameter("description");
            String no = req.getParameter("no");
            // DB작업
            ToDoDto dto = new ToDoDto();
            dto.setCompleted(Boolean.parseBoolean(completed));
            dto.setDescription(description);
            dto.setNo(Integer.parseInt(no));
            boolean result = service.update(dto);
            // servlet list 경로로 이동
            // resp.sendRedirect("/list.do");
        } else if (cmd.equals("/delete.do")) {
            // no 가져오기
            String no = req.getParameter("no");
            // DB작업
            boolean result = service.delete(no);
            // 화면이동
            // resp.sendRedirect("/list.do");
        } else if (cmd.equals("/create.do")) {
            // 사용자가 입력한 todo 가져오기
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            // DB작업
            ToDoDto inserDto = new ToDoDto();
            inserDto.setTitle(title);
            inserDto.setDescription(description);
            boolean result = service.insert(inserDto);
            // 화면이동(list)
            // resp.sendRedirect("/list.do");
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
