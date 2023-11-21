package com.simple.restapi.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simple.restapi.exception.ResourceNotFoundException;
import com.simple.restapi.model.Todo;
import com.simple.restapi.payload.CreateTodoRequest;
import com.simple.restapi.payload.TodoResponse;
import com.simple.restapi.payload.UpdateTodoRequest;
import com.simple.restapi.repository.TodoRepository;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void addTodo(CreateTodoRequest request) {
        validationService.validate(request);

        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());

        todoRepository.save(todo);
    }

    public List<TodoResponse> getTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream()
            .map(todo -> TodoResponse.builder()
                .id(todo.getId().toString())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .build())
            .collect(Collectors.toList());
    }

    public TodoResponse getTodoById(UUID id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo", "id", id));

        return TodoResponse.builder()
                .id(todo.getId().toString())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .build();
    }

    @Transactional
    public TodoResponse updateTodoByid(UUID id, UpdateTodoRequest request) {
        validationService.validate(request);

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo", "id", id));

        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());

        todoRepository.save(todo);

        return TodoResponse.builder()
                .id(todo.getId().toString())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .build();
    }

    public void deleteTodoById(UUID id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo", "id", id));

        todoRepository.delete(todo);
    }
}
