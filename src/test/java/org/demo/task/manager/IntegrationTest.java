package org.demo.task.manager;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableSpringDataWebSupport // Need it for Pageable
@AutoConfigureMockMvc
public abstract class IntegrationTest extends UnitTest {

}
