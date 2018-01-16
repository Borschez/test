package ru.borsch.test.service;

import org.springframework.data.jpa.repository.Query;
import ru.borsch.test.model.Disc;
import ru.borsch.test.model.User;

import java.util.List;

public interface DiscService {
    Disc findById(Long id);

    List<Disc> findByOwnerOrUser(Long ownerId, Long discUserId);

    List<Disc> findAllFreeDiscs();

    List<Disc> findAllGivenDiscs(Long ownerId);

    List<Disc> findByOwnerIdAndUserId(Long ownerId, Long discUserId);

    List<Disc> findByDiscUserId(Long discUserId);

    List<Disc> findByName(String name);

    List<Disc> findByOwner(User owner);

    boolean isDiscTaken(Disc disc);

    List<Disc> findByTakenItemUser(User user);

    List<Disc> findAllDiscs();

    Disc saveDisc(Disc disc);

    Disc updateDisc(Disc disc);

    void deleteDiscById(Long id);

    Disc takeDisc(Long id);

    Disc getBackDisc(Long id);

    Disc getBackDisc(Disc disc);

    Disc takeDisc(Disc disc);
}
