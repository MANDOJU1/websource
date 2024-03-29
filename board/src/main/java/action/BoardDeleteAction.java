package action;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import dto.BoardDto;
import lombok.AllArgsConstructor;
import service.BoardService;
import service.BoardServiceImpl;

@AllArgsConstructor
public class BoardDeleteAction implements Action {

    private String path;

    @Override
    public ActionForward execute(HttpServletRequest req) throws Exception {

        BoardDto deleteDto = new BoardDto();
        deleteDto.setBno(Integer.parseInt(req.getParameter("bno")));
        deleteDto.setPassword(req.getParameter("password"));

        // page 나누기 개념 추가 후
        String page = req.getParameter("page");
        String amount = req.getParameter("amount");
        String criteria = req.getParameter("criteria");
        String keyword = URLEncoder.encode(req.getParameter("keyword"), "utf-8");

        BoardService service = new BoardServiceImpl();
        // bno 이용해서 행 조회
        BoardDto dto = service.read(deleteDto.getBno());
        // bno == re_ref : 원본글
        if (dto.getBno() == dto.getReRef()) {
            // 비밀번호확인 후 일치한다면 deleteAll 호출
            if (service.pwdCheck(dto)) {
                service.deleteAll(dto.getReRef());
            } else {
                path = "/view/qna_board_pwdCheck.jsp?bno=" + deleteDto.getBno() + "&page=" + page + "&amount=" + amount
                        + "&criteria=" + criteria + "&keyword=" + keyword;
            }
        } else {
            // 삭제 실패시 삭제화면으로 되돌아가기
            if (!service.delete(deleteDto)) {
                path = "/view/qna_board_pwdCheck.jsp?bno=" + deleteDto.getBno() + "&page=" + page + "&amount=" + amount
                        + "&criteria=" + criteria + "&keyword=" + keyword;
            } else {
                path += "?page=" + page + "&amount=" + amount + "&criteria=" + criteria + "&keyword=" + keyword;
            }

        }
        return new ActionForward(path, true);
    }

}
