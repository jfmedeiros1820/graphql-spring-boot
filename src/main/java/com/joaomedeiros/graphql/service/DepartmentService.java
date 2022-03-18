package com.joaomedeiros.graphql.service;

import com.joaomedeiros.graphql.domain.Department;
import com.joaomedeiros.graphql.domain.Employee;
import com.joaomedeiros.graphql.domain.Organization;
import com.joaomedeiros.graphql.repository.DepartmentRepository;
import graphql.com.google.common.collect.Lists;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.*;

@Service
@GraphQLApi
@AllArgsConstructor
public class DepartmentService {

    private static final String EMPLOYEES = "employees";
    private static final String ORGANIZATION = "organization";

    private final DepartmentRepository departmentRepository;

    @GraphQLQuery(name = "departments")
    public Iterable<Department> departments(@GraphQLEnvironment Set<String> subfields) {
        Optional<Specification<Department>> specificationOpt = Optional.empty();

        if (subfields.contains(EMPLOYEES) && !subfields.contains(ORGANIZATION))
            specificationOpt = Optional.of(fetchEmployees());
        else if (!subfields.contains(EMPLOYEES) && subfields.contains(ORGANIZATION))
            specificationOpt = Optional.of(fetchOrganization());
        else if (subfields.contains(EMPLOYEES) && subfields.contains(ORGANIZATION))
            specificationOpt = Optional.of(fetchEmployees().and(fetchOrganization()));

        List<Department> departments = new ArrayList<>();
        specificationOpt.ifPresentOrElse(
                departmentSpecification -> departments.addAll(departmentRepository.findAll(departmentSpecification)),
                () -> departments.addAll(Lists.newArrayList(departmentRepository.findAll())));
        return departments;
    }

    @GraphQLQuery(name = "department")
    public Department department(@GraphQLArgument(name = "id") Integer id, @GraphQLEnvironment Set<String> subfields) {
        Specification<Department> spec = byId(id);
        if (subfields.contains(EMPLOYEES))
            spec = spec.and(fetchEmployees());
        if (subfields.contains(ORGANIZATION))
            spec = spec.and(fetchOrganization());
        return departmentRepository.findOne(spec).orElseThrow(NoSuchElementException::new);
    }

    private Specification<Department> fetchOrganization() {
        return (root, query, builder) -> {
            query.distinct(true);
            Fetch<Department, Organization> fetch = root.fetch(ORGANIZATION, JoinType.LEFT);
            Join<Department, Organization> join = (Join<Department, Organization>) fetch;
            return join.getOn();
        };
    }

    private Specification<Department> fetchEmployees() {
        return (root, query, builder) -> {
            query.distinct(true);
            Fetch<Department, Employee> fetch = root.fetch(EMPLOYEES, JoinType.LEFT);
            Join<Department, Employee> join = (Join<Department, Employee>) fetch;
            return join.getOn();
        };
    }

    private Specification<Department> byId(Integer id) {
        return (root, query, builder) -> builder.equal(root.get("id"), id);
    }
}
