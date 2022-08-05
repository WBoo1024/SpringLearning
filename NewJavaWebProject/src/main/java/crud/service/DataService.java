package crud.service;

import crud.bean.Teacher;
import crud.vo.Result;

import java.util.Map;

/**
 * @author WANG
 * @date 2022年07月20日 20:31
 */
public interface DataService {
    Result allSearch(Map<String,Object> map);

    Result add(Teacher teacher);

    Result delete(String[] ids);

    Result update(Teacher teacher);
}
