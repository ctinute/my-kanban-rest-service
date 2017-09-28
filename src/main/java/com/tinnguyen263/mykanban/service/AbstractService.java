package com.tinnguyen263.mykanban.service;

import java.util.List;

public interface AbstractService<Object, Key> {
    List<Object> getAll();
    Object getByKey(Key k);
    Object saveOrUpdate(Object o);
    boolean deleteUser(Key k);
}
