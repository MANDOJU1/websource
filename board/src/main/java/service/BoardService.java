package service;

import java.util.List;

import dto.BoardDto;

// dao를 호출해 값을 받는 역할
public interface BoardService {
    List<BoardDto> getList();

    boolean insert(BoardDto insertDto);

    BoardDto read(int bno);
}
