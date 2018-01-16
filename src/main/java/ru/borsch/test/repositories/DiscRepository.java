package ru.borsch.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.borsch.test.model.Disc;

import java.util.Collection;
import java.util.List;

public interface DiscRepository extends JpaRepository<Disc, Long> {

    @Query("select d from Disc d left outer join d.discUser d_u where d.owner.id= ?1 or d_u.id = ?2")
    List<Disc> findByOwnerOrUser(Long ownerId, Long discUserId);

    @Query("select d from Disc d left outer join d.discUser d_u where  d_u.id = null")
    List<Disc> findAllFreeDiscs();

    @Query("select d from Disc d left outer join d.discUser d_u where  d_u.id is not null and d.owner.id= ?1")
    List<Disc> findAllGivenDiscs(Long ownerId);

    @Query("select d from Disc d left outer join d.discUser d_u where d.owner.id= ?1 and d_u.id = ?2")
    List<Disc> findByOwnerIdAndUserId(Long ownerId, Long discUserId);

    List<Disc> findByDiscUserId(Long discUserId);


    List<Disc> findByOwnerUsername(String userName);
    List<Disc> findByName(String name);
}
