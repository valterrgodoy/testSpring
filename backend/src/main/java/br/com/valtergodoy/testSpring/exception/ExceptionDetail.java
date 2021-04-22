package br.com.valtergodoy.testSpring.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetail {
    private LocalDateTime timestamp;
    private String title;
    private int status;
    private String devMessage;
    private String message;
}
