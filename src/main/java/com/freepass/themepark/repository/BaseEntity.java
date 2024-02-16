package com.freepass.themepark.repository;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 공통 매핑 정보를 가진 Entity
 * @MappedSuperclass
 * 상속받은 entity에서 아래 필드들(createdAt, modifiedAt)을 컬럼으로 인식하여 컬럼으로 사용할 수 있다.
 *
 * @EntityListeners(AuditingEntityListener.class)
 * Hibernate문서에는 JPA 엔티티의 이벤트가 발생할 때 콜백 처리하고 코드를 실행하는 방법이라고 소개
 * 인자로 커스텀 콜백을 요청할 클래스를 지정해주면 되는데 어디팅을 수행할 때는 JPA에서 제공하는 Auditing Entity Listener를 인자로 넘기면 된다.
 * touchForCreate, touchForUpdate 이것을 통해 엔티티의 생성, 수정이 발생하면 콜백이 실행되어 시간을 만들어 준다.
 * (Auditing 정보를 캡처하는 Listener 이다..)
 *
 * @CreatedDate
 * 엔티티가 생성해서 저장될 때 자동 저장된다는 의미
 *
 * @LastModifiedDat
 * 변경할 때 업데이트 하게된다는 의미
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate // 생성 일시를 생성
    @Column(updatable = false, nullable = false) // 업데이트를 하지 않도록, null이 되지 않도록 명시
    private LocalDateTime createdAt;

    @LastModifiedDate // 마지막 수정 일시 생성
    private LocalDateTime modifiedAt;

}
