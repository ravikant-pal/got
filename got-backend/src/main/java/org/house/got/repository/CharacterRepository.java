package org.house.got.repository;

import org.house.got.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
    Optional<Character> findByCharacterName(String characterName);

    List<Character> findByChildrenContains(Character child);

    List<Character> findByKilledContains(Character killer);

    @Query("SELECT c FROM Character c JOIN c.houses h WHERE h.name = :houseName")
    List<Character> findByHouseName(@Param("houseName") String houseName);
}
