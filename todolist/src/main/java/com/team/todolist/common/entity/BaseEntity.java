 package com.team.todolist.common.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass // 테이블로 생성되지 않고, 상속받는 클래스에 필드만 제공
@EntityListeners(AuditingEntityListener.class) // 자동으로 시간을 기록하기 위해 필수
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false) // 생성 시간은 수정 불가
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}