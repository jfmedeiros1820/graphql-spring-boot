package com.joaomedeiros.graphql.scheduler;

import com.joaomedeiros.graphql.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UpdateEmployee {

    private static final long ONE_MINUTE = 30L * 1000L;

    private EmployeeService employeeService;

    @Scheduled(initialDelay = ONE_MINUTE, fixedDelay = ONE_MINUTE)
    public void execute() {

        log.info("Updating employee...");

        boolean pending = true;
        while (pending) {
            pending = employeeService.updateEmployee();
        }
    }
}
