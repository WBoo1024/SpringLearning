package crud.service.impl;

import crud.bean.Teacher;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import crud.mapper.DataMapper;
import org.apache.ibatis.session.SqlSession;
import crud.service.DataService;
import crud.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @program: NewJavaWebProject
 * @description:
 * @author: 壹零二四
 * @created: 2022/07/20 20:31
 */
@Service
public class DataServiceImpl implements DataService {
    @Resource(name = "dataMapper")
    private DataMapper dataMapper;

    @Override
    public Result allSearch(Map<String,Object> map) {

        Result result = new Result();

        PageHelper.startPage(map);
        List<Teacher> list = dataMapper.allSearch(map);
        PageInfo<Teacher> pageInfo = new PageInfo<>(list);
        result.setData(pageInfo.getList());
        result.setCount(pageInfo.getTotal());
        return result;
    }


    @Override
    public Result add(Teacher teacher) {

        Result result = new Result();

        if (dataMapper.add(teacher) == 1){
            result = Result.ok();
        };
        return result;
    }

    @Override
    public Result delete(String[] ids) {
        Result result = Result.failed();

        int count = dataMapper.delete(ids);
        if (ids.length == count){
            result = Result.ok();
            return result;
        };
        return result;
    }

    @Override
    public Result update(Teacher teacher) {
        Result result = Result.failed();

        dataMapper.update(teacher);
        return Result.ok();
    }


}
