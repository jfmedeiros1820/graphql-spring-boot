package com.joaomedeiros.graphql.domain;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Organization {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @GraphQLQuery(name = "id", description = "A Organization's id")
    private Integer id;

    @GraphQLQuery(name = "name", description = "A Organization's name")
    private String name;

    @OneToMany(mappedBy = "organization")
    @GraphQLQuery(name = "departments", description = "A Organization's departments")
    private Set<Department> departments;

    @OneToMany(mappedBy = "organization")
    @GraphQLQuery(name = "employees", description = "A Organization's employees")
    private Set<Employee> employees;
}
