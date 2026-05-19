package edu.bookingtour.svc.contact.api;

import edu.bookingtour.svc.contact.domain.Contact;
import edu.bookingtour.svc.contact.repo.ContactRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactRestController {

    public record ContactRequest(
            @NotBlank String name,
            @NotBlank String email,
            String phoneNumber,
            String tittle,
            @NotBlank String content,
            Integer guestNumber,
            String address,
            String type) {}

    private final ContactRepository contacts;

    public ContactRestController(ContactRepository contacts) {
        this.contacts = contacts;
    }

    @PostMapping("/submissions")
    @ResponseStatus(HttpStatus.CREATED)
    public Contact submit(@Valid @RequestBody ContactRequest req) {
        Contact c = new Contact();
        c.setName(req.name());
        c.setEmail(req.email());
        c.setPhoneNumber(req.phoneNumber());
        c.setTittle(req.tittle());
        c.setContent(req.content());
        c.setGuestNumber(req.guestNumber());
        c.setAddress(req.address());
        c.setType(req.type());
        return contacts.save(c);
    }

    @GetMapping("/submissions")
    public List<Contact> list(@RequestParam(defaultValue = "50") int limit) {
        return contacts.findAll(Sort.by(Sort.Direction.DESC, "id")).stream().limit(limit).toList();
    }
}
