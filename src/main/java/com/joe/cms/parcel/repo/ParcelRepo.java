package com.joe.cms.parcel.repo;

import com.joe.cms.parcel.model.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelRepo extends JpaRepository<Parcel, Long> {
}
