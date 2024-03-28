package service;

import java.util.List;

import dto.BoardDto;
import dto.SearchDto;

// dao를 호출해 값을 받는 역할
public interface BoardService {
    List<BoardDto> list(SearchDto searchDto);

    boolean insert(BoardDto insertDto);

    BoardDto read(int bno);

    boolean update(BoardDto updateDto);

    boolean delete(BoardDto deleteDto);

    boolean reply(BoardDto replydDto);

    boolean updateCount(int bno);

    List<BoardDto> serachList(SearchDto searchDto);

    int getTotalRows(String criteria, String keyword);
}
