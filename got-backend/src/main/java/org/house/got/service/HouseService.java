package org.house.got.service;

import org.house.got.model.House;

import java.util.List;
import java.util.Optional;

public interface HouseService {
    Optional<House> getHouseById(long id);
    Optional<House> findHouseByName(String name);
    List<House> getAllHouse();
    House findOrCreateHouse(String houseName);

    void saveHouse(House house);
}
