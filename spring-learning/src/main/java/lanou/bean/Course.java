package lanou.bean;

import lombok.Data;

/**
 * @author WANG
 */
@Data
public class Course {
    private String cno;
    private String cname;
    private Teacher teacher;
}