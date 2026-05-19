package edu.bookingtour.svc.auth.security;

import edu.bookingtour.svc.auth.domain.NguoiDung;
import edu.bookingtour.svc.auth.repo.NguoiDungRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DomainUserDetailsService implements UserDetailsService {

    private final NguoiDungRepository nguoiDungRepository;

    public DomainUserDetailsService(NguoiDungRepository nguoiDungRepository) {
        this.nguoiDungRepository = nguoiDungRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NguoiDung nguoiDung = nguoiDungRepository.findByTenDangNhap(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user: " + username));

        String rawRole = nguoiDung.getVaiTro();
        if (rawRole != null && rawRole.startsWith("ROLE_")) {
            rawRole = rawRole.substring(5);
        }
        String role = rawRole != null && !rawRole.isBlank() ? rawRole : "USER";

        return User.builder()
                .username(nguoiDung.getTenDangNhap())
                .password(nguoiDung.getMatKhau() != null ? nguoiDung.getMatKhau() : "")
                .roles(role)
                .build();
    }
}
