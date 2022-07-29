package cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Token {
    private final UUID uuid;

    public Token() {
        uuid = UUID.randomUUID();
    }

    public Token(String uuid) {
        this.uuid = UUID.fromString(uuid);
    }

    @JsonProperty("token")
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        return uuid != null ? uuid.equals(token.uuid) : token.uuid == null;
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }
}
