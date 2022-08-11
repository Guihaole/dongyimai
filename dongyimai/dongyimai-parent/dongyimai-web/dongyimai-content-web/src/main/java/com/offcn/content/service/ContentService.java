package com.offcn.content.service;

import com.offcn.content.pojo.Content;
import com.offcn.entity.PageResult;
import com.offcn.entity.Result;

import java.util.List;

public interface ContentService {
    Result<PageResult<Content>> findPage(Content content, int page, int size);

    Result<PageResult<Content>> findPage(int page,  int size);

    Result<List<Content>> findList(Content content);

    Result delete(Long id);

    Result update(Content content,Long id);

    Result add(Content content);

    Result<Content> findById(Long id);

    Result<List<Content>> findAll();

    Result<List<Content>> findByCategory(Long id);
}
