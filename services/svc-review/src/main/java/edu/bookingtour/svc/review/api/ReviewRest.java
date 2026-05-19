package edu.bookingtour.svc.review.api;
import edu.bookingtour.svc.review.domain.DanhGia;import edu.bookingtour.svc.review.repo.DanhGiaRepository;
import jakarta.validation.constraints.Max;import jakarta.validation.constraints.Min;import jakarta.validation.constraints.NotBlank;import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal; import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@RestController @RequestMapping("/api/reviews")
public class ReviewRest {
  public record CreateRv(@NotNull Integer tourId, @Min(1) @Max(5) int diem, @NotBlank String binhLuan){}
  private final DanhGiaRepository repo; public ReviewRest(DanhGiaRepository r){this.repo=r;}
  @GetMapping("/tour/{tourId}") public List<DanhGia> byTour(@PathVariable int tourId){return repo.findByTourIdOrderByNgayDanhGiaDesc(tourId);}
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public DanhGia post(@AuthenticationPrincipal Jwt j, @RequestBody CreateRv b){
    if(j==null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED); int uid=Integer.parseInt(j.getSubject());
    repo.findAll().stream().filter(x->x.getTourId().equals(b.tourId())&&x.getUserId().equals(uid)).findAny().ifPresent(x->{throw new ResponseStatusException(HttpStatus.CONFLICT,"Đã có đánh giá");});
    DanhGia e=new DanhGia(); e.setTourId(b.tourId()); e.setUserId(uid); e.setDiem(b.diem()); e.setBinhLuan(b.binhLuan()); return repo.save(e);
  }
}
