package com.example.employeedirectory.service;

import com.example.employeedirectory.model.Employee;
import com.example.employeedirectory.repository.EmployeeRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Page<Employee> findAll(@NonNull Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Employee findById(@NonNull Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    public Page<Employee> searchByName(String name, @NonNull Pageable pageable) {
        return employeeRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Page<Employee> findByDepartment(Long departmentId, @NonNull Pageable pageable) {
        return employeeRepository.findByDepartmentId(departmentId, pageable);
    }

    public boolean hasEmployeesInDepartment(Long departmentId) {
        return employeeRepository.existsByDepartmentId(departmentId);
    }

    public boolean hasEmployeesInPosition(Long positionId) {
        return employeeRepository.existsByPositionId(positionId);
    }

    @Cacheable("employeeCount")
    public long count() {
        return employeeRepository.count();
    }

    @Transactional
    @CacheEvict(value = {"employees", "employeeCount"}, allEntries = true)
    public Employee save(@NonNull Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional
    @CacheEvict(value = {"employees", "employeeCount"}, allEntries = true)
    public void deleteById(@NonNull Long id) {
        employeeRepository.deleteById(id);
    }
}
