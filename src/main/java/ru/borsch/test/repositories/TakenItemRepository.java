package ru.borsch.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.borsch.test.model.TakenItem;

import java.util.List;

public interface TakenItemRepository extends JpaRepository<TakenItem, Long> {
    List<TakenItem> findByDiscName(String name);
    List<TakenItem> findByUserUsername(String username);
    List<TakenItem> findByDiscOwnerUsername(String username);
}
