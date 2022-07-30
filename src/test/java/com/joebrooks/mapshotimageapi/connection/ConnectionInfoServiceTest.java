package com.joebrooks.mapshotimageapi.connection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketExtension;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConnectionInfoServiceTest {


    @Autowired
    private ConnectionInfoService connectionInfoService;



    @Test
    public void add_테스트(){
        connectionInfoService.add(ConnectionInfo.builder()
                        .webSocketSession(new TestSession())
                .build());
    }


    @Test
    public void add_여러개_테스트(){ //fixme 카페가서 작성
        connectionInfoService.add(ConnectionInfo.builder()
                .webSocketSession(new TestSession())
                .build());
    }



    class TestSession implements WebSocketSession {

        @Override
        public String getId() {
            return null;
        }

        @Override
        public URI getUri() {
            return null;
        }

        @Override
        public HttpHeaders getHandshakeHeaders() {
            return null;
        }

        @Override
        public Map<String, Object> getAttributes() {
            return null;
        }

        @Override
        public Principal getPrincipal() {
            return null;
        }

        @Override
        public InetSocketAddress getLocalAddress() {
            return null;
        }

        @Override
        public InetSocketAddress getRemoteAddress() {
            return null;
        }

        @Override
        public String getAcceptedProtocol() {
            return null;
        }

        @Override
        public void setTextMessageSizeLimit(int messageSizeLimit) {

        }

        @Override
        public int getTextMessageSizeLimit() {
            return 0;
        }

        @Override
        public void setBinaryMessageSizeLimit(int messageSizeLimit) {

        }

        @Override
        public int getBinaryMessageSizeLimit() {
            return 0;
        }

        @Override
        public List<WebSocketExtension> getExtensions() {
            return null;
        }

        @Override
        public void sendMessage(WebSocketMessage<?> message) throws IOException {

        }

        @Override
        public boolean isOpen() {
            return false;
        }

        @Override
        public void close() throws IOException {

        }

        @Override
        public void close(CloseStatus status) throws IOException {

        }
    }
}