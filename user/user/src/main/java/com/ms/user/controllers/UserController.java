package com.ms.user.controllers;
import com.ms.user.dto.UserRecordDto;
import com.ms.user.models.UserModel;
import com.ms.user.services.UserService;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
   @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<?> saveUser(@RequestBody  @Valid UserRecordDto userRecordDto){
        try {
            var userModel = new UserModel();
            BeanUtils.copyProperties(userRecordDto,userModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getAllUsers(){
        return ResponseEntity.ok(userService.findAll());
    }

}
