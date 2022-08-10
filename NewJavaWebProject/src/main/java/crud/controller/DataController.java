package crud.controller;

import crud.bean.Teacher;
import crud.bean.User;
import crud.service.DataService;
import crud.vo.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 壹零二四
 * @date 2022年08月08日 21:56
 */
@RestController
@RequestMapping("/index")
public class DataController {

    @Resource
    private DataService dataService;

    @PostMapping("/allSearch")
    public Result allSearch(int page,int limit,String name) {
        Map<String, Object> map = new HashMap<>(10);
        map.put("pageNum", page);
        map.put("pageSize", limit);
        map.put("name", name);
        return dataService.allSearch(map);
    }

    @PostMapping("/add")
    public Result add(Teacher teacher){
        return dataService.add(teacher);
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam("ids[]") String[] ids){
        return dataService.delete(ids);

    }

    @PostMapping("/update")
    public Result update(Teacher teacher){
        return dataService.update(teacher);
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile file, User user, HttpSession session) throws IOException {
        //文件名 file.getOriginalFilename()
        //文件类型 file.getContentType()
        //文件大小 file.getSize()
        //文件输入流 file.getInputStream()

        //获得文件上传的路径

        String path =session.getServletContext().getRealPath("/static/files/");

        File newFile=new File(path);
        //文件夹不存在则重建
        if(!newFile.exists()){
            newFile.mkdirs();
        }
        //上传

        UUID uuid = UUID.randomUUID();
        String fileName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String filePath=path + uuid + fileName;
        file.transferTo(new File(filePath));


        //调用service方法 将文件信息插入数据库
        String relPath = "http://localhost:8080/" + filePath.substring(filePath.lastIndexOf("static"));
        user.setPicture(relPath);
        return dataService.upload(user);
    }
}

