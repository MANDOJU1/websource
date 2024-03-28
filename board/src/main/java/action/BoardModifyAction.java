package action;

import javax.servlet.http.HttpServletRequest;

import dto.BoardDto;
import lombok.AllArgsConstructor;
import service.BoardService;
import service.BoardServiceImpl;

@AllArgsConstructor
public class BoardModifyAction implements Action {

    private String path;

    @Override
    public ActionForward execute(HttpServletRequest req) throws Exception {

        // 수정 작업
        BoardDto updateDto = new BoardDto();
        // NullPointerException : 디버깅해서 값이 잘 나오는지 확인해보기
        updateDto.setBno(Integer.parseInt(req.getParameter("bno")));
        updateDto.setTitle(req.getParameter("title"));
        updateDto.setContent(req.getParameter("content"));
        updateDto.setPassword(req.getParameter("password"));

        BoardService service = new BoardServiceImpl();

        if (!service.update(updateDto)) {
            // 수정 실패시 수정화면으로 되돌아가기
            path = "/qModify.do?bno=" + updateDto.getBno();
        } else {
            // /qRead.do
            path += "?bno=" + updateDto.getBno();
        }

        return new ActionForward(path, true);
    }

}
