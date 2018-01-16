package ru.borsch.test.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.borsch.test.model.TakenItem;
import ru.borsch.test.repositories.TakenItemRepository;

import java.util.List;

@Service("takenItemService")
@Transactional
public class TakenItemServiceImpl implements TakenItemService{

    @Autowired
    private TakenItemRepository takenItemRepository;

    @Override
    public List<TakenItem> findByUserUserName(String username) {
        return takenItemRepository.findByUserUsername(username);
    }

    @Override
    public List<TakenItem> findByDiscOwnerUserName(String username) {
        return takenItemRepository.findByDiscOwnerUsername(username);
    }

    @Override
    public List<TakenItem> findByDiscName(String name) {
        return takenItemRepository.findByDiscName(name);
    }

    @Override
    public TakenItem saveTakenItem(TakenItem takenItem) {
        return takenItemRepository.save(takenItem);
    }

    @Override
    public TakenItem updateTakenItem(TakenItem takenItem) {
        return saveTakenItem(takenItem);
    }

    @Override
    public void deleteTakenItem(Long id) {
        takenItemRepository.delete(id);
    }
}
