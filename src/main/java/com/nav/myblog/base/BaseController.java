package com.nav.myblog.base;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nav.myblog.comments.rtJson.Result;
import com.nav.myblog.comments.rtJson.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class BaseController<T> {

    @Autowired
    private BaseService<T> baseService;
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result<T> get(@PathVariable Integer id) {
        T t = baseService.queryById(id);
        return ResultUtil.data(t);
    }
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    @ResponseBody
    public Result<List<T>> getAll(){
        List<T> list = baseService.queryAll();
        return ResultUtil.data(list);
    }
    @RequestMapping(value = "/getByPage",method = RequestMethod.GET)
    @ResponseBody
    public Result<PageInfo<T>> getByPage(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize){
        PageHelper.startPage(pageNo, pageSize);
        PageInfo<T> pageinfo = new PageInfo<>(baseService.queryAll());
        return new ResultUtil<PageInfo<T>>().setData(pageinfo);
    }
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public Result<T> save(T t){
        return new ResultUtil<T>().setData(baseService.save(t));
    }
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @ResponseBody
    public Result<T> update(T entity){
        T e = baseService.update(entity);
        return new ResultUtil<T>().setData(e);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ResponseBody
    public Result<Object> delByIds(@RequestParam Integer[] ids){

        for(Integer id:ids){
            baseService.deleteById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
