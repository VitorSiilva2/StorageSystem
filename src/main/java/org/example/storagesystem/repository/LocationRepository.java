package org.example.storagesystem.repository;

import org.example.storagesystem.model.LocationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<LocationModel, Integer> {


    @Query(value = "SELECT * FROM location l WHERE l.position LIKE CONCAT('%', ? , '%')", nativeQuery = true)
    Optional<LocationModel> findByPositionName(@Param("position") String position);

    //@Query("SELECT l FROM LocationModel l WHERE l.position = :position")
    //Optional<LocationModel> findByPositionName(@Param("position") String position);

    //@Query(value = "select * from client c where c.name like '%:name%'", nativeQuery = true)
    //List<Client> encontrarNome(String name);
}