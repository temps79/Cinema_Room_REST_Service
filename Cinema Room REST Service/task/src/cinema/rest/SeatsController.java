package cinema.rest;

import cinema.entity.Seat;
import cinema.entity.Seats;
import cinema.entity.Security;
import cinema.entity.Token;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class SeatsController {
    private final Seats seats;
    private final Map<Token, Seat> tickets;
    private final Security security;

    public SeatsController() {
        this.seats = Seats.getInstance(9, 9);
        tickets = new HashMap<>();
        security = new Security( "super_secret");
    }

    @GetMapping(value = "/seats")
    public ResponseEntity<Seats> list() {
        return ResponseEntity.status(HttpStatus.OK).body(seats);
    }

    @PostMapping(value = "/purchase")
    public ResponseEntity<?> purchase(@RequestBody Seat seat) {
        if (seat.getRow() > seats.getTotalRows() ||
                seat.getColumn() > seats.getTotalColumns() ||
                seat.getColumn() < 1 ||
                seat.getRow() < 1
        ) {
            return ResponseEntity.badRequest().body(Map.of("error", "The number of a row or a column is out of bounds!"));
        }

        Seat blockSeat = seats.getAvailableSeats()
                .stream().filter(seat1 -> seat1.getRow() == seat.getRow() && seat1.getColumn() == seat.getColumn())
                .findFirst().orElse(null);
        if (blockSeat != null) {
            blockSeat.setActive(false);
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "The ticket has been already purchased!"));
        }
        Token token = new Token();
        tickets.put(token, blockSeat);
        return ResponseEntity.ok(new PurchaseResponse(blockSeat, token.getUuid()));
    }

    @PostMapping(value = "/return")
    public ResponseEntity<?> returned(@RequestBody Token token) {
        Seat seat = tickets.remove(token);
        if (seat == null) return ResponseEntity.badRequest().body(Map.of("error", "Wrong token!"));
        seat.setActive(true);
        return ResponseEntity.ok(Map.of("returned_ticket", seat));
    }

    @PostMapping(value = "/stats" ,consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<?> stats(@RequestBody(required = true) MultiValueMap<String,String> paramMap) {
        Security security=paramMap!=null?new Security(paramMap):new Security();
        if (!security.equals(this.security)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "The password is wrong!"));
        }

        Integer currentIncome = tickets.values().stream()
                .map(Seat::getPrice).collect(Collectors.toList())
                .stream().reduce(0, Integer::sum);
        Integer numberOfAvailableSeats = seats.getAvailableSeats().size() ;
        Integer numberOfPurchasedTickets = tickets.size();
        return ResponseEntity.ok(
                Map.of("current_income", currentIncome,
                        "number_of_available_seats", numberOfAvailableSeats,
                        "number_of_purchased_tickets", numberOfPurchasedTickets)
        );
    }

    @PostMapping(value = "/stats" ,consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> stats(@RequestBody(required = false) Security security) {
        return stats(security!=null?security.toMap():new Security().toMap());
    }

    private static class PurchaseResponse {
        public PurchaseResponse(Seat ticket, UUID token) {
            this.ticket = ticket;
            this.token = token;
        }

        private final Seat ticket;
        private final UUID token;

        @JsonProperty("ticket")
        public Seat getTicket() {
            return ticket;
        }

        @JsonProperty("token")
        public String getToken() {
            return token.toString();
        }
    }

}
