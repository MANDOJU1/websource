package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangeDto {
    private String userid;
    private String password; // 현재 비밀번호
    private String newpassword; // 변경 비밀번호
    private String confirmpassword; // 변경 비밀번호 확인

    public boolean newPasswordEqualsConfirmPassword() {
        return newpassword.equals(confirmpassword);
    }
}
