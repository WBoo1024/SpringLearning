package crud.servlet;

import com.sun.org.apache.bcel.internal.generic.NEW;
import crud.bean.Teacher;
import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;
import crud.service.DataService;
import crud.service.impl.DataServiceImpl;
import crud.vo.Result;
import org.omg.CORBA.PRIVATE_MEMBER;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @author WANG
 */
@WebServlet("/index/*")
public class IndexServlet extends HttpServlet {
    @Autowired
    private DataService dataService;

    @Autowired
    private Result result;

    @Autowired
    private Teacher teacher;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String uri = request.getRequestURI();
        String URI = uri.substring(uri.lastIndexOf("/"));
        switch (URI) {
            case "/jump":
                request.getRequestDispatcher("/WEB-INF/table.html").forward(request, response);
                break;
            case "/linkage":
                request.getRequestDispatcher("/WEB-INF/linkage.html").forward(request, response);
                break;
            case "/allSearch":
                allSearch(request,response);
                break;
            case "/add":
                add(request,response);
                break;
            case "/delete":
                delete(request,response);
                break;
            case "/update":
                update(request, response);
                break;
            default:
                break;
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            BeanUtils.populate(teacher,request.getParameterMap());
            result = dataService.update(teacher);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        response.getWriter().write(new Gson().toJson(result));
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] ids = request.getParameterValues("datas[]");
        result = dataService.delete(ids);
        response.getWriter().write(new Gson().toJson(result));
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            BeanUtils.populate(teacher,request.getParameterMap());
            result = dataService.add(teacher);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        response.getWriter().write(new Gson().toJson(result));
    }

    //表格数据
    private void allSearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        String name = request.getParameter("name");
        Map<String, Object> map = new HashMap<String, Object>(10);
        map.put("pageNum",page);
        map.put("pageSize",limit);
        map.put("name",name);
        result = dataService.allSearch(map);
        response.getWriter().write(new Gson().toJson(result));
    }
}
