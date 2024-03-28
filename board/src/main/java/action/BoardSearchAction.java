package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dto.BoardDto;
import dto.SearchDto;
import lombok.AllArgsConstructor;
import service.BoardService;
import service.BoardServiceImpl;

@AllArgsConstructor
public class BoardSearchAction implements Action {

    private String path;

    @Override
    public ActionForward execute(HttpServletRequest req) throws Exception {

        SearchDto searchDto = new SearchDto();
        searchDto.setCriteria(req.getParameter("criteria"));
        searchDto.setKeyword(req.getParameter("keyword"));

        // BoardService list 호출
        BoardService service = new BoardServiceImpl();
        List<BoardDto> list = service.serachList(searchDto);

        // req 결과 담기
        req.setAttribute("list", list);
        // 검색한 기록 남기고 싶을 때 같이 담아서 사용
        req.setAttribute("search", searchDto);

        return new ActionForward(path, false);
    }

}
