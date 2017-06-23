package com.nascenia.domain.repository;

import com.nascenia.domain.entity.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** Created by mozammal on 6/6/17. */
@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Long> {}
