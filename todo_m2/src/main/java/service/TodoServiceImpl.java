package service;

import java.util.List;

import dao.TodoDao;
import dto.ToDoDto;

public class TodoServiceImpl implements TodoService {

    TodoDao dao = new TodoDao();

    @Override
    public List<ToDoDto> list() {
        return dao.getList();
    }

    @Override
    public ToDoDto getRow(String no) {
        return dao.getRow(no);
    }

    @Override
    public boolean insert(ToDoDto inserDto) {
        // 결과가 1이면 true, 아니면 false
        // boolean flag = dao.insert(inserDto)==1 ? true : false;
        // return flag;
        return dao.insert(inserDto) == 1;
    }

    @Override
    public boolean update(ToDoDto inserDto) {
        return dao.update(inserDto) == 1;
    }

    @Override
    public boolean delete(String no) {
        return dao.delete(no) == 1;
    }

}
