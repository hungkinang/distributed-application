package edu.bookingtour.svc.admin.api;

import edu.bookingtour.svc.admin.service.AdminProbeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminOverviewController {

    private final AdminProbeService probe;

    public AdminOverviewController(AdminProbeService probe) {
        this.probe = probe;
    }

    @GetMapping("/overview")
    public Map<String, Object> overview() {
        return probe.probeAll();
    }
}
