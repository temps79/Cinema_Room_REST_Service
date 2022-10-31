package cinema.rest;

import cinema.entity.Seat;
import cinema.entity.Seats;
import cinema.entity.Token;
import cinema.service.TicketsFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SeatsController {
    private final TicketsFacadeService ticketsFacadeService;

    @GetMapping(value = "/seats")
    public ResponseEntity<Seats> list() {
        return ticketsFacadeService.list();
    }

    @PostMapping(value = "/purchase")
    public ResponseEntity<?> purchase(@RequestBody Seat seat) {
        return ticketsFacadeService.purchase(seat);
    }

    @PostMapping(value = "/return")
    public ResponseEntity<?> returned(@RequestBody Token token) {
        return ticketsFacadeService.returned(token);
    }

}
