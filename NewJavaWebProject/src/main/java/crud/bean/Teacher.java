package crud.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @program: NewJavaWebProject
 * @description:
 * @author: 壹零二四
 * @created: 2022/07/23 14:43
 */
@Data
@Component
public class Teacher {
    private int id;
    private String name;
    private String sex;
    private String birthday;
    private String prof;
    private String depart;
//    private String area;
}
