package org.tbk.bitcoin.tool.fee.mempoolspace;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tbk.bitcoin.tool.fee.mempoolspace.proto.FeesRecommended;
import org.tbk.bitcoin.tool.fee.mempoolspace.proto.ProjectedMempoolBlocks;

import java.security.SecureRandom;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Slf4j
class MempoolspaceFeeApiClientImplTest {
    private static final SecureRandom random = new SecureRandom();

    private static final List<String> baseUrls = ImmutableList.<String>builder()
            .add("https://mempool.space")
            // following alternative endpoints can be used in case mempool.space is down:
            // .add("https://mempool.emzy.de")
            // .add("https://mempool.bisq.services")
            // .add("https://mempool.ninja")
            .build();

    private static final String API_TOKEN = null;

    private MempoolspaceFeeApiClientImpl sut;

    @BeforeEach
    void setUp() {
        String url = baseUrls.get(((int) Math.ceil(random.nextDouble() * baseUrls.size())) - 1);

        log.debug("Using service {} for current test", url);

        this.sut = new MempoolspaceFeeApiClientImpl(url, API_TOKEN);
    }

    @Test
    void itShouldGetFeesRecommended() {
        FeesRecommended feesRecommended = this.sut.feesRecommended();

        assertThat(feesRecommended, is(notNullValue()));
        assertThat(feesRecommended.getFastestFee(), is(greaterThanOrEqualTo(0L)));
        assertThat(feesRecommended.getHalfHourFee(), is(greaterThanOrEqualTo(0L)));
        assertThat(feesRecommended.getHourFee(), is(greaterThanOrEqualTo(0L)));
    }

    @Test
    void itShouldGetProjectedBlocks() {
        ProjectedMempoolBlocks projectedBlocks = this.sut.projectedBlocks();

        assertThat(projectedBlocks, is(notNullValue()));
        assertThat(projectedBlocks.getBlocksList(), hasSize(greaterThanOrEqualTo(0)));
    }
}
