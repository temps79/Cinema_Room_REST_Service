package cinema.repository.impl;

import cinema.entity.Seat;
import cinema.entity.Token;
import cinema.repository.TicketsRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TicketsRepositoryInMemory implements TicketsRepository {
    private final Map<Token, Seat> tickets;

    public TicketsRepositoryInMemory() {
        this.tickets = new HashMap<>();
    }

    @Override
    public Seat get(Token token) {
        return tickets.get(token);
    }

    @Override
    public void add(Token token, Seat seat) {
        tickets.put(token, seat);
    }

    @Override
    public Collection<Seat> getListSeat() {
        return tickets.values();
    }

    @Override
    public int size() {
        return tickets.size();
    }

    @Override
    public Seat remove(Token token) {
        return tickets.remove(token);
    }
}
