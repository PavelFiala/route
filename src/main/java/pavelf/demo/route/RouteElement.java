package pavelf.demo.route;

class RouteElement {
    private final RouteElement previousElement;
    private final Country country;

    RouteElement(RouteElement previousElement, Country country) {
        this.previousElement = previousElement;
        this.country = country;
    }

    public RouteElement getPreviousElement() {
        return previousElement;
    }

    public Country getCountry() {
        return country;
    }

    @Override public String toString() {
        return "RouteElement" + previousElement + ", " + country.getCountryCode() + '}';
    }
}
