package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dto.BookDto;

public class BookDao {
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;

    // JDBC 단계
    // 1. 드라이버 로드

    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 2. 커넥션 열기
    public Connection getConnection() {
        Context initContext;
        try {
            initContext = new InitialContext();
            // java:/comp/env : 등록된 이름 관리
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/myoracle");
            con = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    // 3. CRUD
    // 전체조회
    public List<BookDto> getList() {
        List<BookDto> list = new ArrayList<>();

        con = getConnection();
        String sql = "select * from booktbl order by code desc";

        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                BookDto dto = new BookDto();
                dto.setCode(rs.getInt("code"));
                dto.setTitle(rs.getString("title"));
                dto.setWriter(rs.getString("writer"));
                dto.setPrice(rs.getInt("price"));

                list.add(dto);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt, rs);
        }
        return list;
    }

    // 한 개 조회
    public BookDto getRow(int code) {
        BookDto dto = null;

        con = getConnection();
        String sql = "select * from booktbl where code = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, code);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dto = new BookDto();
                dto.setCode(rs.getInt("code"));
                dto.setTitle(rs.getString("title"));
                dto.setWriter(rs.getString("writer"));
                dto.setPrice(rs.getInt("price"));
                dto.setDescription(rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt, rs);
        }

        return dto;
    }

    // update
    public int update(BookDto updateDto) {
        int result = 0;

        con = getConnection();
        String sql = "UPDATE BOOKTBL SET PRICE = ? WHERE CODE = ?";

        try {
            pstmt = con.prepareStatement(sql);

            // ? 해결
            pstmt.setInt(1, updateDto.getPrice());
            pstmt.setInt(2, updateDto.getCode());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt);
        }

        return result;
    }

    // insert
    public int insert(BookDto insertDto) {
        int result = 0;

        con = getConnection();
        String sql = "INSERT INTO BOOKTBL(CODE, TITLE, WRITER, PRICE, DESCRIPTION) values(?,?,?,?,?)";

        try {
            pstmt = con.prepareStatement(sql);

            // ? 해결
            pstmt.setInt(1, insertDto.getCode());
            pstmt.setString(2, insertDto.getTitle());
            pstmt.setString(3, insertDto.getWriter());
            pstmt.setInt(4, insertDto.getPrice());
            pstmt.setString(5, insertDto.getDescription());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt);
        }

        return result;

    }

    // 검색 기능
    public List<BookDto> getSearchList(String criteria, String keyword) {
        List<BookDto> list = new ArrayList<>();

        con = getConnection();

        String sql = "";

        if (criteria.equals("code")) {
            // 검색기준(criteria)이 code 라면
            sql = "SELECT * FROM BOOKTBL WHERE code = ? ";
        } else {
            // 검색기준이(criteria)이 writer 라면
            sql = "SELECT * FROM BOOKTBL WHERE writer = ?";
        }

        try {

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, keyword);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BookDto dto = new BookDto();
                dto.setCode(rs.getInt("code"));
                dto.setTitle(rs.getString("title"));
                dto.setWriter(rs.getString("writer"));
                dto.setPrice(rs.getInt("price"));

                list.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt, rs);
        }
        return list;
    }

    // delete
    public int delete(int code) {
        int result = 0;

        con = getConnection();
        String sql = "DELETE FROM BOOKTBL WHERE CODE = ? ";

        try {
            pstmt = con.prepareStatement(sql);
            // ?
            pstmt.setInt(1, code);
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt);
        }

        return result;
    }

    // 4. 자원 정리
    public void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            if (con != null)
                con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(Connection con, PreparedStatement pstmt) {
        try {
            if (pstmt != null)
                pstmt.close();
            if (con != null)
                con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
