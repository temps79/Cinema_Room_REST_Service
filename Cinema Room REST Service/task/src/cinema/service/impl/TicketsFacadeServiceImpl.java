package cinema.service.impl;

import cinema.entity.Seat;
import cinema.entity.Seats;
import cinema.entity.Security;
import cinema.entity.Token;
import cinema.rest.e.ErrorResponse;
import cinema.rest.e.PurchaseResponse;
import cinema.rest.e.ReturnedResponse;
import cinema.service.TicketService;
import cinema.service.TicketsFacadeService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketsFacadeServiceImpl implements TicketsFacadeService {
    private final Seats seats;
    private final TicketService ticketService;

    @Override
    public ResponseEntity<?> purchase(Seat seat) {
        if (seat.getRow() > seats.getTotalRows() ||
                seat.getColumn() > seats.getTotalColumns() ||
                seat.getColumn() < 1 ||
                seat.getRow() < 1
        ) {
            return ResponseEntity.badRequest().body(new ErrorResponse("The number of a row or a column is out of bounds!"));
        }

        Seat blockSeat = seats.getAvailableSeats()
                .stream().filter(seat1 -> seat1.getRow() == seat.getRow() && seat1.getColumn() == seat.getColumn())
                .findFirst().orElse(null);
        if (blockSeat != null) {
            blockSeat.setActive(false);
        } else {

            return ResponseEntity.badRequest().body(new ErrorResponse("The ticket has been already purchased!"));
        }
        Token token = new Token();
        ticketService.addTicket(token, blockSeat);
        return ResponseEntity.ok(new PurchaseResponse(blockSeat, token.getUuid()));
    }

    @Override
    public ResponseEntity<Seats> list() {
        return ResponseEntity.status(HttpStatus.OK).body(seats);
    }

    @Override
    public ResponseEntity<?> returned(Token token) {
        Seat seat = ticketService.remove(token);

        if (seat == null) return ResponseEntity.badRequest().body(new ErrorResponse("Wrong token!"));
        seat.setActive(true);
        return ResponseEntity.ok(new ReturnedResponse(seat));
    }
}
