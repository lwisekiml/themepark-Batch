package com.freepass.themepark.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/* jpa를 사용해서 엔티티를 테이블에 맵핑을 할 때 공통적으로 엔티티를 가지고 있는 필드 컬럼들이 존재한다.
 * 대표적으로 생성일시, 수정일시 필드가 있다. auditis는 감시하다라는 뜻으로 스프링 데이터 jpa에서는 시간에 대해 자동으로 값을 넣어주는 기능이다.
 * 엔티티를 insert나 update하는 경우 매번 시간 데이트를 입력을 해서 보아야 하는데 audits를 사용하면 자동으로 이 시간을 맵핑해서 DB테이블에 넣어 준다.
 *
 * BaseEntity class 있는 설정들을 enable 해주는 것이 @EnableJpaAuditing 이다.
 */
@EnableJpaAuditing
@Configuration
public class JpaConfig {
}
