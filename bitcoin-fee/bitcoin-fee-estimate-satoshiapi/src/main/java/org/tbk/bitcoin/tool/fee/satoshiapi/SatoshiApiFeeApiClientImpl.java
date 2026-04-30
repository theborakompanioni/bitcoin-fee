package org.tbk.bitcoin.tool.fee.satoshiapi;

import com.google.common.net.HttpHeaders;
import lombok.SneakyThrows;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.tbk.bitcoin.tool.fee.satoshiapi.proto.RecommendedFeesResponse;
import org.tbk.bitcoin.tool.fee.util.MoreHttpClient;
import org.tbk.bitcoin.tool.fee.util.MoreJsonFormat;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class SatoshiApiFeeApiClientImpl implements SatoshiApiFeeApiClient {
    private static final String DEFAULT_VERSION = Optional.ofNullable(SatoshiApiFeeApiClientImpl.class
            .getPackage()
            .getImplementationVersion()
    ).orElse("0.0.0");

    private static final String DEFAULT_USERAGENT = "tbk-satoshiapi-client/" + DEFAULT_VERSION;
    private static final String API_KEY_HEADER_NAME = "X-API-Key";

    private final CloseableHttpClient client = HttpClients.createDefault();

    private final String baseUrl;
    private final String apiToken;

    public SatoshiApiFeeApiClientImpl(String baseUrl, String apiToken) {
        this.baseUrl = requireNonNull(baseUrl);
        this.apiToken = apiToken;
    }

    @Override
    @SneakyThrows(URISyntaxException.class)
    public RecommendedFeesResponse feesRecommended() {
        // https://bitcoinsapi.com/api/v1/fees/recommended
        URI url = new URIBuilder(baseUrl)
                .setPath("api/v1/fees/recommended")
                .build();

        HttpGet request = new HttpGet(url);
        request.addHeader(HttpHeaders.USER_AGENT, DEFAULT_USERAGENT);
        getApiToken().ifPresent(val -> request.addHeader(API_KEY_HEADER_NAME, val));

        String json = MoreHttpClient.executeToJson(client, request);
        return MoreJsonFormat.jsonToProto(json, RecommendedFeesResponse.newBuilder()).build();
    }

    private Optional<String> getApiToken() {
        return Optional.ofNullable(this.apiToken);
    }
}
