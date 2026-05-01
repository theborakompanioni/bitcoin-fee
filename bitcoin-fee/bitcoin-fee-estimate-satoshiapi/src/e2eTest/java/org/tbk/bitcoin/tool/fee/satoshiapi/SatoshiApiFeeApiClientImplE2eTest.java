package org.tbk.bitcoin.tool.fee.satoshiapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tbk.bitcoin.tool.fee.satoshiapi.proto.RecommendedFeesResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class SatoshiApiFeeApiClientImplE2eTest {
    private static final String BASE_URL = "https://bitcoinsapi.com";

    private SatoshiApiFeeApiClientImpl sut;

    @BeforeEach
    void setUp() {
        this.sut = new SatoshiApiFeeApiClientImpl(BASE_URL, null);
    }

    @Test
    void itShouldGetFeesRecommended() {
        RecommendedFeesResponse feesRecommended = this.sut.feesRecommended();

        assertThat(feesRecommended, is(notNullValue()));

        assertThat(feesRecommended.getData().getEstimatesOrThrow("1"), is(greaterThanOrEqualTo(0d)));
    }
}
