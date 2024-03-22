package action;

import javax.servlet.http.HttpServletRequest;

import dto.ToDoDto;
import service.TodoService;
import service.TodoServiceImpl;

public class TodoUpdateAction implements Action {

    private String path;

    public TodoUpdateAction(String path) {
        this.path = path;
    }

    @Override
    public ActionForward execute(HttpServletRequest req) throws Exception {
        String completed = req.getParameter("completed");
        String description = req.getParameter("description");
        String no = req.getParameter("no");

        ToDoDto dto = new ToDoDto();
        dto.setCompleted(Boolean.parseBoolean(completed));
        dto.setDescription(description);
        dto.setNo(Integer.parseInt(no));

        TodoService service = new TodoServiceImpl();
        boolean result = service.update(dto);

        // sendRedirect면 true 사용
        return new ActionForward(path, true);
    }

}
