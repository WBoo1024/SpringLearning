package lanou.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author WANG
 */
@Data
public class Student {
    private Integer id;
    private String stuName;
    private String sex;
    private String birthday;
    private Clazz clazz;
    private List<Course> courses;
    private Clazz[] clazzs;
    private Map<Teacher, Course> teacherMap;
    private Map<String, String> map;
    private String[] names;
}