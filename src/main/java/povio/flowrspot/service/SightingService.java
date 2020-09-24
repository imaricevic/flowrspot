package povio.flowrspot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import povio.flowrspot.model.Sighting;
import povio.flowrspot.model.User;
import povio.flowrspot.repository.SightingRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SightingService {

    @Autowired
    private SightingRepository sightingRepository;

    @Autowired
    private QuoteService quoteService;

    public boolean existsByLikedByUsers_Username(String username) {
        return sightingRepository.existsByLikedByUsers_Username(username);
    }

    public Sighting save(Sighting sighting) {
        //insert
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (sighting.getId() == null) {
            sighting.setUser(user);
            sighting.setQuote(quoteService.retrieveQuoteOfTheDay());
            return sightingRepository.save(sighting);
        }
        //update
        if (isMySighting(sighting)) {
            return sightingRepository.save(sighting);
        }
        return null;
    }

    public boolean isMySighting(Sighting sighting) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (sighting != null && sighting.getUser().getId() == user.getId()) {
            return true;
        }

        return false;
    }

    public Sighting like(Sighting sighting) {
        if (likedAlready(sighting)) {
            return null;
        }
        List<User> usersLiked = sighting.getLikedByUsers();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        usersLiked.add(user);
        return sightingRepository.save(sighting);
    }

    public boolean likedAlready(Sighting sighting) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (sightingRepository.existsByLikedByUsers_Username(user.getUsername())) {
            return true;
        }
        return false;
    }

    public boolean removeLike(Sighting sighting) {
        if (!likedAlready(sighting)) {
            return false;
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        sightingRepository.removeLike(sighting.getId(), user.getId());
        return true;
    }

}
