package com.simple.restapi.payload;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UpdateTodoRequest {

    @Size(max = 100)
    private String title;

    @Size(max = 100)
    private String description;

}
