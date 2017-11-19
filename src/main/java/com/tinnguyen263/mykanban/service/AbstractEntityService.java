package com.tinnguyen263.mykanban.service;

import java.util.List;

interface AbstractEntityService<Object, Key> {
    List<Object> findAll();

    Object findByKey(Key k);

    boolean checkExisted(Key o);

    Object saveOrUpdate(Object o);

    void deleteByKey(Key k);
}
