package com.team.todolist.todo.controller;

import java.util.List;

import com.team.todolist.security.auth.CustomUserDetails;
import com.team.todolist.todo.dto.TodoUpdateDto;
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

    // 전체 일정 조회 (목록 페이지)
    @GetMapping
    public String getTodos(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {
        User user = userDetails.getUser();
        List<TodoResponseDto> todos = todoService.getTodos(user);

        // 완료 개수 계산해서 model에 추가
        long completedCount = todos.stream()
                .filter(t -> t.getStatus().name().equals("COMPLETED"))
                .count();

        model.addAttribute("todos", todos);
        model.addAttribute("completedCount", completedCount);
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
            @ModelAttribute TodoRequestDto requestDto,
            RedirectAttributes redirectAttributes
    ) {
        User user = userDetails.getUser();
        todoService.createTodo(user, requestDto);
        redirectAttributes.addFlashAttribute("successMessage", "할 일이 추가되었습니다!");
        return "redirect:/todos";
    }

    // 수정 페이지
    @GetMapping("/{todoId}/edit")
    public String editTodoForm(@PathVariable Long todoId, Model model) {
        TodoResponseDto todo = todoService.getTodo(todoId);
        model.addAttribute("todo", todo);
        return "todo-form";
    }

    // 할 일 수정
    @PostMapping("/{todoId}/edit")
    public String updateTodo(
            @PathVariable Long todoId,
            @ModelAttribute TodoUpdateDto requestDto,
            RedirectAttributes redirectAttributes
    ) {
        todoService.updateTodo(todoId, requestDto);
        redirectAttributes.addFlashAttribute("successMessage", "할 일이 수정되었습니다!");
        return "redirect:/todos";
    }

    // 체크박스 상태 토글
    @PostMapping("/{todoId}/status")
    public String updateStatus(
            @PathVariable Long todoId,
            @RequestParam String status
    ) {
        TodoUpdateDto dto = new TodoUpdateDto();
        dto.setStatus(com.team.todolist.todo.entity.TodoStatus.valueOf(status));
        todoService.updateTodo(todoId, dto);
        return "redirect:/todos";
    }

    // 할 일 삭제
    @PostMapping("/{todoId}/delete")
    public String deleteTodo(
            @PathVariable Long todoId,
            RedirectAttributes redirectAttributes
    ) {
        todoService.deleteTodo(todoId);
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
