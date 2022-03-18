package com.joaomedeiros.graphql.domain;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Department {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @GraphQLQuery(name = "id", description = "A department's id")
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "department")
    @GraphQLQuery(name = "employees", description = "A department's employees")
    private Set<Employee> employees;

    @ManyToOne(fetch = FetchType.LAZY)
    @GraphQLQuery(name = "organization", description = "A department's organization")
    private Organization organization;
}
