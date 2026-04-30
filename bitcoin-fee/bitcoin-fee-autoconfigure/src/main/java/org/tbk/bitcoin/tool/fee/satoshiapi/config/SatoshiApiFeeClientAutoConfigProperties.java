package org.tbk.bitcoin.tool.fee.satoshiapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.tbk.bitcoin.tool.fee.config.AbstractFeeClientAutoConfigProperties;

@ConfigurationProperties(
        prefix = "org.tbk.bitcoin.tool.fee.satoshiapi",
        ignoreUnknownFields = false
)
public class SatoshiApiFeeClientAutoConfigProperties extends AbstractFeeClientAutoConfigProperties {

    public SatoshiApiFeeClientAutoConfigProperties(boolean enabled, String baseUrl, String token) {
        super(enabled, baseUrl, token);
    }

    @Override
    protected String getDefaultBaseUrl() {
        return "https://bitcoinsapi.com";
    }
}
