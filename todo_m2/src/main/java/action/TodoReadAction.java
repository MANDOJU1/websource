package action;

import javax.servlet.http.HttpServletRequest;

import dto.ToDoDto;
import service.TodoService;
import service.TodoServiceImpl;

public class TodoReadAction implements Action {

    private String path;

    public TodoReadAction(String path) {
        this.path = path;
    }

    @Override
    public ActionForward execute(HttpServletRequest req) throws Exception {
        // TodoReadServlet 에서 했던 작업
        String no = req.getParameter("no");

        TodoService service = new TodoServiceImpl();
        ToDoDto todo = service.getRow(no);

        req.setAttribute("todo", todo);
        // setAttribute면 false 사용
        return new ActionForward(path, false);
    }

}
