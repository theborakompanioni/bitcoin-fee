package org.tbk.bitcoin.tool.fee.satoshiapi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tbk.bitcoin.tool.fee.satoshiapi.proto.RecommendedFeesResponse;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class SatoshiApiFeeApiClientImplE2eTest {
    private static final String BASE_URL = "https://bitcoinsapi.com";

    private SatoshiApiFeeApiClientImpl sut;

    @BeforeEach
    void beforeEach() {
        this.sut = new SatoshiApiFeeApiClientImpl(BASE_URL, null);
    }

    @AfterEach
    void afterEach() throws IOException {
        this.sut.close();
    }

    @Test
    void itShouldGetFeesRecommended() {
        RecommendedFeesResponse feesRecommended = this.sut.feesRecommended();

        assertThat(feesRecommended, is(notNullValue()));
        assertThat(feesRecommended.getData().getEstimatesOrThrow("1"), is(greaterThanOrEqualTo(0d)));
    }
}
