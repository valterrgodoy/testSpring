package br.com.valtergodoy.testSpring.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BadRequestExceptionDetail extends ExceptionDetail {
    private String className;
}
