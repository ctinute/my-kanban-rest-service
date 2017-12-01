package com.tinnguyen263.mykanban.service.mySqlImpl;

import com.tinnguyen263.mykanban.model.Label;
import com.tinnguyen263.mykanban.repository.LabelRepository;
import com.tinnguyen263.mykanban.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;

    @Autowired
    public LabelServiceImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public List<Label> findAll() {
        return labelRepository.findAll();
    }

    @Override
    public Label findByKey(Integer k) {
        return labelRepository.findOne(k);
    }

    @Override
    public boolean checkExisted(Integer o) {
        return labelRepository.findOne(o) != null;
    }

    @Override
    public Label saveOrUpdate(Label o) {
        return labelRepository.save(o);
    }

    @Override
    public void deleteByKey(Integer k) {
        labelRepository.delete(k);
    }
}
