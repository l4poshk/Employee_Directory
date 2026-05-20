package com.example.employeedirectory.service;

import com.example.employeedirectory.model.Department;
import com.example.employeedirectory.repository.DepartmentRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Cacheable("departments")
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public Department findById(@NonNull Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
    }

    @Transactional
    @CacheEvict(value = "departments", allEntries = true)
    public Department save(@NonNull Department department) {
        return departmentRepository.save(department);
    }

    @Transactional
    @CacheEvict(value = "departments", allEntries = true)
    public void deleteById(@NonNull Long id) {
        departmentRepository.deleteById(id);
    }
}
