package edu.bookingtour.svc.favorite.api;
import edu.bookingtour.svc.favorite.domain.YeuThich;import edu.bookingtour.svc.favorite.repo.YeuThichRepository;
import org.springframework.http.HttpStatus;import org.springframework.security.core.annotation.AuthenticationPrincipal;import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@RestController @RequestMapping("/api/favorites")
public class FavoriteRest {
 private final YeuThichRepository repo; public FavoriteRest(YeuThichRepository r){repo=r;}
 @GetMapping("/me") public List<YeuThich> mine(@AuthenticationPrincipal Jwt j){ int u=Integer.parseInt(j.getSubject()); return repo.findByUserIdOrderByNgayThemDesc(u);}
 @PostMapping("/{tourId}") @ResponseStatus(HttpStatus.CREATED) public YeuThich add(@AuthenticationPrincipal Jwt j,@PathVariable int tourId){
  int u=Integer.parseInt(j.getSubject()); if(repo.findByUserIdAndTourId(u,tourId).isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT,"Đã yêu thích");
  YeuThich y=new YeuThich(); y.setUserId(u); y.setTourId(tourId); return repo.save(y);
 }
 @DeleteMapping("/{tourId}") @ResponseStatus(HttpStatus.NO_CONTENT) public void del(@AuthenticationPrincipal Jwt j,@PathVariable int tourId){
  int u=Integer.parseInt(j.getSubject()); repo.deleteByUserIdAndTourId(u,tourId);
 }
}
