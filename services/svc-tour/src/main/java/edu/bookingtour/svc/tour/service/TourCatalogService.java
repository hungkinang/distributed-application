package edu.bookingtour.svc.tour.service;

import edu.bookingtour.svc.tour.domain.ChuyenDi;
import edu.bookingtour.svc.tour.repo.ChuyenDiRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TourCatalogService {

    private final ChuyenDiRepository chuyenDiRepository;

    public TourCatalogService(ChuyenDiRepository chuyenDiRepository) {
        this.chuyenDiRepository = chuyenDiRepository;
    }

    public Page<ChuyenDi> active(int page, int size) {
        return chuyenDiRepository.findByNgayKetThucAfter(LocalDate.now(), PageRequest.of(page, size));
    }

    public Page<ChuyenDi> finished(int page, int size) {
        return chuyenDiRepository.findByNgayKetThucBefore(LocalDate.now(), PageRequest.of(page, size));
    }

    public Page<ChuyenDi> filter(
            String thanhPho,
            String quocGia,
            String diemDen,
            String khoangGia,
            String ngayDi,
            String sort,
            int page,
            int size) {
        LocalDate date = (ngayDi == null || ngayDi.isBlank()) ? null : LocalDate.parse(ngayDi);
        BigDecimal minGia = null;
        BigDecimal maxGia = null;
        if (khoangGia != null) {
            BigDecimal five = BigDecimal.valueOf(5_000_000);
            BigDecimal ten = BigDecimal.valueOf(10_000_000);
            switch (khoangGia) {
                case "DUOI5" -> {
                    minGia = BigDecimal.ZERO;
                    maxGia = five;
                }
                case "5_10" -> {
                    minGia = five;
                    maxGia = ten;
                }
                case "TREN10" -> {
                    minGia = ten;
                    maxGia = BigDecimal.valueOf(Long.MAX_VALUE);
                }
            }
        }
        Sort sortOpt = Sort.unsorted();
        if ("priceAsc".equals(sort)) {
            sortOpt = Sort.by("gia").ascending();
        } else if ("priceDesc".equals(sort)) {
            sortOpt = Sort.by("gia").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortOpt);
        return chuyenDiRepository.filterTour(
                empty(thanhPho), empty(quocGia), empty(diemDen), date, minGia, maxGia, pageable);
    }

    private String empty(String s) {
        return s == null || s.isBlank() ? null : s;
    }

    public ChuyenDi detail(int id) {
        return chuyenDiRepository.findById(id).orElse(null);
    }
}
