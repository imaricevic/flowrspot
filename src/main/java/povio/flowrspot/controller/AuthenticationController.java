package povio.flowrspot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import povio.flowrspot.dto.AuthenticationRequestDTO;
import povio.flowrspot.dto.AuthenticationResponseDTO;
import povio.flowrspot.model.User;
import povio.flowrspot.repository.UserRepository;
import povio.flowrspot.security.JwtUtil;

@RestController
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/hello")
    public ResponseEntity<?> hello() {
        logger.info("hello");
        return ResponseEntity.ok("hello");
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO request) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (AuthenticationException e) {
            logger.error("Bad credentials..");
            throw new Exception("Username or password are incorrect", e);
        }
        final String jwt = jwtUtil.generateJwtToken(request.getUsername());
        logger.info("User " + request.getUsername() + " successfully logged in.");
        return ResponseEntity.ok(new AuthenticationResponseDTO(jwt));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.existsByUsernameOrMail(user.getUsername(), user.getMail())) {
            return ResponseEntity.badRequest().body("Username/mail are already used.");
        }
        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        logger.info("User " + user.getUsername() + " successfully registered.");

        return ResponseEntity.ok("Registration completed.");
    }
}
