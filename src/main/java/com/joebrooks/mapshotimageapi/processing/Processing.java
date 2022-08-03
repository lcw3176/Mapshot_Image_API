package com.joebrooks.mapshotimageapi.processing;

import com.joebrooks.mapshotimageapi.user.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Processing {
    private UserRequest request;
    private WebSocketSession session;

}
