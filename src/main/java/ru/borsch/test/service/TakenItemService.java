package ru.borsch.test.service;

import ru.borsch.test.model.TakenItem;

import java.util.List;

public interface TakenItemService {

    List<TakenItem> findByUserUserName(String username);
    List<TakenItem> findByDiscOwnerUserName(String username);
    List<TakenItem> findByDiscName(String name);

    TakenItem saveTakenItem(TakenItem takenItem);
    TakenItem updateTakenItem(TakenItem takenItem);
    void deleteTakenItem(Long id);
}
