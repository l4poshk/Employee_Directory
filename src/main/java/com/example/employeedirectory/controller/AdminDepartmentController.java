package com.example.employeedirectory.controller;

import com.example.employeedirectory.model.Department;
import com.example.employeedirectory.service.DepartmentService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.employeedirectory.service.EmployeeService;

@Controller
@RequestMapping("/admin/departments")
public class AdminDepartmentController {

    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public AdminDepartmentController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String listDepartments(Model model) {
        if (!model.containsAttribute("department")) {
            model.addAttribute("department", new Department());
        }
        model.addAttribute("departments", departmentService.findAll());
        return "admin/departments/list";
    }

    @PostMapping("/add")
    public String addDepartment(@NonNull @Valid @ModelAttribute("department") Department department, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.findAll());
            return "admin/departments/list";
        }
        departmentService.save(department);
        return "redirect:/admin/departments";
    }

    @PostMapping("/delete/{id}")
    public String deleteDepartment(@NonNull @PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (employeeService.hasEmployeesInDepartment(id)) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete this department because there are employees assigned to it.");
            return "redirect:/admin/departments";
        }
        departmentService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Department deleted successfully.");
        return "redirect:/admin/departments";
    }
}
