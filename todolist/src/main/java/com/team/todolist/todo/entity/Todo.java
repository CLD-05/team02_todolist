package com.team.todolist.todo.entity;

import com.team.todolist.common.entity.BaseEntity;
import com.team.todolist.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "todos")
@Getter
@NoArgsConstructor
public class Todo extends BaseEntity { // BaseEntity 상속 (createdAt, updatedAt 자동 관리)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 내용
    @Column(nullable = false)
    private String content;

    // 상태 (enum)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TodoStatus status;

    // 생성용 생성자
    public Todo(User user, String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("내용은 비어 있을 수 없습니다.");
        }
        this.user = user;
        this.content = content;
        this.status = TodoStatus.PENDING;
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
}