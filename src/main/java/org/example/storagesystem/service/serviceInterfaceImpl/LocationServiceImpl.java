package org.example.storagesystem.service.serviceInterfaceImpl;

import org.example.storagesystem.model.LocationModel;

import java.util.Optional;

public interface LocationServiceImpl {

    LocationModel createPosition (LocationModel locationModel);

    Optional<LocationModel> findPosition (Integer id);

    Optional<LocationModel> findByPositionName (String position);


}
