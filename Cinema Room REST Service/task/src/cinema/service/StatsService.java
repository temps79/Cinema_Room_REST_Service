package cinema.service;

import cinema.entity.Security;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;


public interface StatsService {
    ResponseEntity<?> stats(MultiValueMap<String,String> paramMap);
    ResponseEntity<?> stats(Security security);
}
