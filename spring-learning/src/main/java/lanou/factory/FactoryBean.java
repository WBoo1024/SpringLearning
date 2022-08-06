package lanou.factory;

import lanou.bean.Clazz;
import lanou.bean.Course;
import lanou.bean.Student;
import lanou.bean.Teacher;

/**
 * @author WANG
 */
public class FactoryBean {
    public static Clazz getClazz() {
        return new Clazz();
    }

    public static Course getCourse() {
        return new Course();
    }

    public static Student getStudent() {
        return new Student();
    }

    public static Teacher getTeacher() {
        return new Teacher();
    }
}

