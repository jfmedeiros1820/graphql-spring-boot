package com.joaomedeiros.graphql.domain;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Employee {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @GraphQLQuery(name = "id", description = "An Employee's id")
    private Integer id;

    @GraphQLQuery(name = "firstName", description = "An Employee's firstName")
    private String firstName;

    @GraphQLQuery(name = "lastName", description = "An Employee's lastName")
    private String lastName;

    @GraphQLQuery(name = "position", description = "An Employee's position")
    private String position;

    @GraphQLQuery(name = "salary", description = "An Employee's salary")
    private int salary;

    @GraphQLQuery(name = "age", description = "An Employee's age")
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @GraphQLQuery(name = "department", description = "An Employee's department")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @GraphQLQuery(name = "organization", description = "An Employee's organization")
    private Organization organization;
}
