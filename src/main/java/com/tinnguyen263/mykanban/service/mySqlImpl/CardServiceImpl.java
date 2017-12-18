package com.tinnguyen263.mykanban.service.mySqlImpl;

import com.tinnguyen263.mykanban.model.Card;
import com.tinnguyen263.mykanban.repository.CardRepository;
import com.tinnguyen263.mykanban.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public Card findByKey(Integer k) {
        return cardRepository.findOne(k);
    }

    @Override
    public boolean checkExisted(Integer o) {
        return cardRepository.findOne(o) != null;
    }

    @Override
    public Card saveOrUpdate(Card o) {
        return cardRepository.save(o);
    }

    @Override
    public void deleteByKey(Integer k) {
        cardRepository.delete(k);
    }
}
