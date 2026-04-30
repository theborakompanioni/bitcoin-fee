package org.tbk.bitcoin.tool.fee.satoshiapi;

import lombok.extern.slf4j.Slf4j;
import org.tbk.bitcoin.tool.fee.AbstractFeeProvider;
import org.tbk.bitcoin.tool.fee.FeeRecommendationRequest;
import org.tbk.bitcoin.tool.fee.FeeRecommendationResponse;
import org.tbk.bitcoin.tool.fee.FeeRecommendationResponseImpl;
import org.tbk.bitcoin.tool.fee.ProviderInfo;
import org.tbk.bitcoin.tool.fee.satoshiapi.proto.RecommendedFeesResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Slf4j
public class SatoshiApiFeeProvider extends AbstractFeeProvider {

    private static final ProviderInfo providerInfo = ProviderInfo.SimpleProviderInfo.builder()
            .name("Satoshi API")
            .description("Fee recommendation from Satoshi API's hosted Bitcoin Core-derived estimator.")
            .build();

    private final SatoshiApiFeeApiClient client;

    public SatoshiApiFeeProvider(SatoshiApiFeeApiClient client) {
        super(providerInfo);

        this.client = requireNonNull(client);
    }

    @Override
    public boolean supports(FeeRecommendationRequest request) {
        return request.getDesiredConfidence().isEmpty();
    }

    @Override
    protected Flux<FeeRecommendationResponse> requestHook(FeeRecommendationRequest request) {
        return Mono.fromCallable(() -> requestHookInternal(request)).flux();
    }

    @Nullable
    private FeeRecommendationResponse requestHookInternal(FeeRecommendationRequest request) {
        RecommendedFeesResponse feesRecommended = this.client.feesRecommended();

        Map<String, Double> estimates = feesRecommended.getData().getEstimatesMap();

        Optional<Map.Entry<String, Double>> estimateOrEmpty = estimates
                .entrySet()
                .stream()
                .filter(val -> Long.parseLong(val.getKey(), 10) <= request.getBlockTarget())
                .max(Comparator.comparingLong(val -> Long.parseLong(val.getKey(), 10)));

        if (estimateOrEmpty.isEmpty()) {
            log.warn("No suitable estimation entries present in response for request.");
            return null;
        }

        BigDecimal satPerVbyteValue = BigDecimal.valueOf(estimateOrEmpty.get().getValue());

        return FeeRecommendationResponseImpl.builder()
                .addFeeRecommendation(FeeRecommendationResponseImpl.FeeRecommendationImpl.builder()
                        .feeUnit(FeeRecommendationResponseImpl.SatPerVbyteImpl.builder()
                                .satPerVbyteValue(satPerVbyteValue)
                                .build())
                        .build())
                .build();
    }
}
