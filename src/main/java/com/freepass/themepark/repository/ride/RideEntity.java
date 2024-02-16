package com.freepass.themepark.repository.ride;

import com.freepass.themepark.repository.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "ride")
public class RideEntity extends BaseEntity {

    @Id // pk 정의
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임 (AUTO_INCREMENT)
    private Integer rideSeq;

    private String rideName;
    private Integer count;
    private Integer period;

}
