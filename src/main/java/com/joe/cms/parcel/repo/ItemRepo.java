package com.joe.cms.parcel.repo;

import com.joe.cms.parcel.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE LOWER(i.itemName) = LOWER(:itemName)")
    Item findByNameIgnoreCase(@Param("itemName") String itemName);
}
