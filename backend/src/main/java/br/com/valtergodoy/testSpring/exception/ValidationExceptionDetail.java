package br.com.valtergodoy.testSpring.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExceptionDetail extends ExceptionDetail {
    private final String fieldMessage;
}
