package com.joaomedeiros.graphql.service;

import com.joaomedeiros.graphql.domain.Organization;
import com.joaomedeiros.graphql.repository.OrganizationRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationService {

    private OrganizationRepository organizationRepository;

    @GraphQLQuery(name = "organizations")
    public Iterable<Organization> organizations() {
        return organizationRepository.findAll();
    }

    @GraphQLQuery(name = "organization")
    public Organization organization(@GraphQLArgument(name = "id") Integer id) {
        return organizationRepository.findById(id).orElseThrow();
    }
}
