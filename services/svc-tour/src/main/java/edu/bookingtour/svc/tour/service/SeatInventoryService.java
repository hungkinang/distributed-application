package edu.bookingtour.svc.tour.service;

import edu.bookingtour.commons.exception.BusinessException;
import edu.bookingtour.svc.tour.domain.QuanLyCho;
import edu.bookingtour.svc.tour.repo.QuanLyChoRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeatInventoryService {

    private final QuanLyChoRepository quanLyChoRepository;

    public SeatInventoryService(QuanLyChoRepository quanLyChoRepository) {
        this.quanLyChoRepository = quanLyChoRepository;
    }

    @Transactional
    public void reserveSeats(int chuyenDiId, int quantity) {
        QuanLyCho inventory = quanLyChoRepository.findByIdChuyenDi_Id(chuyenDiId)
                .orElseThrow(() -> new BusinessException("TOUR_NOT_FOUND", "Không tìm thấy chỗ cho tour"));

        int remaining = inventory.getConLai() == null ? 0 : inventory.getConLai();
        if (remaining < quantity) {
            throw new BusinessException("SEATS_UNAVAILABLE", "Không đủ chỗ trống cho tour này");
        }

        int booked = inventory.getDaDat() == null ? 0 : inventory.getDaDat();
        inventory.setDaDat(booked + quantity);
        inventory.setConLai(remaining - quantity);

        try {
            quanLyChoRepository.save(inventory);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new BusinessException(
                    "CONCURRENT_BOOKING",
                    "Tour đang được đặt bởi người khác, vui lòng thử lại");
        }
    }
}
