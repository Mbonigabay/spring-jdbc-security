package com.example.demo.Controller;

import javax.servlet.http.HttpSession;

import com.example.demo.Config.JWTConfig;
import com.example.demo.Model.AuthenticationRequest;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.MyUserDetailsService;
import com.example.demo.Util.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("api")
public class UserController {
    @Autowired
    UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTConfig jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;

    @RequestMapping("")
    public String getUsers(){
        log.info("{}", userRepository.findByUsername("johndoe"));
        return "Hello";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse<String>> createAuthenticationToken(
			@RequestBody AuthenticationRequest authenticationRequest, Authentication authentication,
			HttpSession session) throws Exception {
                log.info("{}", authenticationRequest);
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(),
					authenticationRequest.getPassword()));

		} catch (BadCredentialsException e) {
			// throw new Exception("Incorrect username or password", e);
			final String message = "Incorrect username or password...";

			ApiResponse<String> body = new ApiResponse<>(HttpStatus.UNAUTHORIZED, message, e.getMessage());

			return ResponseEntity.ok(body);
        }
     

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());

		final String jwt = jwtTokenUtil.generateToken(userDetails);
		final String message = "Users logged in successfully...";

		ApiResponse<String> body = new ApiResponse<>(HttpStatus.OK, message, jwt);

		return ResponseEntity.ok(body);
	}
}
