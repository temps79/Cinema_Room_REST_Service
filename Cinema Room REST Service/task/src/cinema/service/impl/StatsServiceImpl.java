package cinema.service.impl;

import cinema.entity.Seat;
import cinema.entity.Seats;
import cinema.entity.Security;
import cinema.rest.e.StatsResponse;
import cinema.service.StatsService;
import cinema.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final Security security;
    private final TicketService ticketService;
    private final Seats seats;

    @Override
    public ResponseEntity<?> stats(MultiValueMap<String, String> paramMap) {
        Security security = paramMap != null ? new Security(paramMap) : new Security();
        if (!security.equals(this.security)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "The password is wrong!"));
        }

        Integer currentIncome = ticketService.getListSeat().stream()
                .map(Seat::getPrice).collect(Collectors.toList())
                .stream().reduce(0, Integer::sum);
        int numberOfAvailableSeats = seats.getAvailableSeats().size();
        int numberOfPurchasedTickets = ticketService.size();
        return ResponseEntity.ok(new StatsResponse()
                .setCurrentIncome(currentIncome)
                .setNumberOfAvailableSeats(numberOfAvailableSeats)
                .setNumberOfPurchasedTickets(numberOfPurchasedTickets)
        );
    }

    @Override
    public ResponseEntity<?> stats(Security security) {
        return stats(security != null ? security.toMap() : new Security().toMap());
    }
}
