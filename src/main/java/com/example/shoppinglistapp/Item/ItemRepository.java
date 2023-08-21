package com.example.shoppinglistapp.Item;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemRepository extends CassandraRepository<Item, ItemPrimaryKey> {

    Optional<Item> findByIdId(UUID id);

    Optional<Item> findById(ItemPrimaryKey id);


}
