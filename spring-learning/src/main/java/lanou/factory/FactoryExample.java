package lanou.factory;

import lanou.bean.Clazz;
import lanou.bean.Course;
import lanou.bean.Student;
import lanou.bean.Teacher;

public class FactoryExample {
    public Clazz getClazz() {
        return new Clazz();
    }

    public Course getCourse() {
        return new Course();
    }

    public Student getStudent() {
        return new Student();
    }

    public Teacher getTeacher() {
        return new Teacher();
    }
}
