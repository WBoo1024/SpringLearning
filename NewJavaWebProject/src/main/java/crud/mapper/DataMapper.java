package crud.mapper;

import crud.bean.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author WANG
 * @date 2022年07月20日 20:32
 */

public interface DataMapper {

    List<Teacher> allSearch(Map<String,Object> map);

    int add(Teacher teacher);

    int delete(String[] ids);

    int update(Teacher teacher);
}
