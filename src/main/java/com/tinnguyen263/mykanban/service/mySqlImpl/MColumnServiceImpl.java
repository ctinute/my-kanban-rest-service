package com.tinnguyen263.mykanban.service.mySqlImpl;

import com.tinnguyen263.mykanban.model.MColumn;
import com.tinnguyen263.mykanban.repository.MColumnRepository;
import com.tinnguyen263.mykanban.service.MColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class MColumnServiceImpl implements MColumnService {

    private final MColumnRepository mColumnRepository;

    @Autowired
    public MColumnServiceImpl(MColumnRepository mColumnRepository) {
        this.mColumnRepository = mColumnRepository;
    }

    @Override
    public List<MColumn> findAll() {
        return mColumnRepository.findAll();
    }

    @Override
    public MColumn findByKey(Integer k) {
        return mColumnRepository.findOne(k);
    }

    @Override
    public boolean checkExisted(Integer o) {
        return mColumnRepository.findOne(o) != null;
    }

    @Override
    public MColumn saveOrUpdate(MColumn o) {
        return mColumnRepository.save(o);
    }

    @Override
    public void deleteByKey(Integer k) {
        mColumnRepository.delete(k);
    }

    @Override
    public Collection<MColumn> getColumnsOfProject(Integer projectId) {
        return mColumnRepository.getMColumnsByProjectIdOrderByDisplayOrder(projectId);
    }
}
