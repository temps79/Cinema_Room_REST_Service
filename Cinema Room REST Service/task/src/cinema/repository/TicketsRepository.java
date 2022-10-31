package cinema.repository;

import cinema.entity.Seat;
import cinema.entity.Token;

import java.util.Collection;

public interface TicketsRepository {
    Seat get(Token token);

    void add(Token token, Seat seat);

    Collection<Seat> getListSeat();
    int size();
    Seat  remove(Token token);
}
