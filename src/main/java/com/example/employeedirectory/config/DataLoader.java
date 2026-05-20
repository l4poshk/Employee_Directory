package com.example.employeedirectory.config;

import com.example.employeedirectory.model.Department;
import com.example.employeedirectory.model.Employee;
import com.example.employeedirectory.model.Role;
import com.example.employeedirectory.model.User;
import com.example.employeedirectory.model.Position;
import com.example.employeedirectory.repository.DepartmentRepository;
import com.example.employeedirectory.repository.EmployeeRepository;
import com.example.employeedirectory.repository.PositionRepository;
import com.example.employeedirectory.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository,
                      DepartmentRepository departmentRepository,
                      PositionRepository positionRepository,
                      EmployeeRepository employeeRepository,
                      BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.positionRepository = positionRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            // Seed Users
            User admin = new User("admin", passwordEncoder.encode("admin123"), Role.ROLE_ADMIN);
            User user = new User("user", passwordEncoder.encode("user123"), Role.ROLE_USER);
            userRepository.save(admin);
            userRepository.save(user);

            // Seed Departments
            Department engineering = new Department("Engineering");
            Department humanResources = new Department("Human Resources");
            departmentRepository.save(engineering);
            departmentRepository.save(humanResources);

            // Seed Positions
            Position swEng = new Position("Software Engineer");
            Position snrDev = new Position("Senior Developer");
            Position hrMgr = new Position("HR Manager");
            Position devOps = new Position("DevOps Engineer");
            Position hrSpec = new Position("HR Specialist");
            positionRepository.save(swEng);
            positionRepository.save(snrDev);
            positionRepository.save(hrMgr);
            positionRepository.save(devOps);
            positionRepository.save(hrSpec);

            // Seed 23 Employees
            for (int i = 1; i <= 23; i++) {
                Department dept = (i % 2 == 0) ? humanResources : engineering;
                Position pos = swEng;
                if (i % 3 == 0) pos = snrDev;
                if (i % 4 == 0) pos = devOps;
                if (i % 5 == 0) pos = hrSpec;
                
                employeeRepository.save(new Employee("Employee " + i, pos, dept));
            }
        }

        // Always ensure superadmin exists
        if (userRepository.findByUsername("superadmin").isEmpty()) {
            User superadmin = new User("superadmin", passwordEncoder.encode("superadmin"), Role.ROLE_SUPERADMIN);
            userRepository.save(superadmin);
        }
    }
}
