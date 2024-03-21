package service;

import java.util.List;

import dto.ToDoDto;

// DAO의 CRUD 메소드를 호출

public interface TodoService {

    List<ToDoDto> list();

    ToDoDto getRow(String no);

    boolean insert(ToDoDto inserDto);

    boolean update(ToDoDto inserDto);

    boolean delete(String no);
}
