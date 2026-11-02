package io.github.Maike2961.hexagonal.adapters.in.controller.handler;


public record StandardError(
        Long timestamp,
        Integer status,
        String message,
        String path
) {
}
