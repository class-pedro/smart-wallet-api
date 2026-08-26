package com.example.smart_wallet.modules.expense.infrastructure.client;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NfcContentFetcherHttpClientTest {

    private final NfcContentFetcherHttpClient client = new NfcContentFetcherHttpClient();

    private HttpServer server;

    @AfterEach
    void stopServer() {
        if (server != null) {
            server.stop(0);
        }
    }

    @Test
    void fetchReturnsTheResponseBody() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        String body = "<html>ok</html>";
        server.createContext("/nfce", exchange -> {
            byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        });
        server.start();

        String result = client.fetch("http://127.0.0.1:" + server.getAddress().getPort() + "/nfce");

        assertThat(result).isEqualTo(body);
    }

    @Test
    void fetchWrapsConnectionFailuresInARuntimeException() throws IOException {
        int closedPort;
        try (ServerSocket socket = new ServerSocket(0)) {
            closedPort = socket.getLocalPort();
        }

        assertThatThrownBy(() -> client.fetch("http://127.0.0.1:" + closedPort + "/nfce"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Erro ao consultar NFC-e");
    }
}
