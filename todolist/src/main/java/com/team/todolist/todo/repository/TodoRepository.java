package com.team.todolist.todo.repository;

import com.team.todolist.todo.entity.Todo;
import com.team.todolist.todo.entity.TodoStatus;
import com.team.todolist.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    // 유저의 전체 Todo 조회
    List<Todo> findAllByUser(User user);

    // 완료된 Todo 일괄 삭제
    @Modifying
    @Query("DELETE FROM Todo t WHERE t.user = :user AND t.status = :status")
    void deleteAllByUserAndStatus(@Param("user") User user, @Param("status") TodoStatus status);

    // 유저의 Todo 전체 삭제 (회원탈퇴 시 사용)
    @Modifying
    @Query("DELETE FROM Todo t WHERE t.user = :user")
    void deleteAllByUser(@Param("user") User user);
}
