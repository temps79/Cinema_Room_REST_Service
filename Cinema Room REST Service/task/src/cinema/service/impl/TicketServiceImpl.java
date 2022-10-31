package cinema.service.impl;

import cinema.entity.Seat;
import cinema.entity.Token;
import cinema.repository.TicketsRepository;
import cinema.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketsRepository ticketsRepository;

    @Override
    public Seat getTicket(Token token) {
        return ticketsRepository.get(token);
    }

    @Override
    public void addTicket(Token token, Seat seat) {
        ticketsRepository.add(token, seat);
    }

    @Override
    public Collection<Seat> getListSeat() {
        return ticketsRepository.getListSeat();
    }

    @Override
    public int size() {
        return ticketsRepository.size();
    }

    @Override
    public Seat remove(Token token) {
        return ticketsRepository.remove(token);
    }
}
