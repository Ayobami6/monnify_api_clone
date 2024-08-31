package com.ayo.monnify_api_clone.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ayo.monnify_api_clone.auth.dto.AuthResponseDto;
import com.ayo.monnify_api_clone.auth.dto.LoginDto;
import com.ayo.monnify_api_clone.auth.dto.RegisterDto;
import com.ayo.monnify_api_clone.exception.ServiceException;
import com.ayo.monnify_api_clone.user.UserEntity;
import com.ayo.monnify_api_clone.user.UserRepository;

public class AuthenticationServiceTest {


    @InjectMocks
    private AuthenticationService authenticationService;

    // mock dependencies
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        // given
        String token = "6hhsdgt632ktkekkwkwgekwhedvgwvegehw";
        LoginDto loginDto = new LoginDto(
            "user@example.com",
            "somepassword"
        );

        UserEntity user = UserEntity.builder()
                            .email("user@example.com")
                            .firstName("John")
                            .lastName("Doe")
                            .phoneNumber("123-45655666")
                            .password("somepassword")
                            .build();

        // mock calls
        Mockito.when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken("user@example.com", "somepassword"));
        Mockito.when(jwtService.generateToken(any(UserDetails.class))).thenReturn(token);
        Mockito.when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.of(user));

        // when
        AuthResponseDto authResponseDto = authenticationService.login(loginDto);

        // then
        assertEquals(authResponseDto.getAccessToken(), token);

    }

    @Test
    void testLoginAPI() {

    }

    @Test
    public void testRegister_success() {
        // given
        String token = "6hhsdgt632ktkekkwkwgekwhedvgwvegehw";
        UserEntity user = UserEntity.builder()
                            .email("user@example.com")
                            .firstName("John")
                            .lastName("Doe")
                            .phoneNumber("123-45655666")
                            .password("somepassword")
                            .build();

        RegisterDto registerDto = new RegisterDto(
            "user@example.com",
            "John",
            "Doe",
            "123-45655666",
            "somepassword"
        );

        // mock calls
        Mockito.when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        Mockito.when(jwtService.generateToken(any(UserEntity.class))).thenReturn(token);
        Mockito.when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("Hashedpassword");
        // Mockito.when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken("user@example.com", "somepassword"));

        // when
        AuthResponseDto authResponseDto = authenticationService.register(registerDto);
        System.out.println("This is auth" + authResponseDto);

        // then
        assertEquals(token, authResponseDto.getAccessToken());

    }

    @Test
    public void testRegister_emailExists() {
        // given
        String token = "6hhsdgt632ktkekkwkwgekwhedvgwvegehw";
        UserEntity user = UserEntity.builder()
                            .email("user@example.com")
                            .firstName("John")
                            .lastName("Doe")
                            .phoneNumber("123-45655666")
                            .password("somepassword")
                            .build();

        RegisterDto registerDto = new RegisterDto(
            "user@example.com",
            "John",
            "Doe",
            "123-45655666",
            "somepassword"
        );

        // mock calls
        Mockito.when(userRepository.save(any(UserEntity.class))).thenThrow(new DataIntegrityViolationException(""));
        Mockito.when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("Hashedpassword");

        ServiceException exception = assertThrows(ServiceException.class, () -> { 
            authenticationService.register(registerDto);
        });

        assertEquals(400, exception.getStatusCode());
        assertEquals("Email already exists", exception.getMessage());

    }
}
