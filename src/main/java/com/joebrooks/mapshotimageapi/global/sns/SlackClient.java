package com.joebrooks.mapshotimageapi.global.sns;

import com.joebrooks.mapshotimageapi.global.exception.ExceptionResponse;
import org.springframework.stereotype.Component;

@Component
public class SlackClient extends SnsClient {

    private final String slackUrl = System.getenv("SLACK_URL");

    public void sendMessage(ExceptionResponse errors) {
        String message = SlackMessageFormatter.makeErrorMessage(errors);
        getClient(slackUrl).post();
        post(slackUrl, message);
    }

    public void sendMessage(Exception e){
        ExceptionResponse errorMessage = ExceptionResponse.builder()
                .name(e.getClass().getName())
                .message(makeTransmissible(e))
                .build();

        sendMessage(errorMessage);
    }

    public void sendMessage(String name, Exception exception) {
        ExceptionResponse errorMessage = ExceptionResponse.builder()
                .name(name)
                .message(makeTransmissible(exception))
                .build();

        sendMessage(errorMessage);
    }

    private String makeTransmissible(Exception e){
//        String stackTrace = Arrays.toString(e.getStackTrace());
//        int len = Math.min(stackTrace.length(), 1700);
//
//        return stackTrace.substring(0, len);

        return e.getMessage();
    }

}