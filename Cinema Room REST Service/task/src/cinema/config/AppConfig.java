package cinema.config;

import cinema.entity.Seats;
import cinema.entity.Security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Seats seats(){
        return new Seats(9, 9);
    }
    @Bean
    public Security security(){
        return new Security( "super_secret");
    }
}
