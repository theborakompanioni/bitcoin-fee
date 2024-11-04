package org.tbk.bitcoin.tool.fee.blockcypher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tbk.bitcoin.tool.fee.blockcypher.proto.ChainInfo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class BlockcypherFeeApiClientImplTest {
    private static final String BASE_URL = "https://api.blockcypher.com";
    private static final String API_TOKEN = null;

    private BlockcypherFeeApiClientImpl sut;

    @BeforeEach
    void setUp() {
        this.sut = new BlockcypherFeeApiClientImpl(BASE_URL, API_TOKEN);
    }

    @Test
    void itShouldGetBtcMainnet() {
        ChainInfo chainInfo = this.sut.btcMain();

        assertThat(chainInfo, is(notNullValue()));
        assertThat(chainInfo.getName(), is("BTC.main"));
        assertThat(chainInfo.getHeight(), is(greaterThanOrEqualTo(0L)));
        assertThat(chainInfo.getHash(), is(notNullValue()));
        assertThat(chainInfo.getTime(), is(notNullValue()));
        assertThat(chainInfo.getLatestUrl(), startsWith("https://api.blockcypher.com/v1/btc/main/"));
        assertThat(chainInfo.getPreviousHash(), is(notNullValue()));
        assertThat(chainInfo.getPreviousUrl(), startsWith("https://api.blockcypher.com/v1/btc/main/"));
        assertThat(chainInfo.getUnconfirmedCount(), is(greaterThanOrEqualTo(0L)));
        assertThat(chainInfo.getHighFeePerKb(), is(greaterThanOrEqualTo(0L)));
        assertThat(chainInfo.getLowFeePerKb(), is(greaterThanOrEqualTo(0L)));
        assertThat(chainInfo.getLastForkHeight(), is(greaterThanOrEqualTo(0L)));
        assertThat(chainInfo.getLastForkHash(), is(notNullValue()));
    }

    @Test
    void itShouldGetBtcTestnet() {
        ChainInfo chainInfo = this.sut.btcTestnet3();

        assertThat(chainInfo, is(notNullValue()));
        assertThat(chainInfo.getName(), is("BTC.test3"));
        assertThat(chainInfo.getHeight(), is(greaterThanOrEqualTo(0L)));
        assertThat(chainInfo.getHash(), is(notNullValue()));
        assertThat(chainInfo.getTime(), is(notNullValue()));
        assertThat(chainInfo.getLatestUrl(), startsWith("https://api.blockcypher.com/v1/btc/test3/"));
        assertThat(chainInfo.getPreviousHash(), is(notNullValue()));
        assertThat(chainInfo.getPreviousUrl(), startsWith("https://api.blockcypher.com/v1/btc/test3/"));
        assertThat(chainInfo.getUnconfirmedCount(), is(greaterThanOrEqualTo(0L)));
        assertThat(chainInfo.getHighFeePerKb(), is(greaterThanOrEqualTo(0L)));
        assertThat(chainInfo.getLowFeePerKb(), is(greaterThanOrEqualTo(0L)));
        assertThat(chainInfo.getLastForkHeight(), is(greaterThanOrEqualTo(0L)));
        assertThat(chainInfo.getLastForkHash(), is(notNullValue()));
    }
}
