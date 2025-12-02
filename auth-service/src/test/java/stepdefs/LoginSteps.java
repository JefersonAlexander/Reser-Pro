package stepdefs;

import com.reser_pro.auth_service.DTO.LoginRequest;
import com.reser_pro.auth_service.DTO.LoginResponse;
import com.reser_pro.auth_service.entity.Role;
import com.reser_pro.auth_service.entity.User;
import com.reser_pro.auth_service.repository.UserRepository;
import com.reser_pro.auth_service.service.AuthAuditService;
import com.reser_pro.auth_service.service.AuthService;
import com.reser_pro.auth_service.util.JwtUtil;
import io.cucumber.java.en.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LoginSteps {

    private AuthService authService;

    private final AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final JwtUtil jwtUtil = mock(JwtUtil.class);
    private final AuthAuditService auditService = mock(AuthAuditService.class);

    private Exception caughtException;
    private LoginResponse loginResponse;

    private User existingUser;

    @Given("I am an active user with email {string} and password {string}")
    public void iAmAnActiveUser(String email, String rawPassword) {

        Role role = new Role();
        role.setRoleId(1L);
        role.setRoleName("ADMIN");

        existingUser = User.builder()
                .userId(1L)
                .email(email)
                .password("encoded_" + rawPassword)
                .userName("Test User")
                .phoneNumber("123456789")
                .isActive(true)
                .roles(List.of(role))
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                email, existingUser.getPassword(), List.of(new SimpleGrantedAuthority("ADMIN"))
        );

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);

        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("TokenJWT");

        injectMocks();
    }

    @Given("I am a not existing user")
    public void iAmANotExistingUser() {

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("User not found"));

        injectMocks();
    }

    @When("I enter the email {string} and the password {string}")
    public void iEnterCredentials(String email, String password) {

        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);

        try {
            loginResponse = authService.login(request);
            caughtException = null;
        } catch (Exception e) {
            loginResponse = null;
            caughtException = e;
        }
    }

    @Then("login is successful")
    public void loginIsSuccessful() {
        assertNull(caughtException);
        assertNotNull(loginResponse);
    }

    @Then("the JWT token is generated")
    public void tokenGenerated() {
        assertNotNull(loginResponse.getToken());
        assertEquals("TokenJWT", loginResponse.getToken());
    }

    @Then("login fails")
    public void loginFails() {
        assertNotNull(caughtException);
    }

    @Then("I can see a message that says {string}")
    public void iCanSeeMessage(String expectedMessage) {
        assertEquals(expectedMessage, caughtException.getMessage());
    }

    private void injectMocks() {
        authService = new AuthService();
        try {
            var field1 = AuthService.class.getDeclaredField("authenticationManager");
            field1.setAccessible(true);
            field1.set(authService, authenticationManager);

            var field2 = AuthService.class.getDeclaredField("jwtUtil");
            field2.setAccessible(true);
            field2.set(authService, jwtUtil);

            var field3 = AuthService.class.getDeclaredField("userRepository");
            field3.setAccessible(true);
            field3.set(authService, userRepository);

            var field4 = AuthService.class.getDeclaredField("auditService");
            field4.setAccessible(true);
            field4.set(authService, auditService);

            var field5 = AuthService.class.getDeclaredField("roleRepository");
            field5.setAccessible(true);
            field5.set(authService, null);

            var field6 = AuthService.class.getDeclaredField("userMapper");
            field6.setAccessible(true);
            field6.set(authService, null);

            var field7 = AuthService.class.getDeclaredField("passwordEncoder");
            field7.setAccessible(true);
            field7.set(authService, null);

        } catch (Exception e) {
            throw new RuntimeException("Error injecting mocks", e);
        }
    }
}
