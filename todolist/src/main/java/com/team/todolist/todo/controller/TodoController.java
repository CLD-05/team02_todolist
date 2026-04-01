package com.team.todolist.todo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.team.todolist.common.response.ApiResponse;
import com.team.todolist.todo.dto.TodoRequestDto;
import com.team.todolist.todo.dto.TodoResponseDto;
import com.team.todolist.todo.dto.TodoUpdateDto;
<<<<<<< Updated upstream
=======
import com.team.todolist.todo.entity.Category;
import com.team.todolist.todo.entity.TodoStatus;
>>>>>>> Stashed changes
import com.team.todolist.todo.service.TodoService;
import com.team.todolist.user.entity.User;
import com.team.todolist.user.repository.UserRepository; // 추가됨

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
<<<<<<< Updated upstream
    private final UserRepository userRepository; // 테스트를 위해 유저 저장소 주입

    // 일정 생성
    @PostMapping
    public ApiResponse<TodoResponseDto> createTodo(
            @RequestBody TodoRequestDto requestDto
    ) {

        User user = userRepository.findById(7L)
                .orElseThrow(() -> new IllegalArgumentException("테스트용 유저(ID: 1)가 없습니다. 먼저 가입해주세요."));

        TodoResponseDto response = todoService.createTodo(user, requestDto);
        return ApiResponse.success("일정 생성 성공", response);
=======
 
    // ── 목록 페이지: todo-list.html ──────────────────────────────
    @GetMapping
    public String todoList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {
        User user = userDetails.getUser();
        List<TodoResponseDto> todos = todoService.getTodos(user);
        model.addAttribute("todos", todos);
        return "todo-list";
    }
 
    // ── 추가 폼 페이지: todo-form.html ────────────────────────────
    @GetMapping("/new")
    public String newTodoForm(Model model) {
        List<Category> categories = todoService.getAllCategories(); 
        model.addAttribute("categories", categories);
        return "todo-form";
    }
 
    // ── 추가 처리 ─────────────────────────────────────────────────
    @PostMapping
    public String createTodo(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam String content,
            @RequestParam Long categoryId,
            RedirectAttributes redirectAttributes
    ) {
        User user = userDetails.getUser();
        TodoRequestDto requestDto = new TodoRequestDto(content, categoryId);
        todoService.createTodo(user, requestDto);
        redirectAttributes.addFlashAttribute("successMessage", "할 일이 추가되었습니다!");
        return "redirect:/todos";
>>>>>>> Stashed changes
    }

    // 전체 일정 조회
    @GetMapping
    public ApiResponse<List<TodoResponseDto>> getTodos() {
        // 조회 시에도 1번 유저의 목록만 가져오도록 임시 설정
        User user = userRepository.findById(7L).orElse(null);

        List<TodoResponseDto> response = todoService.getTodos(user);
        return ApiResponse.success("일정 목록 조회 성공", response);
    }

    // 단건 일정 조회
    @GetMapping("/{todoId}")
    public ApiResponse<TodoResponseDto> getTodo(@PathVariable Long todoId) {
        TodoResponseDto response = todoService.getTodo(todoId);
        return ApiResponse.success("일정 단건 조회 성공", response);
    }

    // 일정 수정
    @PatchMapping("/{todoId}")
    public ApiResponse<TodoResponseDto> updateTodo(
            @PathVariable Long todoId,
            @RequestBody TodoUpdateDto requestDto
    ) {
        TodoResponseDto response = todoService.updateTodo(todoId, requestDto);
        return ApiResponse.success("일정 수정 성공", response);
    }

    // 일정 삭제
    @DeleteMapping("/{todoId}")
    public ApiResponse<Void> deleteTodo(@PathVariable Long todoId) {
        todoService.deleteTodo(todoId);
        return ApiResponse.success("일정 삭제 성공", null);
    }
}