package com.kevin.fincore.common.exception;

import jakarta.servlet.http.HttpServletRequest; // Reponsavavel por acessar os dados da requisição, como o caminho da URL e os parâmetros de consulta.

import org.slf4j.Logger; // Reponsavavel por registrar mensagens de log para depuração e monitoramento.
import org.slf4j.LoggerFactory; // Reponsavavel por criar instâncias de Logger para classes específicas.

import org.springframework.http.HttpStatus; // Representa os códigos de status HTTP, como 200 OK, 404 Not Found, 500 Internal Server Error, entre outros.
import org.springframework.http.ResponseEntity; // Representa uma resposta HTTP completa, incluindo o corpo da resposta, os cabeçalhos e o código de status.
import org.springframework.http.converter.HttpMessageNotReadableException; //Identificar problemas na leitura do corpo da requisição
import org.springframework.web.bind.MethodArgumentNotValidException; //Identificar problemas de validação de argumentos em métodos de manipuladores de requisição
import org.springframework.web.bind.annotation.ExceptionHandler; //Associar um método ao tratamento de uma exceção.
import org.springframework.web.bind.annotation.RestControllerAdvice; //	Centralizar o tratamento de exceções dos Controllers

import java.time.LocalDateTime; //Registrar data e hora.
import java.util.LinkedHashMap; // Organizar os dados da resposta em uma estrutura de mapa que mantém a ordem de inserção dos elementos.
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class); // Cria um logger específico permitindo registrar mensagens de log relacionadas ao tratamento de exceções.

    @ExceptionHandler(EmailRegisteredException.class) // Tratamento do email duplicado
    public ResponseEntity<Map<String, Object>> handleEmailAlreadyRegistered(
            EmailRegisteredException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) //Tratamento de erros de validação de argumentos em métodos de manipuladores de requisição
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        Map<String, String> validationErrors = new LinkedHashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error ->
                validationErrors.put(
                        error.getField(),
                        error.getDefaultMessage()
                )
        );

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", "Existem dados inválidos na requisição.");
        body.put("path", request.getRequestURI());
        body.put("validationErrors", validationErrors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class) //Tratamento de erros de leitura do corpo da requisição, como JSON inválido ou ausente
    public ResponseEntity<Map<String, Object>> handleInvalidRequestBody(
            HttpMessageNotReadableException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "O corpo da requisição está ausente ou possui formato inválido.",
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class) //Tratamento de erros inesperados, capturando exceções genéricas que não foram tratadas por outros manipuladores.
    public ResponseEntity<Map<String, Object>> handleUnexpectedError(
            Exception exception,
            HttpServletRequest request
    ) {
        log.error("Erro inesperado ao processar a requisição", exception);

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro interno. Tente novamente mais tarde.",
                request.getRequestURI()
        );
    }
    @ExceptionHandler(InvalidCredentialsException.class)
        public ResponseEntity<Map<String, Object>> handleInvalidCredentials(
                InvalidCredentialsException exception,
                HttpServletRequest request
        ) {
                return buildResponse(
                        HttpStatus.UNAUTHORIZED,
                        exception.getMessage(),
                        request.getRequestURI()
        );
}

    private ResponseEntity<Map<String, Object>> buildResponse(
            HttpStatus status,
            String message,
            String path
    ) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", path);

        return ResponseEntity.status(status).body(body);
    }
}