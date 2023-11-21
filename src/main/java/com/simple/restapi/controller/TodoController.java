package com.simple.restapi.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple.restapi.model.Todo;
import com.simple.restapi.model.web.WebResponse;
import com.simple.restapi.payload.CreateTodoRequest;
import com.simple.restapi.payload.TodoResponse;
import com.simple.restapi.payload.UpdateTodoRequest;
import com.simple.restapi.service.TodoService;

@RequestMapping("/api")
@RestController
public class TodoController {

	@Autowired
	private TodoService todoService;

	@GetMapping(path = "/health-check", produces = MediaType.APPLICATION_JSON_VALUE)
	public WebResponse<String> healthCheck() {
		return WebResponse.<String>builder()
				.code(HttpStatus.OK.value())
				.status(HttpStatus.OK.name())
				.build();
	}

	@PostMapping(path = "/todos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public WebResponse<String> addTodo(@RequestBody CreateTodoRequest request) {
		todoService.addTodo(request);
		return WebResponse.<String>builder()
				.code(HttpStatus.CREATED.value())
				.status(HttpStatus.CREATED.name())
				.data("OK!")
				.build();
	}

	@GetMapping(path = "/todos", produces = MediaType.APPLICATION_JSON_VALUE)
	public WebResponse<List<TodoResponse>> getTodos() {
		List<TodoResponse> todoResponse = todoService.getTodos();
		return WebResponse.<List<TodoResponse>>builder()
				.code(HttpStatus.OK.value())
				.status(HttpStatus.OK.name())
				.data(todoResponse)
				.build();
	}

	@GetMapping(path = "/todos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public WebResponse<TodoResponse> getTodo(@PathVariable(value = "id") UUID id) {
		TodoResponse todoResponse = todoService.getTodoById(id);
		return WebResponse.<TodoResponse>builder()
				.code(HttpStatus.OK.value())
				.status(HttpStatus.OK.name())
				.data(todoResponse)
				.build();
	}

	@PutMapping(path = "/todos/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public WebResponse<TodoResponse> updateTodo(@PathVariable(value = "id") UUID id, @RequestBody UpdateTodoRequest request) {
		TodoResponse todoResponse = todoService.updateTodoByid(id, request);
		return WebResponse.<TodoResponse>builder()
				.code(HttpStatus.OK.value())
				.status(HttpStatus.OK.name())
				.data(todoResponse)
				.build();
	}

	@DeleteMapping(path = "/todos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public WebResponse<String> deleteTodo(@PathVariable(value = "id") UUID id) {
		todoService.deleteTodoById(id);
		return WebResponse.<String>builder()
				.code(HttpStatus.OK.value())
				.status(HttpStatus.OK.name())
				.data("OK!")
				.build();
	}
}
