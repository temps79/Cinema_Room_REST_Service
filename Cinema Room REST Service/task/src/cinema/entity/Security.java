package cinema.entity;

import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Security {
    private String password;

    public Security(String password) {
        this.password = password;
    }

    public Security() {
        password="bad_password";
    }
    public Security(MultiValueMap<String,String> paramMap) {
        this.password=paramMap.get("password").stream().findFirst().orElse("");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MultiValueMap<String,String> toMap(){
        return new MultiValueMapAdapter<>(Map.of("password", Collections.singletonList(password)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Security security = (Security) o;

        return password != null ? password.equals(security.password) : security.password == null;
    }

    @Override
    public int hashCode() {
        return password != null ? password.hashCode() : 0;
    }
}
