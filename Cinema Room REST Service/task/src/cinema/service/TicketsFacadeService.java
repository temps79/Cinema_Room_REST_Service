package cinema.service;

import cinema.entity.Seat;
import cinema.entity.Seats;
import cinema.entity.Token;
import org.springframework.http.ResponseEntity;

public interface TicketsFacadeService {
    ResponseEntity<?> purchase(Seat seat);

    ResponseEntity<Seats> list();

    ResponseEntity<?> returned(Token token);
}
