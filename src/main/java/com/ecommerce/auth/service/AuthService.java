package com.ecommerce.auth.service;
import com.ecommerce.auth.dto.AuthResponse;
import com.ecommerce.auth.dto.LoginRequest;
import com.ecommerce.auth.dto.RegisterRequest;
import com.ecommerce.auth.entity.Role;
import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.repository.RoleRepository;
import com.ecommerce.auth.repository.UserRepository;
import com.ecommerce.config.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;
import com.ecommerce.auth.dto.CreateAdminRequest;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    public AuthResponse createAdmin(CreateAdminRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé");
        }

        Role adminRole = roleRepository.findByName(Role.RoleName.ADMIN)
                .orElseThrow(() -> new RuntimeException("Rôle ADMIN introuvable"));

        User admin = new User();
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setFirstName(request.getFirstName());
        admin.setLastName(request.getLastName());
        admin.setRoles(Set.of(adminRole));

        userRepository.save(admin);

        String token = jwtUtil.generateToken(admin.getEmail());
        return new AuthResponse(token, admin.getEmail());
    }


    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé");
        }

        Role clientRole = roleRepository.findByName(Role.RoleName.CLIENT)
                .orElseThrow(() -> new RuntimeException("Rôle CLIENT introuvable"));

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setRoles(Set.of(clientRole));

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token, user.getEmail());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token, user.getEmail());
    }
}