package com.joebrooks.mapshotimageapi.global.exception;

import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class RestControllerExceptionAdvice {

    private final SlackClient slackClient;

    @ExceptionHandler(Exception.class)
    public void exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        slackClient.sendMessage(e);
    }
}