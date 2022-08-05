package crud.mapper;

import crud.bean.Area;
import crud.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author WANG
 * @date 2022年07月20日 20:30
 */

public interface UserMapper {
    /**
     *注册
     * @param user 传入用户对象
     * @return int
     */
    int register(User user) ;

    /**
     *登陆
     * @param user 传入用户对象
     * @return int
     */
    int searchName(User user);

    int login(User user);

    List<User> getUsers(Map<String, Object> map);

    int delete(String[] ids);

    List<Area> getProvince();

    List<Area> getCity(String provincecode);

    List<Area> getArea(String citycode);

    int update(User user);

    String searchPic(String name);

    int changeWord(User user);
}
