package edu.bookingtour.svc.user.api;

import edu.bookingtour.svc.user.domain.UserProfile;
import edu.bookingtour.svc.user.repo.UserProfileRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    public record ProfileDto(
            @NotBlank String hoTen,
            String email,
            String number,
            String anhDaiDien) {}

    private final UserProfileRepository repo;

    public UserProfileController(UserProfileRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/me")
    public UserProfile me(@AuthenticationPrincipal Jwt jwt) {
        int uid = Integer.parseInt(jwt.getSubject());
        return repo.findById(uid)
                .orElseGet(() -> {
                    UserProfile p = new UserProfile();
                    p.setUserId(uid);
                    p.setEmail(jwt.getClaimAsString("preferred_username"));
                    return p;
                });
    }

    @PutMapping("/me")
    public UserProfile saveMe(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody ProfileDto body) {
        int uid = Integer.parseInt(jwt.getSubject());
        UserProfile p = repo.findById(uid).orElseGet(() -> {
            UserProfile x = new UserProfile();
            x.setUserId(uid);
            return x;
        });
        p.setHoTen(body.hoTen());
        if (body.email() != null && !body.email().isBlank()) {
            repo.findAll().stream()
                    .filter(o -> body.email().equalsIgnoreCase(o.getEmail())
                            && !o.getUserId().equals(uid))
                    .findAny()
                    .ifPresent(other -> {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email đã dùng");
                    });
            p.setEmail(body.email());
        }
        p.setNumber(body.number());
        p.setAnhDaiDien(body.anhDaiDien());
        return repo.save(p);
    }
}
