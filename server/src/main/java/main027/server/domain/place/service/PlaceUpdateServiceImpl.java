package main027.server.domain.place.service;

import lombok.RequiredArgsConstructor;
import main027.server.domain.place.entity.Place;
import main027.server.domain.place.repository.PlaceRepository;
import main027.server.domain.place.verifier.PlaceVerifier;
import main027.server.global.utils.CustomBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceUpdateServiceImpl implements PlaceUpdateService {

    private final PlaceRepository placeRepository;
    private final CustomBeanUtils<Place> beanUtils;
    private final PlaceVerifier placeVerifier;

    public Place updatePlace(Place place) {
        Place verifiedPlace = placeVerifier.findVerifiedPlace(place.getPlaceId());
        Place updatedPlace = beanUtils.copyNonNullProperties(place, verifiedPlace);
        return placeRepository.save(updatedPlace);
    }
}
