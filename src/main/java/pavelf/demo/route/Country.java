package pavelf.demo.route;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

class Country {
    @JsonProperty("cca3")
    private String countryCode;

    @JsonProperty("borders")
    private String[] borders;

    public String getCountryCode() {
        return countryCode;
    }

    public String[] getBorders() {
        return borders;
    }

    @Override public String toString() {
        return "Country{countryCode='" + countryCode + '\'' + ", borders=" + Arrays.toString(borders) + '}';
    }
}
