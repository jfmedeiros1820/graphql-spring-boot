package com.joaomedeiros.graphql.repository;

import com.joaomedeiros.graphql.domain.Department;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer>, JpaSpecificationExecutor<Department> {

}
