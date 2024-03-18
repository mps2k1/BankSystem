package mps2k1.bank.controller;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mps2k1.bank.dto.LoginRequest;
import mps2k1.bank.dto.UserRequest;
import mps2k1.bank.model.Token;
import mps2k1.bank.model.UserEntity;
import mps2k1.bank.service.imp.TokenService;
import mps2k1.bank.service.imp.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController{
    private final UserServiceImpl userService;
    private final TokenService tokenService;
    public AuthController(UserServiceImpl userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }
    @PostMapping("/register")
    @ApiResponse(description = "Registers a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "406", description = "Not acceptable")
    })
    public ResponseEntity<String> register(@RequestBody UserRequest userRequest){
        try {
            UserEntity user = userService.saveUser(userRequest);
            return new ResponseEntity<>("Register succes", HttpStatus.CREATED);
        }
        catch (RuntimeException e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @PostMapping("/login")
    @ApiResponse(description = "Generate token for user's authentication")
    @ApiResponse(responseCode = "200", description = "Successfully logged in")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            Token token = tokenService.loginUser(request);
            return ResponseEntity.ok(token.getTokenValue().toString());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam UUID token) {
        try {
        tokenService.logoutUser(token);
        return new ResponseEntity<>("Logout succes",HttpStatus.OK);
    }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        }

    }
