package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dto.BoardDto;
import dto.SearchDto;

public class BoardDao {
    // JDBC 단계

    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
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
    // 전체 게시물 개수 가져오기
    public int getRows(String criteria, String keyword) {
        int total = 0;
        try {
            con = getConnection();
            String sql = "";

            if (criteria.isEmpty()) {
                sql += "SELECT COUNT(*) FROM BOARD";
                pstmt = con.prepareStatement(sql);
            } else {
                sql += "SELECT COUNT(*) FROM BOARD WHERE " + criteria + " like ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, "%" + keyword + "%");
            }
            rs = pstmt.executeQuery();
            if (rs.next()) {
                // total = rs.getString("컬럼명");
                total = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt, rs);
        }
        return total;
    }

    // 전체 리스트 가져오기
    public List<BoardDto> getList(SearchDto searchDto) {
        List<BoardDto> list = new ArrayList<>();

        con = getConnection();
        // String sql = "SELECT BNO, TITLE, NAME, REGDATE, READ_COUNT FROM BOARD ORDER
        // BY BNO DESC";

        // -- start = 페이지번호 * 한 페이지 당 게시물 수
        // -- end = (페이지번호-1) * 한 페이지당 게시물 수
        int start = searchDto.getPage() * searchDto.getAmount();
        int end = (searchDto.getPage() - 1) * searchDto.getAmount();

        try {
            String sql = "SELECT bno, title, name, regdate, READ_COUNT, RE_LEV ";
            sql += "FROM (SELECT rownum AS rnum, A.* ";
            sql += "FROM (SELECT bno, title, name, regdate, READ_COUNT, RE_LEV ";
            sql += "FROM board b WHERE bno > 0 ";

            if (searchDto.getCriteria().isEmpty()) { // 전체리스트
                sql += "ORDER BY RE_REF DESC, RE_SEQ ASC) A ";
                sql += "WHERE rownum <= ?) WHERE rnum > ?";

                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, start);
                pstmt.setInt(2, end);

            } else { // 검색리스트
                sql += "AND " + searchDto.getCriteria() + " like ? ";
                sql += "ORDER BY RE_REF DESC, RE_SEQ ASC) A ";
                sql += "WHERE rownum <= ?) WHERE rnum > ?";

                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, "%" + searchDto.getKeyword() + "%");
                pstmt.setInt(2, start);
                pstmt.setInt(3, end);
            }

            rs = pstmt.executeQuery();
            while (rs.next()) {
                BoardDto dto = new BoardDto();
                dto.setBno(rs.getInt(1));
                dto.setTitle(rs.getString(2));
                dto.setName(rs.getString(3));
                dto.setRegDate(rs.getDate(4));
                dto.setReadCount(rs.getInt(5));

                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt, rs);
        }
        return list;
    }

    // 새 글 작성
    public int create(BoardDto insertDto) {
        int result = 0;
        con = getConnection();

        String sql = "INSERT INTO board(bno, name, password, title, content, attach, re_ref, re_lev, re_seq)";
        sql += "VALUES (board_seq.nextval,?,?,?,?,?,board_seq.currval,?,?)";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, insertDto.getName());
            pstmt.setString(2, insertDto.getPassword());
            pstmt.setString(3, insertDto.getTitle());
            pstmt.setString(4, insertDto.getContent());
            pstmt.setString(5, insertDto.getAttach());
            pstmt.setInt(6, 0);
            pstmt.setInt(7, 0);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt, rs);
        }
        return result;
    }

    // 특정 글 조회
    public BoardDto getRow(int bno) {
        BoardDto dto = null;

        con = getConnection();
        String sql = "select bno, title, name, content, attach, re_ref, re_seq, re_lev from BOARD where bno =?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, bno);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dto = new BoardDto();
                dto.setBno(rs.getInt(1));
                dto.setTitle(rs.getString(2));
                dto.setName(rs.getString(3));
                dto.setContent(rs.getString("content"));
                dto.setAttach(rs.getString("attach"));
                // reply 에서 필요함
                dto.setReRef(rs.getInt("re_ref"));
                dto.setReSeq(rs.getInt("re_seq"));
                dto.setReLev(rs.getInt("re_lev"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt, rs);
        }

        return dto;
    }

    // 특정 글 수정
    public int update(BoardDto updateDto) {
        // bno 와 password 일치 시 제목, 내용 수정
        int result = 0;

        con = getConnection();
        String sql = "UPDATE BOARD SET title= ?, content= ? where bno =? AND PASSWORD =? ";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, updateDto.getTitle());
            pstmt.setString(2, updateDto.getContent());
            pstmt.setInt(3, updateDto.getBno());
            pstmt.setString(4, updateDto.getPassword());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt);
        }

        return result;
    }

    // 특정 글 삭제
    public int delete(BoardDto deleteDto) {
        // bno와 password 일치시 삭제
        int result = 0;

        con = getConnection();
        String sql = "DELETE FROM BOARD WHERE BNO=? AND PASSWORD =?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, deleteDto.getBno());
            pstmt.setString(2, deleteDto.getPassword());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt);
        }
        return result;

    }

    // bno와 password 받아서 비밀번호 확인
    public int pwdCheck(BoardDto passDto) {
        int result = 0;

        con = getConnection();
        String sql = "SELECT count(*) FROM BOARD WHERE BNO =? AND PASSWORD =?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, passDto.getBno());
            pstmt.setString(2, passDto.getPassword());

            result = pstmt.executeUpdate();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt);
        }

        return result;
    }

    public int deleteAll(int reRef) {
        // 부모+자식 (원본글+댓글)
        int result = 0;

        con = getConnection();
        String sql = "DELETE FROM BOARD WHERE re_ref=?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, reRef);

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt);
        }
        return result;

    }

    // 댓글 작성
    public int reply(BoardDto replyDto) {
        int result = 0;

        try {
            con = getConnection();
            // 원본글의 re_ref, re_seq, re_lev 가져오기
            int reRef = replyDto.getReRef();
            int reSeq = replyDto.getReSeq();
            int reLev = replyDto.getReLev();

            String sql = "UPDATE BOARD SET RE_SEQ = RE_SEQ + 1 WHERE re_ref = ? AND RE_SEQ > ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, reRef);
            pstmt.setInt(2, reSeq);

            pstmt.executeUpdate();

            sql = "INSERT INTO board(bno, name, password, title, content, re_ref, re_lev, re_seq)";
            sql += "VALUES (board_seq.nextval,?,?,?,?,?,?,?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, replyDto.getName());
            pstmt.setString(2, replyDto.getPassword());
            pstmt.setString(3, replyDto.getTitle());
            pstmt.setString(4, replyDto.getContent());
            pstmt.setInt(5, reRef);
            pstmt.setInt(6, reLev + 1);
            pstmt.setInt(7, reSeq + 1);

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt, rs);
        }
        return result;
    }

    // 조회수업데이트
    public int updateCount(int bno) {
        int result = 0;

        try {

            con = getConnection();

            String sql = "UPDATE BOARD SET READ_COUNT = READ_COUNT + 1 WHERE BNO = ? ";
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, bno);

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt);
        }

        return result;
    }

    // 검색 기능
    public List<BoardDto> getSearchList(SearchDto searchDto) {
        List<BoardDto> list = new ArrayList<>();

        con = getConnection();
        String sql = "SELECT bno,title,name,REGDATE,READ_COUNT,RE_LEV";
        sql += " FROM BOARD b WHERE " + searchDto.getCriteria() + " like ? ";
        sql += " ORDER BY RE_REF DESC, RE_SEQ asc";
        // searchDto.getCriteria() => ?로 대체 불가능 필드명은 써줘야 함

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + searchDto.getKeyword() + "%");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                BoardDto dto = new BoardDto();
                dto.setBno(rs.getInt(1));
                dto.setTitle(rs.getString(2));
                dto.setName(rs.getString(3));
                dto.setRegDate(rs.getDate(4));
                dto.setReadCount(rs.getInt(5));
                dto.setReLev(rs.getInt(6));

                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
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
