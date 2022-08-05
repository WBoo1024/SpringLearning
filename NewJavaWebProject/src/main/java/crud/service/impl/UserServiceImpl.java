package crud.service.impl;

import crud.bean.Area;
import crud.bean.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.Claims;
import crud.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import crud.service.UserService;
import crud.util.JwtUtils;
import crud.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: NewJavaWebProject
 * @description:
 * @author: 壹零二四
 * @created: 2022/07/20 20:30
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result login(User user) {
        Result result = new Result();
        int count = userMapper.searchName(user);
        //如果count=0，说明未查询到用户名
        if (count == 1) {
            count += userMapper.login(user);
            if (count == 2) {
                //查询到用户名和密码匹配，登陆成功
                result = Result.ok();
                String token = JwtUtils.toJwtToken(user.getUsername(), LocalDateTime.now());
                result.setData(token);
                return result;
            } else {
                //查询到用户名，但未查询到密码
                result = Result.failed();
                result.setMsg("密码错误，请重新输入");
            }
        } else if (count == 0){
            result.setCode(2);
            result.setMsg("用户名不存在，请先注册");
        }
        return result;
    }

    @Override
    public Result register(User user) {
        Result result = new Result();
        userMapper.register(user);
        result = Result.ok();
        return result;
    }

    @Override
    public Result getUsers(Map<String, Object> map) {
        Result result = Result.ok();
        PageHelper.startPage(map);
        List<User> list = userMapper.getUsers(map);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        result.setData(pageInfo.getList());
        result.setCount(pageInfo.getTotal());
        return result;
    }

    @Override
    public Result delete(String[] ids) {
        Result result = Result.ok();
        int count = userMapper.delete(ids);
        result = Result.ok();
        return result;
    }

    @Override
    public Result getProvince() {
        Result result = Result.ok();
        List<Area> list = userMapper.getProvince();
        result.setData(list);
        return result;
    }

    @Override
    public Result getCity(String provincecode) {
        Result result = Result.ok();
        List<Area> list = userMapper.getCity(provincecode);
        result.setData(list);
        return result;
    }

    @Override
    public Result getArea(String citycode) {
        Result result = Result.ok();
        List<Area> list = userMapper.getArea(citycode);
        result.setData(list);
        return result;
    }

    @Override
    public Result update(User user) {
        Result result = Result.ok();
        userMapper.update(user);
        return result;
    }

    @Override
    public String searchPic(String name) {
        return userMapper.searchPic(name);
    }

    @Override
    public Result checkToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Result result = new Result();
        if (token == null) {
            result.setCode(1);
        } else {
            Claims claims = JwtUtils.fromJwtToken(token);
            if (claims == null) {
                result.setCode(1);
            } else {
                result.setCode(0);
                String name = (String) claims.get("userName");
                String url = userMapper.searchPic(name);
                List<String> list = new ArrayList<>();
                list.add(url);
                list.add(name);
                result.setData(list);
            }
        }
        return result;
    }

    @Override
    public Result changeWord(User user) {
        Result result = Result.ok();
        userMapper.changeWord(user);
        return result;
    }
}