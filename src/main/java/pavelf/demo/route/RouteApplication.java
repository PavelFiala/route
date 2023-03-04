package pavelf.demo.route;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class RouteApplication {

    @Value("classpath:countries.json")
    Resource countriesFile;
    private Map<String, Country> countries;

    public static void main(String[] args) {
        SpringApplication.run(RouteApplication.class, args);
    }

    @PostConstruct
    void init() throws IOException {
        countries = new HashMap<>();
        ObjectMapper objectMapper =
                new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        for (Country country : objectMapper.readValue(countriesFile.getURL(), Country[].class)) {
            countries.put(country.getCountryCode(), country);
        }
    }


    @GetMapping("/routing/{origin}/{destination}")
    String getRoute(@PathVariable("origin") String origin, @PathVariable("destination") String destination) {
        return new RouteFinder(countries, origin, destination).getRoute();
    }

}
