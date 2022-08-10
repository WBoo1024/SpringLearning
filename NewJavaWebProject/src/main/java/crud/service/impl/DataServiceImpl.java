package crud.service.impl;

import crud.bean.Teacher;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import crud.bean.User;
import crud.mapper.DataMapper;
import crud.service.DataService;
import crud.vo.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public Result allSearch(Map<String, Object> map) {
        PageHelper.startPage(map);
        List<Teacher> list = dataMapper.allSearch(map);
        PageInfo<Teacher> pageInfo = new PageInfo<>(list);
        Result result = Result.ok();
        result.setData(pageInfo.getList());
        result.setCount(pageInfo.getTotal());
        return result;
    }

    @Override
    public Result add(Teacher teacher) {
        if (dataMapper.add(teacher) == 1) {
            return Result.ok();
        }
        return Result.failed();
    }

    @Override
    public Result delete(String[] ids) {
        int count = dataMapper.delete(ids);
        if (ids.length == count) {
            return Result.ok();
        }
        return Result.failed();
    }

    @Override
    public Result update(Teacher teacher) {
        int count = dataMapper.update(teacher);
        if (count == 1) {
            return Result.ok();
        }
        return Result.failed();
    }

    @Override
    public Result upload(User user) {
        int count = dataMapper.upload(user);
        if (count == 1) {
            Result result = Result.ok();
            result.setData(user.getPicture());
            return result;
        }
        return Result.failed();
    }
}
