package com.example.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = {"com.example:producer:+:stubs"},
                         stubsMode = StubRunnerProperties.StubsMode.LOCAL)
@ContextConfiguration(classes = {RabbitConfig.class})
class ConsumerApplicationTests {
    
    @Autowired
    private StubTrigger stubTrigger;
    
    @Test
    void shouldReceiveMessage() {
        stubTrigger.trigger("custom-event-trigger");
        assertNotNull(ConsumerApplication.MESSAGE);
        assertEquals(ConsumerApplication.MESSAGE, "AAA");
    }
}
