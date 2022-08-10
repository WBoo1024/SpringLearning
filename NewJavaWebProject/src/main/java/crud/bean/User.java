package crud.bean;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @program: NewJavaWebProject
 * @description:
 * @author: 壹零二四
 * @created: 2022/07/21 19:50
 */

@Data
public class User {
    private int uid;
    private String username;
    private String password;
    private String email;
    private String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reTime;
    private String address;
    private String picture;
}
