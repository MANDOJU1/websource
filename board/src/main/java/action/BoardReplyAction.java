package action;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import dto.BoardDto;
import dto.SearchDto;
import lombok.AllArgsConstructor;
import service.BoardService;
import service.BoardServiceImpl;

@AllArgsConstructor
public class BoardReplyAction implements Action {

    private String path;

    @Override
    public ActionForward execute(HttpServletRequest req) throws Exception {

        String name = req.getParameter("name");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String password = req.getParameter("password");
        // 부모 정보
        int bno = Integer.parseInt(req.getParameter("bno"));
        int reRef = Integer.parseInt(req.getParameter("reRef"));
        int reLev = Integer.parseInt(req.getParameter("reLev"));
        int reSeq = Integer.parseInt(req.getParameter("reSeq"));

        // page 나누기 개념 추가 후
        String page = req.getParameter("page");
        String amount = req.getParameter("amount");
        String criteria = req.getParameter("criteria");
        String keyword = URLEncoder.encode(req.getParameter("keyword"), "utf-8");

        BoardDto replydDto = new BoardDto();
        replydDto.setName(name);
        replydDto.setTitle(title);
        replydDto.setContent(content);
        replydDto.setPassword(password);
        replydDto.setReRef(reRef);
        replydDto.setReLev(reLev);
        replydDto.setReSeq(reSeq);

        // BoardService 호출
        BoardService service = new BoardServiceImpl();

        // 성공시 리스트 보여주기
        if (!service.reply(replydDto)) {
            // 실패시 댓글화면 보여주기
            path = "/qReplyView.do?bno=" + bno + "&page=" + page + "&amount=" + amount
                    + "&criteria=" + criteria + "&keyword=" + keyword;
        } else {
            path += "?page=" + page + "&amount=" + amount + "&criteria=" + criteria + "&keyword=" + keyword;
        }
        return new ActionForward(path, true);
    }

}
