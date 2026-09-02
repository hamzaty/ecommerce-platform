package com.ecommerce.config;

import com.ecommerce.auth.entity.Role;
import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.repository.RoleRepository;
import com.ecommerce.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        for (Role.RoleName roleName : Role.RoleName.values()) {
            if (roleRepository.findByName(roleName).isEmpty()) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }

        if (userRepository.findByEmail("superadmin@ecommerce.com").isEmpty()) {
            Role superAdminRole = roleRepository.findByName(Role.RoleName.SUPER_ADMIN).orElseThrow();

            User superAdmin = new User();
            superAdmin.setEmail("superadmin@ecommerce.com");
            superAdmin.setPassword(passwordEncoder.encode("ChangeMe123!"));
            superAdmin.setFirstName("Super");
            superAdmin.setLastName("Admin");
            superAdmin.setRoles(Set.of(superAdminRole));

            userRepository.save(superAdmin);
        }
    }
}