package edu.bookingtour.svc.tour.api;

import edu.bookingtour.svc.tour.domain.ChuyenDi;
import edu.bookingtour.svc.tour.domain.LichTrinh;
import edu.bookingtour.svc.tour.domain.NgayKhoiHanh;
import edu.bookingtour.svc.tour.domain.QuanLyCho;
import edu.bookingtour.svc.tour.repo.LichTrinhRepository;
import edu.bookingtour.svc.tour.repo.NgayKhoiHanhRepository;
import edu.bookingtour.svc.tour.repo.QuanLyChoRepository;
import edu.bookingtour.svc.tour.service.TourCatalogService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tours")
public class TourRestController {

    private final TourCatalogService catalog;
    private final NgayKhoiHanhRepository ngayKhoiHanhRepository;
    private final LichTrinhRepository lichTrinhRepository;
    private final QuanLyChoRepository quanLyChoRepository;

    public TourRestController(
            TourCatalogService catalog,
            NgayKhoiHanhRepository ngayKhoiHanhRepository,
            LichTrinhRepository lichTrinhRepository,
            QuanLyChoRepository quanLyChoRepository) {
        this.catalog = catalog;
        this.ngayKhoiHanhRepository = ngayKhoiHanhRepository;
        this.lichTrinhRepository = lichTrinhRepository;
        this.quanLyChoRepository = quanLyChoRepository;
    }

    /** Gọn cho Chat / BFF embed danh sách tour. */
    @GetMapping("/chat-context")
    public List<Map<String, Object>> chatContext(@RequestParam(defaultValue = "20") int limit) {
        return catalog.active(0, Math.min(limit, 50)).getContent().stream()
                .map(t -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("id", t.getId());
                    m.put("tieuDe", t.getTieuDe());
                    m.put("gia", t.getGia());
                    m.put(
                            "diemDen",
                            t.getIdDiemDen() != null ? t.getIdDiemDen().getThanhPho() : null);
                    m.put("moTaShort", shorten(t.getMoTa(), 120));
                    return m;
                })
                .collect(Collectors.toList());
    }

    private String shorten(String s, int len) {
        if (s == null) return null;
        return s.length() <= len ? s : s.substring(0, len) + "…";
    }

    @GetMapping("/active")
    public Page<ChuyenDi> active(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return catalog.active(page, size);
    }

    @GetMapping("/completed")
    public Page<ChuyenDi> completed(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return catalog.finished(page, size);
    }

    @GetMapping("/filter")
    public Page<ChuyenDi> filter(
            @RequestParam(required = false) String thanhPho,
            @RequestParam(required = false) String quocGia,
            @RequestParam(required = false) String diemDen,
            @RequestParam(required = false) String khoangGia,
            @RequestParam(required = false) String ngayDi,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return catalog.filter(thanhPho, quocGia, diemDen, khoangGia, ngayDi, sort, page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChuyenDi> byId(@PathVariable int id) {
        ChuyenDi t = catalog.detail(id);
        return t == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(t);
    }

    @GetMapping("/{id}/lich-trinh")
    public List<LichTrinh> schedule(@PathVariable int id) {
        return lichTrinhRepository.findByTourIdOrderByNgayThuAsc(id);
    }

    @GetMapping("/{id}/ngay-khoi-hanh")
    public List<NgayKhoiHanh> departures(
            @PathVariable int id,
            @RequestParam int month,
            @RequestParam int year) {
        return ngayKhoiHanhRepository.findByChuyenDi_IdAndThangAndNam(id, month, year);
    }

    @GetMapping("/{id}/cho")
    public ResponseEntity<QuanLyCho> seats(@PathVariable int id) {
        return ResponseEntity.of(quanLyChoRepository.findByIdChuyenDi_Id(id));
    }
}
