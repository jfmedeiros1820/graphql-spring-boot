package com.joaomedeiros.graphql.repository;

import com.joaomedeiros.graphql.domain.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Integer> {
}
