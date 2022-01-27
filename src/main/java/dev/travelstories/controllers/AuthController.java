package dev.travelstories.controllers;


import dev.travelstories.dtos.JwtTokenDTO;
import dev.travelstories.dtos.LoginDTO;
import dev.travelstories.dtos.RegisterDTO;
import dev.travelstories.entities.Role;
import dev.travelstories.entities.User;
import dev.travelstories.exceptions.BadRequestException;
import dev.travelstories.repositories.RoleRepository;
import dev.travelstories.repositories.UserRepository;
import dev.travelstories.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static dev.travelstories.constants.Constants.AUTHENTICATION_ACCESS_URL;

@RestController
@RequestMapping(path = AUTHENTICATION_ACCESS_URL)
@CrossOrigin(value = "http://localhost:3000")
public class AuthController {

   private final AuthenticationManager authenticationManager;
   private final UserRepository userRepository;
   private final RoleRepository roleRepository;
   private final PasswordEncoder passwordEncoder;
   private final JwtTokenProvider jwtTokenProvider;


   @Autowired
   public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
      this.authenticationManager = authenticationManager;
      this.userRepository = userRepository;
      this.roleRepository = roleRepository;
      this.passwordEncoder = passwordEncoder;
      this.jwtTokenProvider = jwtTokenProvider;
   }


   @PostMapping(path = "/login")
   @CrossOrigin(value = "http://localhost:3000")
   public ResponseEntity<JwtTokenDTO> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {

      try {
         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                 loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
      } catch (RuntimeException exception) {
         throw new BadRequestException("Email of wachtwoord niet correct");
      }


      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
              loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authentication);

      String token = jwtTokenProvider.generateToken(authentication);

      return new ResponseEntity<>(new JwtTokenDTO(token), HttpStatus.OK);
   }


   @PostMapping(path = "/register")
   @CrossOrigin(value = "http://localhost:3000")
   public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterDTO registerDTO) {

      if (userRepository.existsByEmail(registerDTO.getEmail())) {
         throw new BadRequestException("Email is al in gebruik");
      }

      User user = new User();
      user.setFirstname(registerDTO.getFirstname());
      user.setLastname(registerDTO.getLastname());
      user.setUsername(registerDTO.getEmail());
      user.setEmail(registerDTO.getEmail());
      user.setProfileImage("https://robohash.org/" + registerDTO.getFirstname());
      user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

      Role role = roleRepository.findByName("ROLE_USER").get();
      List<Role> roles = new ArrayList<>();
      roles.add(role);

      user.setRoles(roles);

      userRepository.save(user);

      return new ResponseEntity<>("Gebruiker is geregistreerd", HttpStatus.CREATED);
   }
}
