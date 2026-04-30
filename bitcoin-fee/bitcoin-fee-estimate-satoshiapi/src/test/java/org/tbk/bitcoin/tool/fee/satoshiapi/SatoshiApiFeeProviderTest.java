package org.tbk.bitcoin.tool.fee.satoshiapi;

import org.junit.jupiter.api.Test;
import org.tbk.bitcoin.tool.fee.FeeRecommendationRequestImpl;
import org.tbk.bitcoin.tool.fee.FeeRecommendationResponse;
import org.tbk.bitcoin.tool.fee.satoshiapi.proto.RecommendedFeesResponse;

import java.math.BigDecimal;
import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class SatoshiApiFeeProviderTest {

    @Test
    void itShouldMapDurationTargetsToSatoshiApiEstimates() {
        SatoshiApiFeeApiClient client = () -> RecommendedFeesResponse.newBuilder()
                .setData(RecommendedFeesResponse.Data.newBuilder()
                        .putEstimates("1", 12.5d)
                        .putEstimates("3", 7.25d)
                        .putEstimates("6", 4.0d)
                        .putEstimates("144", 1.0d)
                        .build())
                .build();

        SatoshiApiFeeProvider sut = new SatoshiApiFeeProvider(client);

        assertFee(sut, Duration.ZERO, "12.5");
        assertFee(sut, Duration.ofMinutes(30), "7.25");
        assertFee(sut, Duration.ofHours(1), "4.0");
    }

    @Test
    void itShouldNotSupportConfidenceBasedRequests() {
        SatoshiApiFeeProvider sut = new SatoshiApiFeeProvider(() -> RecommendedFeesResponse.getDefaultInstance());

        boolean supports = sut.supports(FeeRecommendationRequestImpl.builder()
                .durationTarget(Duration.ofMinutes(30))
                .desiredConfidence(FeeRecommendationRequestImpl.ConfidenceImpl.builder()
                        .confidenceValue(0.8d)
                        .build())
                .build());

        assertThat(supports, is(false));
    }

    private static void assertFee(SatoshiApiFeeProvider sut, Duration durationTarget, String expected) {
        FeeRecommendationResponse response = sut.request(FeeRecommendationRequestImpl.builder()
                        .durationTarget(durationTarget)
                        .build())
                .blockFirst();

        assertThat(response, is(notNullValue()));
        assertThat(response.getFeeRecommendations().get(0).getFeeUnit().getValue(),
                comparesEqualTo(new BigDecimal(expected)));
    }
}
