package cinema.service;

import cinema.entity.Seat;
import cinema.entity.Token;

import java.util.Collection;

public interface TicketService {
    Seat getTicket(Token token);

    void addTicket(Token token, Seat seat);
    Collection<Seat> getListSeat();
    int size();
    Seat  remove(Token token);
}
