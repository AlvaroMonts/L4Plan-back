package com.l4plan.api.rest.controllers;

import com.l4plan.api.rest.dto.*;
import com.l4plan.api.rest.model.User;
import com.l4plan.api.rest.service.EmailService;
import com.l4plan.api.rest.service.UserService;
import com.l4plan.api.rest.utils.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController extends GenericController<User, UserDTO> {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bcrypt;

    @Autowired
    EmailService emailService;

    @GetMapping("/email/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.existsUserByEmail(email));
    }

    /** /register same as 'create' for users but this one is permitted without being logged */
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterUserDTO dto) throws URISyntaxException {
        if(!userService.existsUserByEmail(dto.getEmail())) {
            dto.setPass(bcrypt.encode(dto.getPass()));
            Long id = userService.registerUser(dto);
            URI uri = new URI(ServletUriComponentsBuilder.fromCurrentRequest().toUriString().replace("/register",""));
            // ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri()).body(id);
            return ResponseEntity.created(ServletUriComponentsBuilder.fromUriString(uri.toString()).build().toUri()).body(id);
        } else {
            return ResponseEntity.badRequest().body("Ya existe una cuenta con ese correo electrónico, elija otro");
        }
    }

    @PutMapping("/data/{id}")
    public ResponseEntity<Object> updateUserDataLogged(@PathVariable("id") Long id, @RequestBody UserDataDTO user) {
        try {
            UserDTO userDTO = userService.findById(id);
            if(userDTO == null) {
                return ResponseEntity.notFound().build();
            }
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setBirthDate(user.getBirthDate());
            userDTO.setGender(user.getGender());
            userService.update(id, userDTO);
            return ResponseEntity.noContent().build();
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/change-psw")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            UserDTO user = userService.findByEmail(request.getEmail());
            String rawPass = PasswordGenerator.generateRandomPassword();
            emailService.sendEmailChangingPass(user.getEmail(), rawPass);
            user.setPass(bcrypt.encode(rawPass));
            userService.update(user.getId(), user);
            return ResponseEntity.ok("Se ha enviado un mail con su nueva contraseña, revise su buzón");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body("Ese correo electrónico no esta registrado");
        } catch(MailException e) {
            return ResponseEntity.badRequest().body("No se ha logrado enviar un mail a su correo, por lo que no se ha cambiado su contraseña.\nContacte con un administrador");
        }
    }

    @PutMapping("/change-psw-logged/{id}")
    public ResponseEntity<Object> changePasswordLogged(@PathVariable("id") Long id, @RequestBody ChangePasswordLoggedRequest request) {
        try {
            // User from db with password decoded
            UserDTO userDTO = userService.findById(id);
            if(userDTO == null) {
                return ResponseEntity.notFound().build();
            }
            if (!bcrypt.matches(request.getOldPassword(), userDTO.getPass())) {
                return ResponseEntity.badRequest().body("La contraseña antigua no es la que se ha indicado");
            }
            // Encode the new password and assign it to the user
            userDTO.setPass(bcrypt.encode(request.getNewPassword()));
            userService.update(userDTO.getId(), userDTO);
            return ResponseEntity.noContent().build();
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
