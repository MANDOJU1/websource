package dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// no NUMBER(8) PRIMARY KEY,
// title nvarchar2(100) NOT NULL,
// created_at DATE DEFAULT sysdate,
// completed char(1) DEFAULT '0',
// description nvarchar2(1000)

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ToDoDto {
    // table 구조와 동일하게 작성
    private int no;
    private String title;
    private LocalDateTime createdAt;
    private boolean completed;
    private String description;

}
