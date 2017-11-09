package com.tinnguyen263.mykanban.service;

import java.util.List;

public interface AbstractService<Object, Key> {
    List<Object> findAll();

    Object findByKey(Key k);
    Object saveOrUpdate(Object o);

    void deleteByKey(Key k);
}
