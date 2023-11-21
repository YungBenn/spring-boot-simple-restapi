package com.simple.restapi.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TodoResponse {

    private String id;

    private String title;

    private String description;

}
