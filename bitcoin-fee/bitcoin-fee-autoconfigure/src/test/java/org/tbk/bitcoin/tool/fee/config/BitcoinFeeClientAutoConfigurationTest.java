package org.tbk.bitcoin.tool.fee.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.tbk.bitcoin.tool.fee.CompositeFeeProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BitcoinFeeClientAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

    @Test
    void beansAreCreated() {
        this.contextRunner.withUserConfiguration(BitcoinFeeClientAutoConfiguration.class)
                .withPropertyValues(
                        "org.tbk.bitcoin.tool.fee.enabled=true"
                )
                .run(context -> {
                    assertThat(context.containsBean("compositeFeeProvider"), is(true));
                    assertThat(context.getBean(CompositeFeeProvider.class), is(notNullValue()));
                });
    }


    @Test
    void noBeansAreCreated() {
        this.contextRunner.withUserConfiguration(BitcoinFeeClientAutoConfiguration.class)
                .withPropertyValues(
                        "org.tbk.bitcoin.tool.fee.enabled=false"
                )
                .run(context -> {
                    assertThat(context.containsBean("compositeFeeProvider"), is(false));
                    assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(CompositeFeeProvider.class));
                });
    }
}
