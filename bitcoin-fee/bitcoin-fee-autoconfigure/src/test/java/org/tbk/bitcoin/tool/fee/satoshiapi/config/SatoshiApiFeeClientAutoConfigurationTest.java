package org.tbk.bitcoin.tool.fee.satoshiapi.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.tbk.bitcoin.tool.fee.satoshiapi.SatoshiApiFeeApiClient;
import org.tbk.bitcoin.tool.fee.satoshiapi.SatoshiApiFeeProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SatoshiApiFeeClientAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

    @Test
    void beansAreCreated() {
        this.contextRunner.withUserConfiguration(SatoshiApiFeeClientAutoConfiguration.class)
                .withPropertyValues(
                        "org.tbk.bitcoin.tool.fee.enabled=true",
                        "org.tbk.bitcoin.tool.fee.satoshiapi.enabled=true"
                )
                .run(context -> {
                    assertThat(context.getBean(SatoshiApiFeeApiClient.class), is(notNullValue()));
                    assertThat(context.getBean(SatoshiApiFeeProvider.class), is(notNullValue()));
                });
    }

    @Test
    void noBeansAreCreated() {
        this.contextRunner.withUserConfiguration(SatoshiApiFeeClientAutoConfiguration.class)
                .withPropertyValues(
                        "org.tbk.bitcoin.tool.fee.enabled=true",
                        "org.tbk.bitcoin.tool.fee.satoshiapi.enabled=false"
                )
                .run(context -> {
                    assertThrows(NoSuchBeanDefinitionException.class,
                            () -> context.getBean(SatoshiApiFeeApiClient.class));
                    assertThrows(NoSuchBeanDefinitionException.class,
                            () -> context.getBean(SatoshiApiFeeProvider.class));
                });
    }
}
