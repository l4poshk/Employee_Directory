package com.example.employeedirectory.controller;

import com.example.employeedirectory.model.Position;
import com.example.employeedirectory.service.PositionService;
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
@RequestMapping("/admin/positions")
public class AdminPositionController {

    private final PositionService positionService;
    private final EmployeeService employeeService;

    public AdminPositionController(PositionService positionService, EmployeeService employeeService) {
        this.positionService = positionService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String listPositions(Model model) {
        if (!model.containsAttribute("position")) {
            model.addAttribute("position", new Position());
        }
        model.addAttribute("positions", positionService.findAll());
        return "admin/positions/list";
    }

    @PostMapping("/add")
    public String addPosition(@NonNull @Valid @ModelAttribute("position") Position position, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("positions", positionService.findAll());
            return "admin/positions/list";
        }
        positionService.save(position);
        return "redirect:/admin/positions";
    }

    @PostMapping("/delete/{id}")
    public String deletePosition(@NonNull @PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (employeeService.hasEmployeesInPosition(id)) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete this position because there are employees assigned to it.");
            return "redirect:/admin/positions";
        }
        positionService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Position deleted successfully.");
        return "redirect:/admin/positions";
    }
}
