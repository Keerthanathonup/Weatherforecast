package com.example.WeatherApp.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@CrossOrigin(origins = "http://localhost:3000")

@Controller
@RequestMapping("/api/weather")

public class WeatherController {

 // Store latitude and longitude values for 5 places
 private final Map<String, Coordinates> locationCoordinates = new HashMap<>();

 public WeatherController() {
     // Initialize coordinates for 5 places
     locationCoordinates.put("Seattle", new Coordinates(47.6062, -122.3321));
     locationCoordinates.put("Detroit", new Coordinates(42.3314, -83.0458));
     locationCoordinates.put("Dallas", new Coordinates(32.7767, -96.7970));
     locationCoordinates.put("New York", new Coordinates(40.7128, -74.0060));
     locationCoordinates.put("Chicago", new Coordinates(41.8781, -87.6298));
 }



    @Value("${openmeteo.api.url}")
    private String openMeteoApiUrl;

    @GetMapping("/forecastByLocation")
    public ResponseEntity<String> getWeatherForecastByLocation(@RequestParam String locationName) {


    
        // Look up coordinates based on the selected location
        Coordinates coordinates = locationCoordinates.get(locationName);

        if (coordinates == null) {
            return ResponseEntity.badRequest().body("Invalid location");
        }


        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        String apiUrlWithCoordinates = openMeteoApiUrl + "&latitude=" + coordinates.getLatitude() + "&longitude=" + coordinates.getLongitude(); 
        // Make a GET request to the Open Meteo API
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                apiUrlWithCoordinates,
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {});

        // Extract relevant information
        Map<String, Object> apiResponse = responseEntity.getBody();
        List<String> timeList = (List<String>) ((Map<String, Object>) apiResponse.get("hourly")).get("time");
        List<Double> temperatureList = (List<Double>) ((Map<String, Object>) apiResponse.get("hourly")).get("temperature_2m");

         // Create a JSON-formatted string with the forecast data
         StringBuilder forecastData = new StringBuilder("{\"hourlyForecast\": [");
        for (int i = 0; i < timeList.size(); i++) {
            forecastData.append("{\"time\":\"").append(timeList.get(i)).append("\", \"temperature\":").append(temperatureList.get(i)).append("}");
            if (i < timeList.size() - 1) {
                forecastData.append(",");
            }
        }
         forecastData.append("]}");
 
         return ResponseEntity.ok(forecastData.toString());
     }
     private static class Coordinates {
        private final double latitude;
        private final double longitude;

        public Coordinates(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}   
