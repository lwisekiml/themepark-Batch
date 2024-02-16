package com.freepass.themepark.repository.ride;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest // 직접 db에 붙여 롤백 없이 수행 후 데이터를 직접 보기 위함
@ActiveProfiles("test")
class RideRepositoryTest {

    @Autowired
    private RideRepository rideRepository;

    @DisplayName("save test")
    @Test
    public void test_save() {
        // given
        RideEntity rideEntity = new RideEntity();
        rideEntity.setRideName("Rock Spin 12주");
        rideEntity.setPeriod(84);

        // when
        rideRepository.save(rideEntity);

        // then
        assertNotNull(rideEntity.getRideSeq());
    }

    @DisplayName("created at 기반으로 이후에 있는 값을 가져오도록 한다.")
    @Test
    public void test_findByCreatedAtAfter() {
        // given
        LocalDateTime dateTime = LocalDateTime.now().minusMinutes(1);

        RideEntity rideEntity0 = new RideEntity();
        rideEntity0.setRideName("학생 전용 3개월");
        rideEntity0.setPeriod(90);
        rideRepository.save(rideEntity0);

        RideEntity rideEntity1 = new RideEntity();
        rideEntity1.setRideName("학생 전용 6개월");
        rideEntity1.setPeriod(180);
        rideRepository.save(rideEntity1);

        // when
        final List<RideEntity> rideEntities = rideRepository.findByCreatedAtAfter(dateTime, PageRequest.of(0, 1, Sort.by("rideSeq").descending()));

        // then
        assertEquals(1, rideEntities.size());
        assertEquals(rideEntity1.getRideSeq(), rideEntities.get(0).getRideSeq());
    }

    @DisplayName("update test")
    @Test
    public void test_updateCountAndPeriod() {
        // given
        RideEntity rideEntity = new RideEntity();
        rideEntity.setRideName("학생 전용 이벤트 3개월");
        rideEntity.setPeriod(90);
        rideRepository.save(rideEntity);

        // when
        int updatedCount = rideRepository.updateCountAndPeriod(rideEntity.getRideSeq(), 30, 120);
        final RideEntity updatedRideEntity = rideRepository.findById(rideEntity.getRideSeq()).get();

        // then
        assertEquals(30, updatedRideEntity.getCount());
        assertEquals(30, updatedRideEntity.getCount());
        assertEquals(120, updatedRideEntity.getPeriod());
    }

    @DisplayName("delete test")
    @Test
    public void test_delete() {
        // given
        RideEntity rideEntity = new RideEntity();
        rideEntity.setRideName("제거할 이용권");
        rideEntity.setCount(1);
        RideEntity newRideEntity = rideRepository.save(rideEntity);

        // when
        rideRepository.deleteById(newRideEntity.getRideSeq());

        // then
        assertTrue(rideRepository.findById(newRideEntity.getRideSeq()).isEmpty());
    }
}