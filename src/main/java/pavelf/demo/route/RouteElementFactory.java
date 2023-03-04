package pavelf.demo.route;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class RouteElementFactory {
    private final Map<String, Country> countries;
    private final Set<String> usedCodes = new HashSet<>();

    public RouteElementFactory(Map<String, Country> countries) {
        this.countries = countries;
    }

    public Optional<RouteElement> createElement(RouteElement previousElement, String countryCode) {
        Country country = countries.get(countryCode);
        if (country != null && usedCodes.add(countryCode)) {
            // create a node only if the country code is valid and has not been used yet
            return Optional.of(new RouteElement(previousElement, country));
        }
        return Optional.empty();
    }
}
