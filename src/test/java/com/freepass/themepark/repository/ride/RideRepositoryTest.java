package com.freepass.themepark.repository.ride;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest // 직접 db에 붙여 롤백 없이 수행 후 데이터를 직접 보기 위함
@ActiveProfiles("test")
class RideRepositoryTest {

    @Autowired
    private RideRepository rideRepository;

    @Test
    public void test_save() {
        // given
        RideEntity rideEntity = new RideEntity();
        rideEntity.setRideName("Rock Spin 12주");
        rideEntity.setPeriod(84);

        // when


        // then
        assertNotNull(rideEntity.getRideSeq());
    }

}