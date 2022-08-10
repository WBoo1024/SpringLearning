package crud.controller;

import crud.bean.User;
import crud.service.SendMail;
import crud.service.UserService;
import crud.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 壹零二四
 * @date 2022年08月08日 15:47
 */
@RestController
@RequestMapping("/front")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    private Result login(User user) {
        return userService.login(user);
    }

    @PostMapping("/register")
    private Result register(User user) throws SQLException {
        System.out.println(user);
        return userService.register(user);
    }

    @PostMapping("/getUsers")
    public Result getUsers(int page, int limit, String name) {
        Map<String, Object> map = new HashMap<>(10);
        map.put("pageNum", page);
        map.put("pageSize", limit);
        map.put("name", name);
        return userService.getUsers(map);
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam("ids[]") String[] ids){
        return userService.delete(ids);
    }

    @PostMapping("/update")
    public Result update(User user){
        return userService.update(user);
    }

    @PostMapping("/changeWord")
    public Result changeWord(User user){
        return userService.changeWord(user);
    }

    @PostMapping("/searchProvince")
    public Result getProvince(){
        return userService.getProvince();
    }

    @PostMapping("/searchCity")
    public Result getCity(String provinceCode){
        return userService.getCity(provinceCode);
    }

    @PostMapping("/searchArea")
    public Result getArea(String cityCode){
        return userService.getArea(cityCode);
    }

    @PostMapping("/checkToken")
    public Result checkToken(HttpServletRequest request){
        return userService.checkToken(request);
    }

    @PostMapping("/sendCode")
    public Result sendCode(User user){
        String email = user.getEmail();
        int code = (int)((Math.random()*9+1)*100000);
        Result result = new Result();
        result.setData(code);
        SendMail sendMail = new SendMail(email,code);
        sendMail.start();
        return result;
    }
}
