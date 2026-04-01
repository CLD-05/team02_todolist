package com.team.todolist.todo.controller;

import java.util.List;

import com.team.todolist.security.auth.CustomUserDetails;
import com.team.todolist.todo.dto.TodoUpdateDto;
import com.team.todolist.todo.entity.TodoStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team.todolist.todo.dto.TodoRequestDto;
import com.team.todolist.todo.dto.TodoResponseDto;
import com.team.todolist.todo.service.TodoService;
import com.team.todolist.user.entity.User;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;


    // 전체 일정 조회
    @GetMapping
    public String todoList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {
        User user = userDetails.getUser();
        List<TodoResponseDto> todos = todoService.getTodos(user);

        // 🔥 진행중 / 완료 분리
        List<TodoResponseDto> activeTodos = todos.stream()
                .filter(todo -> todo.getStatus() != TodoStatus.COMPLETED)
                .toList();

        List<TodoResponseDto> completedTodos = todos.stream()
                .filter(todo -> todo.getStatus() == TodoStatus.COMPLETED)
                .toList();


        // 진행률 계산 (0으로 나누기 방지)
        int total = todos.size();
        int completed = completedTodos.size();
        int progress = total == 0 ? 0
                : completed == total ? 100
                : (int) Math.round((double) completed / total * 100);

        model.addAttribute("activeTodos", activeTodos);
        model.addAttribute("completedTodos", completedTodos);
        model.addAttribute("totalCount", total);
        model.addAttribute("completedCount", completed);
        model.addAttribute("progress", progress);
        model.addAttribute("nickname", user.getNickname());
        return "todo-list";
    }

    // 새 할 일 작성 페이지
    @GetMapping("/new")
    public String newTodoForm() {
        return "todo-form";
    }

    // 할 일 생성
    @PostMapping
    public String createTodo(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam String content,
            RedirectAttributes redirectAttributes
    ) {
        User user = userDetails.getUser();
        TodoRequestDto requestDto = new TodoRequestDto(content);
        todoService.createTodo(user, requestDto);

        redirectAttributes.addFlashAttribute("successMessage", "할 일이 추가되었습니다!");
        return "redirect:/todos";
    }

    // 수정 페이지
    @GetMapping("/{id}/edit")
    public String editTodoForm(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {
        User user = userDetails.getUser();
        TodoResponseDto todo = todoService.getTodo(id, user);

        model.addAttribute("todo", todo);
        return "todo-form";
    }

    // 할 일 수정
    @PostMapping("/{id}/edit")
    public String updateTodo(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam String content,
            @RequestParam TodoStatus status,
            RedirectAttributes redirectAttributes
    ) {
        User user = userDetails.getUser();
        TodoUpdateDto requestDto = new TodoUpdateDto(content, status);

        todoService.updateTodo(id, user, requestDto);

        redirectAttributes.addFlashAttribute("successMessage", "할 일이 수정되었습니다!");
        return "redirect:/todos";
    }

    // 체크박스 상태 토글
    @PostMapping("/{id}/status")
    public String updateStatus(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam TodoStatus status
    ) {
        User user = userDetails.getUser();
        TodoUpdateDto requestDto = new TodoUpdateDto(null, status);

        todoService.updateTodo(id, user, requestDto);

        return "redirect:/todos";
    }

    // 할 일 삭제
    @PostMapping("/{id}/delete")
    public String deleteTodo(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            RedirectAttributes redirectAttributes
    ) {
        User user = userDetails.getUser();
        todoService.deleteTodo(id, user);

        redirectAttributes.addFlashAttribute("successMessage", "할 일이 삭제되었습니다!");
        return "redirect:/todos";
    }

    // 완료된 할 일 일괄 삭제
    @PostMapping("/delete-completed")
    public String deleteCompleted(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            RedirectAttributes redirectAttributes
    ) {
        User user = userDetails.getUser();
        int count = todoService.deleteCompletedTodos(user);
        if (count > 0) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "완료된 할 일 " + count + "개가 삭제되었습니다!");
        } else {
            redirectAttributes.addFlashAttribute("successMessage",
                    "삭제할 완료 항목이 없습니다.");
        }
        return "redirect:/todos";
    }
}
