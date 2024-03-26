package action;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import dto.BoardDto;
import lombok.AllArgsConstructor;
import service.BoardService;
import service.BoardServiceImpl;

@AllArgsConstructor
public class BoardWriteAction implements Action {

    private String path;

    @Override
    public ActionForward execute(HttpServletRequest req) throws Exception {

        // qna_board_write.jsp 넘긴 값 가져오기
        BoardDto insertDto = new BoardDto();

        insertDto.setName(req.getParameter("name"));
        insertDto.setTitle(req.getParameter("title"));
        insertDto.setContent(req.getParameter("content"));
        insertDto.setPassword(req.getParameter("password"));

        // 파일 업로드 처리
        Part part = req.getPart("attach");
        String fileName = getFileName(part);

        String saveDir = "c:\\upload";

        if (!fileName.isEmpty()) {

            UUID uuid = UUID.randomUUID();
            // 중복파일명은 저장을 해주지 않음 → 서버에 저장 시 다른 이름 사용
            // 고유한 값 생성 → 고유한값_사용자가 올린 파일명
            // saveDir : "c:\\upload" / File.separator : \\ / uuid : 고유한 값을 만들어줌
            File uploadFile = new File(saveDir + File.separator + uuid + "_" + fileName);

            // c:\\upload\\1.jpg~ 저장
            // empty 가 아니면 파일 저장
            // part.write(saveDir + "\\" + fileName); // 서버의 디스크에 파일 저장
            part.write(uploadFile.toString());
            insertDto.setAttach(uploadFile.getName());
        }

        System.out.println(insertDto);

        // 서비스 호출
        BoardService service = new BoardServiceImpl();

        // true : 목록, false : qna_board_write.jsp
        if (!service.insert(insertDto)) {
            path = "/view/qna_board_write.jsp";
        }

        // req.setAttribute 안하면 모두 다 true
        return new ActionForward(path, true);
    }

    private String getFileName(Part part) {
        // Content-Disposition: attachment; filename="filename.jpg"
        // 키 값 검사해서 파일 갖고오고
        String header = part.getHeader("content-disposition");
        // ; 으로 분리
        String[] arr = header.split(";");
        for (int i = 0; i < arr.length; i++) {
            String temp = arr[i];
            // filename 으로 시작하는 걸 찾아서 추출해서 이름을 돌려받음
            if (temp.trim().startsWith("filename")) {
                return temp.substring(temp.indexOf("=") + 2, temp.length() - 1);
            }
        }
        return "";
    }

}
