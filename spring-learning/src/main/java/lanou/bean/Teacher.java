package lanou.bean;

import lombok.Data;

import java.util.Date;
/**
 * @author WANG
 */
@Data
public class Teacher {
    private String tNo;
    private String tName;
    private String tSex;
    private Date tBirthday;
    private String prof;
    private String depart;
}