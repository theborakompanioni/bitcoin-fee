package org.tbk.bitcoin.tool.fee.satoshiapi.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.tbk.bitcoin.tool.fee.satoshiapi.SatoshiApiFeeApiClient;
import org.tbk.bitcoin.tool.fee.satoshiapi.SatoshiApiFeeApiClientImpl;
import org.tbk.bitcoin.tool.fee.satoshiapi.SatoshiApiFeeProvider;

import static java.util.Objects.requireNonNull;

@AutoConfiguration
@EnableConfigurationProperties(SatoshiApiFeeClientAutoConfigProperties.class)
@ConditionalOnClass({
        SatoshiApiFeeApiClient.class,
        SatoshiApiFeeProvider.class
})
@ConditionalOnProperty(name = {
        "org.tbk.bitcoin.tool.fee.enabled",
        "org.tbk.bitcoin.tool.fee.satoshiapi.enabled"
}, havingValue = "true", matchIfMissing = true)
public class SatoshiApiFeeClientAutoConfiguration {

    private final SatoshiApiFeeClientAutoConfigProperties properties;

    public SatoshiApiFeeClientAutoConfiguration(SatoshiApiFeeClientAutoConfigProperties properties) {
        this.properties = requireNonNull(properties);
    }

    @Bean
    @ConditionalOnMissingBean(SatoshiApiFeeApiClient.class)
    SatoshiApiFeeApiClient satoshiApiFeeApiClient() {
        return new SatoshiApiFeeApiClientImpl(properties.getBaseUrl(), properties.getToken().orElse(null));
    }

    @Bean
    @ConditionalOnMissingBean(SatoshiApiFeeProvider.class)
    SatoshiApiFeeProvider satoshiApiFeeProvider(SatoshiApiFeeApiClient satoshiApiFeeApiClient) {
        return new SatoshiApiFeeProvider(satoshiApiFeeApiClient);
    }
}
