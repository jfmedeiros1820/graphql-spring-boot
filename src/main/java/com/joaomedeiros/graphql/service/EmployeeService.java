package com.joaomedeiros.graphql.service;

import com.joaomedeiros.graphql.domain.Employee;
import com.joaomedeiros.graphql.filter.EmployeeFilter;
import com.joaomedeiros.graphql.filter.FilterField;
import com.joaomedeiros.graphql.repository.EmployeeRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    @GraphQLQuery(name = "employees")
    public Iterable<Employee> employees() {
        return employeeRepository.findAll();
    }

    @GraphQLQuery(name = "employee")
    public Employee employee(@GraphQLArgument(name = "id") Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @GraphQLQuery(name = "employeesWithFilter")
    public Iterable<Employee> employeesWithFilter(@GraphQLContext EmployeeFilter filter) {
        Specification<Employee> spec = null;
        if (filter.getSalary() != null)
            spec = bySalary(filter.getSalary());
        if (filter.getAge() != null)
            spec = (spec == null ? byAge(filter.getAge()) : spec.and(byAge(filter.getAge())));
        if (filter.getPosition() != null)
            spec = (spec == null ? byPosition(filter.getPosition()) : spec.and(byPosition(filter.getPosition())));
        if (spec != null)
            return employeeRepository.findAll(spec);
        else
            return employeeRepository.findAll();
    }

    private Specification<Employee> bySalary(FilterField filterField) {
        return (root, query, builder) -> filterField.generateCriteria(builder, root.get("salary"));
    }

    private Specification<Employee> byAge(FilterField filterField) {
        return (root, query, builder) -> filterField.generateCriteria(builder, root.get("age"));
    }

    private Specification<Employee> byPosition(FilterField filterField) {
        return (root, query, builder) -> filterField.generateCriteria(builder, root.get("position"));
    }

    @Transactional
    public boolean updateEmployee() {
        int age = 10;
        List<Employee> employees = employeeRepository.findTop50ByAgeGreaterThan(age+1);
        if (employees.isEmpty()) {
            return false;
        }

        employees.forEach(employee -> {
            employee.setAge(age);
            employeeRepository.save(employee);

            log.info("Updating employee '{}' with age '{}'", employee.getFirstName(), employee.getAge());
        });
        return true;
    }
}
