package edu.bookingtour.svc.contact.repo;

import edu.bookingtour.svc.contact.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {}
