package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BookDao;
import dto.BookDto;

@WebServlet("/create")
public class BookCreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 한글처리
        req.setCharacterEncoding("utf-8");

        // create.jsp 에서 넘긴 값 가져오기
        // Integer.parsInt
        int code = Integer.parseInt(req.getParameter("code"));
        String title = req.getParameter("title");
        String writer = req.getParameter("writer");
        int price = Integer.parseInt(req.getParameter("price"));
        String description = req.getParameter("description");

        BookDto dto = new BookDto(code, title, writer, price, description);

        BookDao dao = new BookDao();
        int result = dao.insert(dto);

        if (result > 0) {
            resp.sendRedirect("/list");
        } else {
            resp.sendRedirect("/view/create.jsp");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
