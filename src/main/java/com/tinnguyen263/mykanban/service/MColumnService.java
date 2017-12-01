package com.tinnguyen263.mykanban.service;

import com.tinnguyen263.mykanban.model.MColumn;

import java.util.Collection;

public interface MColumnService extends AbstractEntityService<MColumn, Integer> {
    Collection<MColumn> getColumnsOfProject(Integer projectId);
}
