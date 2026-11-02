package io.github.Maike2961.hexagonal.adapters.in.controller.handler;

import java.util.List;

public record ErroCampo(
        String message,
        List<String> campo
) {
}
