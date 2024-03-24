package com.example.assessment.repository;

import com.example.assessment.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByNameAndParentId(String name, Long parentId);

    Optional<Item> findByName(String name);

}