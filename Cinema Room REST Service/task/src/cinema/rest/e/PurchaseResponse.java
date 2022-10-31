package cinema.rest.e;

import cinema.entity.Seat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class PurchaseResponse {
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
