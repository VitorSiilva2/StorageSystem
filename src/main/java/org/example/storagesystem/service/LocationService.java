package org.example.storagesystem.service;

import lombok.RequiredArgsConstructor;
import org.example.storagesystem.model.LocationModel;
import org.example.storagesystem.repository.LocationRepository;
import org.example.storagesystem.repository.ProductRepository;
import org.example.storagesystem.service.serviceInterfaceImpl.LocationServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LocationService implements LocationServiceImpl {

    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;

    @Override
    public LocationModel createPosition(LocationModel locationModel) {
        return locationRepository.save(locationModel);
    }

    @Override
    public Optional<LocationModel> findPosition(Integer id) {
        return locationRepository.findById(id);
    }

    @Override
    public Optional<LocationModel> findByPositionName(String position) {
        return locationRepository.findByPositionName(position);
    }


}
