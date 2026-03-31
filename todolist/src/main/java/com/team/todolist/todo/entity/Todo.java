package com.team.todolist.todo.entity;

import java.time.LocalDateTime;
import com.team.todolist.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "todos")
@Getter
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 내용 (빈값 방지)
    @Column(nullable = false)
    private String content;

    // 상태 (enum)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TodoStatus status;

    // 생성 시간
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 수정 시간
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // 생성용 생성자
    @Builder
    public Todo(User user, String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("내용은 비어 있을 수 없습니다.");
        }

        this.user = user;
        this.content = content;
        this.status = TodoStatus.PENDING; // 기본값
    }

    // 내용 수정
    public void updateContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("내용은 비어 있을 수 없습니다.");
        }
        this.content = content;
    }

    // 상태 변경
    public void updateStatus(TodoStatus status) {
        this.status = status;
    }

    // 생성 시 자동 시간 설정
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 수정 시 자동 시간 변경
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
