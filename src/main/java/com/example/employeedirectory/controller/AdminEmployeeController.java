package com.example.employeedirectory.controller;

import com.example.employeedirectory.model.Employee;
import com.example.employeedirectory.service.DepartmentService;
import com.example.employeedirectory.service.EmployeeService;
import com.example.employeedirectory.service.PositionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/employees")
public class AdminEmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final PositionService positionService;

    public AdminEmployeeController(EmployeeService employeeService, DepartmentService departmentService, PositionService positionService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.positionService = positionService;
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("positions", positionService.findAll());
        return "admin/add-employee";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@NonNull @PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("positions", positionService.findAll());
        return "admin/edit-employee";
    }

    @PostMapping("/save")
    public String saveEmployee(@NonNull @Valid @ModelAttribute("employee") Employee employee, BindingResult result, 
                               @RequestParam(required = false) Long departmentId, 
                               @RequestParam(required = false) Long positionId, 
                               Model model) {
        
        if (departmentId == null) {
            result.rejectValue("department", "error.employee", "Department is required");
        }
        if (positionId == null) {
            result.rejectValue("position", "error.employee", "Position is required");
        }

        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("positions", positionService.findAll());
            return employee.getId() == null ? "admin/add-employee" : "admin/edit-employee";
        }

        if (departmentId != null && positionId != null) {
            employee.setDepartment(departmentService.findById(departmentId));
            employee.setPosition(positionService.findById(positionId));
            employeeService.save(employee);
        }
        return "redirect:/employees";
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@NonNull @PathVariable Long id) {
        employeeService.deleteById(id);
        return "redirect:/employees";
    }
}
