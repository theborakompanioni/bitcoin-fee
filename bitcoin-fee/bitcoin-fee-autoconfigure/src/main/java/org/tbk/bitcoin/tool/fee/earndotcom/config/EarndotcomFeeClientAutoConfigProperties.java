package org.tbk.bitcoin.tool.fee.earndotcom.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.tbk.bitcoin.tool.fee.config.AbstractFeeClientAutoConfigProperties;

@ConfigurationProperties(
        prefix = "org.tbk.bitcoin.tool.fee.earndotcom",
        ignoreUnknownFields = false
)
public class EarndotcomFeeClientAutoConfigProperties extends AbstractFeeClientAutoConfigProperties {

    public EarndotcomFeeClientAutoConfigProperties(boolean enabled, String baseUrl, String token) {
        super(enabled, baseUrl, token);
    }

    @Override
    protected String getDefaultBaseUrl() {
        return "https://bitcoinfees.earn.com";
    }
}
