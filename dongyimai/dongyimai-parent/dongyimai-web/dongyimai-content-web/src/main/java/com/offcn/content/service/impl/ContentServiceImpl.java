package com.offcn.content.service.impl;

import com.offcn.content.fegin.ContentFeign;
import com.offcn.content.pojo.Content;
import com.offcn.content.service.ContentService;
import com.offcn.entity.PageResult;
import com.offcn.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentFeign contentFeign;

    @Override
    public Result<PageResult<Content>> findPage(Content content, int page, int size) {
        return contentFeign.findPage(content, page, size);
    }

    @Override
    public Result<PageResult<Content>> findPage(int page, int size) {
        return contentFeign.findPage(page, size);
    }

    @Override
    public Result<List<Content>> findList(Content content) {
        return contentFeign.findList(content);
    }

    @Override
    public Result delete(Long id) {
        return contentFeign.delete(id);
    }

    @Override
    public Result update(Content content, Long id) {
        return contentFeign.update(content, id);
    }

    @Override
    public Result add(Content content) {
        return contentFeign.add(content);
    }

    @Override
    public Result<Content> findById(Long id) {
        return contentFeign.findById(id);
    }

    @Override
    public Result<List<Content>> findAll() {
        return contentFeign.findAll();
    }

    @Override
    public Result<List<Content>> findByCategory(Long id) {
        return contentFeign.findByCategory(id);
    }
}
