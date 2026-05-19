package edu.bookingtour.svc.auth.api;

import edu.bookingtour.svc.auth.api.dto.LoginRequest;
import edu.bookingtour.svc.auth.api.dto.RegisterRequest;
import edu.bookingtour.svc.auth.api.dto.TokenResponse;
import edu.bookingtour.svc.auth.config.JwtProperties;
import edu.bookingtour.svc.auth.domain.NguoiDung;
import edu.bookingtour.svc.auth.repo.NguoiDungRepository;
import edu.bookingtour.svc.auth.security.DomainUserDetailsService;
import edu.bookingtour.svc.auth.security.JwtTokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final DomainUserDetailsService domainUserDetailsService;
    private final NguoiDungRepository nguoiDungRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final JwtProperties jwtProperties;

    public AuthRestController(
            AuthenticationManager authenticationManager,
            DomainUserDetailsService domainUserDetailsService,
            NguoiDungRepository nguoiDungRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenService jwtTokenService,
            JwtProperties jwtProperties) {
        this.authenticationManager = authenticationManager;
        this.domainUserDetailsService = domainUserDetailsService;
        this.nguoiDungRepository = nguoiDungRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.jwtProperties = jwtProperties;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest req) {
        Authentication auth =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        NguoiDung entity = nguoiDungRepository.findByTenDangNhap(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException(auth.getName()));
        String jwt = jwtTokenService.issueToken(entity.getId(), entity.getTenDangNhap(), auth.getAuthorities());
        return ResponseEntity.ok(toTokenResponse(entity.getTenDangNhap(), jwt, entity.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        if (req.confirmPassword() != null && !req.confirmPassword().isBlank()
                && !req.matKhau().equals(req.confirmPassword())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Mật khẩu xác nhận không khớp"));
        }
        if (nguoiDungRepository.findByTenDangNhap(req.tenDangNhap()).isPresent()) {
            return conflict("Tên đăng nhập đã tồn tại");
        }
        if (nguoiDungRepository.findByEmail(req.email()).isPresent()) {
            return conflict("Email đã được sử dụng");
        }
        NguoiDung user = new NguoiDung();
        user.setTenDangNhap(req.tenDangNhap());
        user.setEmail(req.email());
        user.setHoTen(req.hoTen());
        user.setMatKhau(passwordEncoder.encode(req.matKhau()));
        user.setVaiTro("USER");
        user = nguoiDungRepository.save(user);

        UserDetails ud = domainUserDetailsService.loadUserByUsername(user.getTenDangNhap());
        String jwt = jwtTokenService.issueToken(user.getId(), user.getTenDangNhap(), ud.getAuthorities());
        return ResponseEntity.status(HttpStatus.CREATED).body(toTokenResponse(user.getTenDangNhap(), jwt, user.getId()));
    }

    private ResponseEntity<Map<String, String>> conflict(String message) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", message));
    }

    private TokenResponse toTokenResponse(String username, String jwt, int userId) {
        return new TokenResponse(
                jwt,
                "Bearer",
                jwtProperties.getAccessTokenTtlMinutes() * 60,
                userId,
                username);
    }
}
