package com.joao.hexagonal_architecture_introduction_project.adapters.in.controller.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardError {
    private Long timestamp;
    private Integer status;
    private String message;
    private String path;
}
