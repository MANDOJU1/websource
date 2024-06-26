package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ToDoDto;

public class TodoDao {

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
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "c##test2";
        String password = "test";

        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    // 3. sql 작업 = CRUD 메소드 구현
    // 전체조회 - Read
    public List<ToDoDto> getList() {

        List<ToDoDto> list = new ArrayList<>();

        con = getConnection();
        String sql = "select no, title, created_at, completed from todotbl order by no desc";
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ToDoDto dto = new ToDoDto();
                dto.setNo(rs.getInt("no"));
                dto.setTitle(rs.getString("title"));
                dto.setCreatedAt(rs.getDate("created_at"));
                dto.setCompleted(rs.getBoolean("completed"));
                // dto.setDescription(rs.getString("description"));

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
    public ToDoDto getRow(String no) {

        ToDoDto dto = null;

        con = getConnection();
        String sql = "select * from todotbl where no = ?";
        try {
            pstmt = con.prepareStatement(sql);
            // ? 해결
            pstmt.setInt(1, Integer.parseInt(no));
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dto = new ToDoDto();
                dto.setNo(rs.getInt("no"));
                dto.setTitle(rs.getString("title"));
                dto.setCreatedAt(rs.getDate("created_at"));
                dto.setCompleted(rs.getBoolean("completed"));
                dto.setDescription(rs.getString("description"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt, rs);
        }

        return dto;
    }

    // 추가 - Create(insert)
    public int insert(ToDoDto inserDto) {
        int result = 0;

        con = getConnection();
        String sql = "INSERT INTO TODOTBL(NO, title, Description) values(todo_seq.nextval,?,?)";
        try {
            pstmt = con.prepareStatement(sql);
            // ? 해결
            pstmt.setString(1, inserDto.getTitle());
            pstmt.setString(2, inserDto.getDescription());
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt);
        }

        return result;
    }

    // 수정 - Update
    public int update(ToDoDto inserDto) {
        int result = 0;

        con = getConnection();
        String sql = "UPDATE TODOTBL SET Completed=?, DESCRIPTION=? WHERE NO=?";
        try {
            pstmt = con.prepareStatement(sql);
            // ? 해결
            // pstmt.setString(1, inserDto.getTitle());
            pstmt.setBoolean(1, inserDto.isCompleted());
            pstmt.setString(2, inserDto.getDescription());
            pstmt.setInt(3, inserDto.getNo());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt);
        }
        return result;
    }

    // 삭제 - DELETE
    public int delete(String no) {
        int result = 0;

        con = getConnection();
        String sql = "DELETE FROM TODOTBL WHERE NO=?";
        try {
            pstmt = con.prepareStatement(sql);
            // ? 해결
            pstmt.setInt(1, Integer.parseInt(no));
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
