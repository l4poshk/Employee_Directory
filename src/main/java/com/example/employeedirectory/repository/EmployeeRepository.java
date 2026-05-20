package com.example.employeedirectory.repository;

import com.example.employeedirectory.model.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @NonNull
    @EntityGraph(attributePaths = {"department", "position"})
    Page<Employee> findAll(@NonNull Pageable pageable);

    @NonNull
    @EntityGraph(attributePaths = {"department", "position"})
    Optional<Employee> findById(@NonNull Long id);

    @EntityGraph(attributePaths = {"department", "position"})
    Page<Employee> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @EntityGraph(attributePaths = {"department", "position"})
    Page<Employee> findByDepartmentId(Long departmentId, Pageable pageable);

    @EntityGraph(attributePaths = {"department", "position"})
    Page<Employee> findByNameContainingIgnoreCaseAndDepartmentId(String name, Long departmentId, Pageable pageable);

    boolean existsByDepartmentId(Long departmentId);

    boolean existsByPositionId(Long positionId);
}
