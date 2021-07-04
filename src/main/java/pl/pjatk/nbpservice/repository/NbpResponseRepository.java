package pl.pjatk.nbpservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pjatk.nbpservice.model.NbpResponse;

public interface NbpResponseRepository extends JpaRepository<NbpResponse, Long> {
}
