MyWeatherApp
MyWeatherApp is a full-stack application built using React.js for the frontend and Spring Boot for the backend. It provides hourly weather forecasts for five different locations: Seattle, Detroit, Dallas, New York, and Chicago.

Features
Hourly Weather Forecast: Displays hourly temperature forecasts for the selected location.
Location Selection: Allows users to select a location from a dropdown menu to view weather data.
Backend Integration: Fetches weather data from the Open Meteo API based on the selected location's coordinates.
Backend (Spring Boot)
Controller
The backend utilizes a Spring Boot application with a WeatherController that handles API requests to fetch weather forecasts. Here's a brief overview:

Endpoint: /api/weather/forecastByLocation
Coordinates: Pre-defined latitude and longitude values for the five locations.
API Integration: Utilizes the Open Meteo API to fetch hourly weather data based on coordinates.
Frontend (React.js)
WeatherChart Component
The frontend is developed using React.js, featuring a WeatherChart component that:

Location Dropdown: Provides a dropdown menu to select one of the five locations.
Data Fetching: Fetches weather data based on the selected location using a custom API function.
Display: Presents the hourly temperature forecast in a table format.
Setup Instructions
To run the application locally:

Backend Setup:

Clone the repository.
Navigate to the backend directory and run the Spring Boot application.
Ensure the backend is running on http://localhost:8080.
Frontend Setup:

Navigate to the frontend directory.
Install dependencies using npm install.
Start the React development server using npm start.
Access the application at http://localhost:3000.
Usage
Open the MyWeatherApp frontend in your web browser.
Select a location from the dropdown menu (Seattle, Detroit, Dallas, New York, or Chicago).
View the hourly temperature forecast displayed in a table format.
