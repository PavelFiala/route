package pavelf.demo.route;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.*;

class RouteFinder {
    private final String destination;
    private final RouteElementFactory routeElementFactory;
    private RouteElement lastRouteElement;

    public RouteFinder(Map<String, Country> countries, String origin, String destination) {
        this.destination = destination;
        this.routeElementFactory = new RouteElementFactory(countries);
        routeElementFactory.createElement(null, origin).ifPresent(routeElement -> {
            if (origin.equals(destination)) {
                if (countries.containsKey(origin)) {
                    lastRouteElement = routeElement;
                }
            } else {
                Set<RouteElement> elements = Collections.singleton(routeElement);
                do {
                    elements = findNextRouteElements(elements);
                } while (!elements.isEmpty());
            }
        });
    }

    private Set<RouteElement> findNextRouteElements(Set<RouteElement> previousElements) {
        Set<RouteElement> elements = new HashSet<>();
        for (RouteElement previousElement : previousElements) {
            for (String countryCode : previousElement.getCountry().getBorders()) {
                Optional<RouteElement> optionalRouteElement =
                        routeElementFactory.createElement(previousElement, countryCode);
                if (optionalRouteElement.isPresent()) {
                    RouteElement element = optionalRouteElement.get();
                    elements.add(element);
                    if (countryCode.equals(destination)) {
                        // we have found the shortest route to the destination, so we are done
                        lastRouteElement = element;
                        return Collections.emptySet();
                    }
                }
            }
        }
        return elements;
    }

    public String getRoute() {
        List<String> route = new LinkedList<>();
        for (RouteElement element = lastRouteElement; element != null; element = element.getPreviousElement()) {
            // create route in reverse order
            route.add(0, element.getCountry().getCountryCode());
        }
        JsonNodeFactory nodeFactory = new ObjectMapper().getNodeFactory();
        ObjectNode jsonNodes = nodeFactory.objectNode();
        ArrayNode array = jsonNodes.putArray("route");
        route.forEach(array::add);
        return jsonNodes.toPrettyString();
    }
}
