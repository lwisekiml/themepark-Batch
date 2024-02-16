package com.freepass.themepark.repository.ride;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface RideRepository extends JpaRepository<RideEntity, Integer> {

    List<RideEntity> findByCreatedAtAfter(LocalDateTime dateTime, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE RideEntity r" +
            "          SET r.count = :count," +
            "              r.period = :period" +
            "        WHERE r.rideSeq = :rideSeq")
    int updateCountAndPeriod(@Param("rideSeq") Integer rideSeq, @Param("count") Integer count, @Param("period") Integer period);
}
