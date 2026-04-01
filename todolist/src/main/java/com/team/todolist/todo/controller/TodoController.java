package com.team.todolist.todo.controller;

import java.util.List;

import com.team.todolist.security.auth.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team.todolist.todo.dto.TodoRequestDto;
import com.team.todolist.todo.dto.TodoResponseDto;
import com.team.todolist.todo.dto.TodoUpdateDto;
import com.team.todolist.todo.entity.TodoStatus;
import com.team.todolist.todo.service.TodoService;
import com.team.todolist.user.entity.User;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public String todoList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {
        User user = userDetails.getUser();
        List<TodoResponseDto> todos = todoService.getTodos(user);

        List<TodoResponseDto> activeTodos = todos.stream()
                .filter(todo -> todo.getStatus() != TodoStatus.COMPLETED)
                .toList();

        List<TodoResponseDto> completedTodos = todos.stream()
                .filter(todo -> todo.getStatus() == TodoStatus.COMPLETED)
                .toList();

        model.addAttribute("activeTodos", activeTodos);
        model.addAttribute("completedTodos", completedTodos);
        model.addAttribute("totalCount", todos.size());

        return "todo-list";
    }

    @GetMapping("/new")
    public String newTodoForm() {
        return "todo-form";
    }

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

}