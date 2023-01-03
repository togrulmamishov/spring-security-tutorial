package az.mamishov;

import az.mamishov.dto.AuthenticationResponse;
import az.mamishov.dto.AuthenticationRequest;
import az.mamishov.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class HomeResource {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtTokenUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException exception) {
            throw new Exception("Incorrect username or password");
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @GetMapping("/")
    public String home() {
        return "<h1 style=color:purple>Welcome</h1>";
    }

    @GetMapping("/admin")
    public String admin() {
        return "<h1 style=color:red>Welcome Admin</h1>";
    }

    @GetMapping("/user")
    public String user() {
        return "<h1 style=color:green>Welcome User</h1>";
    }

    @GetMapping("/access-denied")
    public ModelAndView accessDenied() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("access-denied");

        return modelAndView;

    }
}
