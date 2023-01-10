package main027.server.domain.place.service;

import lombok.RequiredArgsConstructor;
import main027.server.domain.place.entity.Place;
import main027.server.domain.place.repository.PlaceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceService {

    private final PlaceRepository placeRepository;

    public Place createPlace(Place place) {
        verifyExistsPlace(place.getName());
        return placeRepository.save(place);
    }

    public Place findPlace(Long placeId) {
        return findVerifiedPlace(placeId);
    }

    public Page<Place> findPlaces(Pageable pageable) {
        return placeRepository.findAll(pageable);
    }

    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    public Place updatePlace(Place place) {
        Place findPlace = findVerifiedPlace(place.getPlaceId());
        Optional.ofNullable(place.getDescription()).ifPresent(description -> findPlace.setDescription(description));
        return placeRepository.save(findPlace);
    }

    public void deletePlace(Long placeId) {
        Place verifiedPlace = findVerifiedPlace(placeId);
        placeRepository.delete(verifiedPlace);
    }


    public Place findVerifiedPlace(Long placeId) {
        Optional<Place> optionalPlace = placeRepository.findById(placeId);
        Place findPlace = optionalPlace.orElseThrow(() ->
                                                            new RuntimeException("No Such Place"));
        return findPlace;
    }

    public void verifyExistsPlace(String name) {
        Optional<Place> place = placeRepository.findByName(name);
        if (place.isPresent())
            throw new RuntimeException("Place Already Exists");
    }
}
