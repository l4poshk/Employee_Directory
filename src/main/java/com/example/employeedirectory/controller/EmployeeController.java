package com.example.employeedirectory.controller;

import com.example.employeedirectory.model.Employee;
import com.example.employeedirectory.service.DepartmentService;
import com.example.employeedirectory.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping
    public String listEmployees(@RequestParam(required = false) Long dept, 
                                @RequestParam(defaultValue = "0") int page,
                                Model model) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<Employee> employeePage;
        if (dept != null) {
            employeePage = employeeService.findByDepartment(dept, pageable);
        } else {
            employeePage = employeeService.findAll(pageable);
        }
        model.addAttribute("employees", employeePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employeePage.getTotalPages());
        model.addAttribute("totalItems", employeePage.getTotalElements());
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("selectedDept", dept);
        return "employees/list";
    }

    @GetMapping("/search")
    public String searchEmployees(@RequestParam(defaultValue = "") String name, 
                                  @RequestParam(defaultValue = "0") int page,
                                  Model model) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<Employee> employeePage = employeeService.searchByName(name, pageable);
        model.addAttribute("employees", employeePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employeePage.getTotalPages());
        model.addAttribute("totalItems", employeePage.getTotalElements());
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("searchName", name);
        return "employees/list";
    }

    @GetMapping("/{id}")
    public String viewEmployee(@NonNull @PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        return "employees/profile";
    }
}
