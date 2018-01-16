package ru.borsch.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.borsch.test.model.Disc;
import ru.borsch.test.model.User;
import ru.borsch.test.service.DiscService;
import ru.borsch.test.service.UserService;
import ru.borsch.test.util.CustomErrorType;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {

    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    UserService userService;

    @Autowired
    DiscService discService;

    // -------------------Retrieve All Users---------------------------------------------

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    // -------------------Retrieve Single User------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        logger.info("Fetching User with id {}", id);
        User user = userService.findById(id);
        if (user == null) {
            logger.error("User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    // -------------------Create a User-------------------------------------------

    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", user);

        if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getUsername());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " +
                    user.getUsername() + " already exist."),HttpStatus.CONFLICT);
        }
        userService.saveUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    // ------------------- Update a User ------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        logger.info("Updating User with id {}", id);

        User currentUser = userService.findById(id);

        if (currentUser == null) {
            logger.error("Unable to update. User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentUser.setUsername(user.getUsername());

        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    // ------------------- Delete a User-----------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting User with id {}", id);

        User user = userService.findById(id);
        if (user == null) {
            logger.error("Unable to delete. User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/discs/filter/ownerName={diskOwnerName}&discUserName={discUserName}/", method = RequestMethod.GET)
    public ResponseEntity<List<Disc>> findByFilter(
            @PathVariable("diskOwnerName") String ownerName,
            @PathVariable("discUserName") String discUserName) {

        User owner = userService.findByUsername(ownerName);
        User discUser = userService.findByUsername(discUserName);
        List<Disc> discs = null;
        if (owner == null && discUser == null){// filter Free Discs
            discs = discService.findAllFreeDiscs();
        }else if (owner == null && discUser != null){//filter Using Discs
            discs = discService.findByDiscUserId(discUser.getId());
        }else if (owner != null && discUser == null){//filter Giving Discs
            discs = discService.findAllGivenDiscs(owner.getId());
        }else if (owner != null && discUser != null) {// filter DashBoard
            discs = discService.findByOwnerOrUser(owner.getId(),discUser.getId());
        }

        if (discs == null || discs.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Disc>>(discs, HttpStatus.OK);
    }

    // -------------------Take Single Disc---------------------------------------------

    @RequestMapping(value = "/disc/{id}/take", method = RequestMethod.GET)
    public ResponseEntity<?> takeDisc(@PathVariable("id") long id) {
        logger.info("Fetching Disc with id {}", id);
        Disc disc = discService.findById(id);
        if (disc == null) {
            logger.error("Disc with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Disc with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        disc = discService.takeDisc(disc);

        return new ResponseEntity<Disc>(disc, HttpStatus.OK);
    }

    // -------------------Take Single Disc---------------------------------------------

    @RequestMapping(value = "/disc/{id}/get-back", method = RequestMethod.GET)
    public ResponseEntity<?> getBackDisc(@PathVariable("id") long id) {
        logger.info("Fetching Disc with id {}", id);
        Disc disc = discService.findById(id);
        if (disc == null) {
            logger.error("Disc with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Disc with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        disc = discService.getBackDisc(disc);

        return new ResponseEntity<Disc>(disc, HttpStatus.OK);
    }

    // -------------------Retrieve All Discs---------------------------------------------

    @RequestMapping(value = "/discs/", method = RequestMethod.GET)
    public ResponseEntity<List<Disc>> listAllDiscs() {
        List<Disc> discs = discService.findAllDiscs();
        if (discs.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Disc>>(discs, HttpStatus.OK);
    }


    // -------------------Retrieve Single Disc---------------------------------------------

    @RequestMapping(value = "/disc/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getDisc(@PathVariable("id") long id) {
        logger.info("Fetching Disc with id {}", id);
        Disc disc = discService.findById(id);
        if (disc == null) {
            logger.error("Disc with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Disc with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Disc>(disc, HttpStatus.OK);
    }

    // -------------------Create a Disc-------------------------------------------

    @RequestMapping(value = "/disc/", method = RequestMethod.POST)
    public ResponseEntity<?> createDisc(@RequestBody Disc disc, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Disc : {}", disc);

        discService.saveDisc(disc);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/disc/{id}").buildAndExpand(disc.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    // ------------------- Update a Disc ------------------------------------------------

    @RequestMapping(value = "/disc/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDisc(@PathVariable("id") long id, @RequestBody Disc disc) {
        logger.info("Updating Disc with id {}", id);

        Disc currentDisc = discService.findById(id);

        if (currentDisc == null) {
            logger.error("Unable to update. Disc with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. Disc with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentDisc.setName(disc.getName());
        currentDisc.setDescription(disc.getDescription());
        currentDisc.setPosterURL(disc.getPosterURL());
        currentDisc.setOwner(disc.getOwner());

        discService.updateDisc(currentDisc);

        return new ResponseEntity<Disc>(currentDisc, HttpStatus.OK);
    }

    // ------------------- Delete a Disc-----------------------------------------

    @RequestMapping(value = "/disc/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDisc(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting Disc with id {}", id);

        Disc disc = discService.findById(id);
        if (disc == null) {
            logger.error("Unable to delete. Disc with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. disc with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        discService.deleteDiscById(id);
        return new ResponseEntity<Disc>(HttpStatus.NO_CONTENT);
    }

}
