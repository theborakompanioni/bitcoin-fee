package org.tbk.bitcoin.tool.fee.satoshiapi;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.tbk.bitcoin.tool.fee.satoshiapi.proto.RecommendedFeesResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SatoshiApiFeeApiClientImplTest {
    private HttpServer server;

    @AfterEach
    void tearDown() {
        if (server != null) {
            server.stop(0);
        }
    }

    @Test
    void itShouldFetchRecommendedFeesFromSatoshiApiShape() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(0), 0);
        this.server.createContext("/api/v1/fees/recommended", exchange -> {
            byte[] response = """
                    {
                      "data": {
                        "recommendation": "Fees are low.",
                        "estimates": {
                          "1": 12.5,
                          "3": 7.25,
                          "6": 4.0,
                          "144": 1.0
                        }
                      },
                      "meta": {
                        "chain": "main",
                        "cached": true
                      }
                    }
                    """.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, response.length);
            try (OutputStream body = exchange.getResponseBody()) {
                body.write(response);
            }
        });
        this.server.start();

        String baseUrl = "http://localhost:" + this.server.getAddress().getPort();

        RecommendedFeesResponse response = new SatoshiApiFeeApiClientImpl(baseUrl, null).feesRecommended();

        assertThat(response.getData().getRecommendation(), is("Fees are low."));
        assertThat(response.getData().getEstimatesOrThrow("1"), is(closeTo(12.5d, 0.0001d)));
        assertThat(response.getData().getEstimatesOrThrow("3"), is(closeTo(7.25d, 0.0001d)));
        assertThat(response.getData().getEstimatesOrThrow("6"), is(closeTo(4.0d, 0.0001d)));
    }

    @Test
    void itShouldCloseHttpClientOnDestroy() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(0), 0);
        this.server.createContext("/api/v1/fees/recommended", exchange -> {
            byte[] response = """
                    {
                      "data": {
                        "recommendation": "Fees are low.",
                        "estimates": {
                          "1": 12.5
                        }
                      }
                    }
                    """.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, response.length);
            try (OutputStream body = exchange.getResponseBody()) {
                body.write(response);
            }
        });
        this.server.start();

        String baseUrl = "http://localhost:" + this.server.getAddress().getPort();

        SatoshiApiFeeApiClientImpl client = new SatoshiApiFeeApiClientImpl(baseUrl, null);
        client.destroy();

        assertThrows(RuntimeException.class, client::feesRecommended);
    }
}
