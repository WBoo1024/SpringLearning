package crud.servlet;

import crud.bean.User;
import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;
import crud.service.SendMail;
import crud.service.UserService;
import crud.service.impl.UserServiceImpl;
import crud.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WANG
 */
@WebServlet("/front/*")
public class UserServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Autowired
    private UserService userService;

    @Autowired
    private Result result;

    @Autowired
    private User user;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI();
        String URI = uri.substring(uri.lastIndexOf("/"));
        switch (URI) {
            case "/searchProvince":
                getProvince(request, response);
                break;
            case "/searchCity":
                getCity(request, response);
                break;
            case "/searchArea":
                getArea(request, response);
                break;
            case "/register":
                register(request, response);
                break;
            case "/login":
                login(request, response);
                break;
            case "/getUsers":
                getUsers(request, response);
                break;
            case "/add":
                add(request, response);
                break;
            case "/delete":
                delete(request, response);
                break;
            case "/update":
                update(request, response);
                break;
            case "/checkToken":
                checkToken(request, response);
                break;
            case "/sendCode":
                sendCode(request, response);
                break;
            case "/changeWord":
                changeWord(request, response);
                break;
            default:
                break;
        }
    }

    private void changeWord(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            BeanUtils.populate(user, request.getParameterMap());
            result = userService.changeWord(user);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        response.getWriter().write(new Gson().toJson(result));
    }

    private void sendCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        int code = (int)((Math.random()*9+1)*100000);
        result.setData(code);
        SendMail sendMail = new SendMail(email,code);
        sendMail.start();
        response.getWriter().write(new Gson().toJson(result));
    }

    private void checkToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        result = userService.checkToken(request);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(new Gson().toJson(result));
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            BeanUtils.populate(user, request.getParameterMap());
            result = userService.update(user);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        response.getWriter().write(new Gson().toJson(result));
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] ids = request.getParameterValues("datas[]");
        result = userService.delete(ids);
        response.getWriter().write(new Gson().toJson(result));
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        register(request, response);
    }

    private void getUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        String name = request.getParameter("name");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageNum", page);
        map.put("pageSize", limit);
        map.put("name", name);
        result = userService.getUsers(map);
        response.getWriter().write(new Gson().toJson(result));
    }


    //登陆请求
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            BeanUtils.populate(user, request.getParameterMap());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        result = userService.login(user);
        response.getWriter().write(new Gson().toJson(result));
    }

    //注册请求
    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            user.setReTime(LocalDateTime.now());
            BeanUtils.populate(user, request.getParameterMap());
            result = userService.register(user);
        } catch (IllegalAccessException | InvocationTargetException | SQLException e) {
            e.printStackTrace();
        }
        response.getWriter().write(new Gson().toJson(result));
    }


    //获取省数据
    private void getProvince(HttpServletRequest request, HttpServletResponse response) throws IOException {
        result = userService.getProvince();
        response.getWriter().write(new Gson().toJson(result));
    }

    //获取市数据
    private void getCity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String provincecode = request.getParameter("value");
        result = userService.getCity(provincecode);
        response.getWriter().write(new Gson().toJson(result));
    }

    //获取区数据
    private void getArea(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String citycode = request.getParameter("value");
        result = userService.getArea(citycode);
        response.getWriter().write(new Gson().toJson(result));
    }


}
