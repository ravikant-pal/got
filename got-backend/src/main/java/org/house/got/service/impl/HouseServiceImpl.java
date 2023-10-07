package org.house.got.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.house.got.model.House;
import org.house.got.repository.HouseRepository;
import org.house.got.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    @Autowired
    public HouseServiceImpl(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public Optional<House> getHouseById(long id) {
        return houseRepository.findById(id);
    }
    @Override
    public Optional<House> findHouseByName(String name) {
        return houseRepository.findByName(name);
    }

    @Override
    public List<House> getAllHouse() {
        return houseRepository.findAll();
    }

    @Override
    @Transactional
    public House findOrCreateHouse(String houseName) {
        Optional<House> houseByName = findHouseByName(houseName);
        if(houseByName.isPresent()) {
            return houseByName.get();
        } else {
            House house = new House();
            house.setName(houseName);
            return houseRepository.save(house);
        }
    }

    @Override
    @Transactional
    public void saveHouse(House house) {
        houseRepository.save(house);
    }
}
