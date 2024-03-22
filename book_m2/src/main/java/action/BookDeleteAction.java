package action;

import javax.servlet.http.HttpServletRequest;

import dto.BookDto;
import service.BookService;
import service.BookServiceImpl;

public class BookDeleteAction implements Action {

    private String path;

    public BookDeleteAction(String path) {
        this.path = path;
    }

    @Override
    public ActionForward execute(HttpServletRequest req) throws Exception {
        // 도서코드 가져오기
        int code = Integer.parseInt(req.getParameter("code"));

        // 서비스 작업
        BookService service = new BookServiceImpl();
        boolean result = service.delete(code);

        // 실패시 delete.jsp
        if (!result) {
            path = "/view/delete.jsp";
        }
        return new ActionForward(path, true);
    }

}
