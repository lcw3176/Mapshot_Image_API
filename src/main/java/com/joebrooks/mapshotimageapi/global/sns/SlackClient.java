package com.joebrooks.mapshotimageapi.global.sns;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SlackClient extends SnsClient {

    private final String slackUrl = System.getenv("SLACK_URL");

    public void sendMessage(MessageResponse errors) {
        String message = SlackMessageFormatter.makeErrorMessage(errors);
        post(slackUrl, message);
    }

    public void sendMessage(Exception e){
        MessageResponse errorMessage = MessageResponse.builder()
                .title(e.getClass().getName())
                .message(makeTransmissible(e))
                .build();

        sendMessage(errorMessage);
    }

    private String makeTransmissible(Exception e){
        String stackTrace = Arrays.toString(e.getStackTrace());
        int len = Math.min(stackTrace.length(), 1700);

        return stackTrace.substring(0, len);
    }

}