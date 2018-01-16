package ru.borsch.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.borsch.test.model.Disc;
import ru.borsch.test.model.User;
import ru.borsch.test.repositories.DiscRepository;

import java.util.List;

@Service("discService")
@Transactional
public class DiscServiceImpl implements DiscService {

    @Autowired
    private DiscRepository discRepository;

    @Autowired
    private UserService userService;

    @Override
    public Disc findById(Long id) {
        return discRepository.findOne(id);
    }

    @Override
    public List<Disc> findByOwnerOrUser(Long ownerId, Long discUserId) {
        return discRepository.findByOwnerOrUser(ownerId, discUserId);
    }

    @Override
    public List<Disc> findAllFreeDiscs() {
        return discRepository.findAllFreeDiscs();
    }

    @Override
    public List<Disc> findAllGivenDiscs(Long ownerId) {
        return discRepository.findAllGivenDiscs(ownerId);
    }

    @Override
    public List<Disc> findByOwnerIdAndUserId(Long ownerId, Long discUserId) {
        return discRepository.findByOwnerIdAndUserId(ownerId, discUserId);
    }

    @Override
    public List<Disc> findByDiscUserId(Long discUserId) {
        return discRepository.findByDiscUserId(discUserId);
    }

    @Override
    public List<Disc> findByName(String name) {
        return discRepository.findByName(name);
    }

    @Override
    public List<Disc> findByOwner(User owner) {
        return discRepository.findByOwnerUsername(owner.getUsername());
    }

    @Override
    public boolean isDiscTaken(Disc disc) {
        return false;
    }

    @Override
    public List<Disc> findByTakenItemUser(User user) {
        return null;
    }

    @Override
    public List<Disc> findAllDiscs() {
        return discRepository.findAll();
    }

    @Override
    public Disc saveDisc(Disc disc) {
        disc.setOwner(userService.getCurrentUser());
        return discRepository.save(disc);
    }

    @Override
    public Disc updateDisc(Disc disc) {
        return discRepository.save(disc);
    }

    @Override
    public void deleteDiscById(Long id) {
        discRepository.delete(id);
    }

    @Override
    public Disc takeDisc(Long id) {
        return takeDisc(discRepository.getOne(id));
    }

    @Override
    public Disc getBackDisc(Long id) {
        return getBackDisc(discRepository.getOne(id));
    }

    @Override
    public Disc getBackDisc(Disc disc) {
        disc.setDiscUser(null);
        return discRepository.save(disc);
    }

    @Override
    public Disc takeDisc(Disc disc){
        if (disc.getDiscUser() == null){
            disc.setDiscUser(userService.getCurrentUser());
            return discRepository.save(disc);
        }
        return disc;
    }
}
