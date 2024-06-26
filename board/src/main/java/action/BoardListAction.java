package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dto.BoardDto;
import dto.PageDto;
import dto.SearchDto;
import lombok.AllArgsConstructor;
import service.BoardService;
import service.BoardServiceImpl;

@AllArgsConstructor
public class BoardListAction implements Action {

    private String path;

    @Override
    public ActionForward execute(HttpServletRequest req) throws Exception {

        // BoardService list 호출
        int page = Integer.parseInt(req.getParameter("page"));
        int amount = Integer.parseInt(req.getParameter("amount"));

        String criteria = req.getParameter("criteria");
        String keyword = req.getParameter("keyword");
        SearchDto searchDto = new SearchDto(page, amount, criteria, keyword);

        BoardService service = new BoardServiceImpl();

        PageDto pageDto = new PageDto(searchDto, service.getTotalRows(criteria, keyword));
        List<BoardDto> list = service.list(searchDto);

        // req 결과 담기
        req.setAttribute("list", list);
        req.setAttribute("pageDto", pageDto); // searchDto 포함됨

        return new ActionForward(path, false);
    }
}
