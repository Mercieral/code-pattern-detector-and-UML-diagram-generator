package temp;

public class WeatherApp {

	public static void main(String[] args) {
		IWeatherReporter reporter = new WeatherReporter();
		
		ISubscriber temperatureDisplay = new TemperatureDisplay();
		ISubscriber humidityDisplay = new HumidityDisplay();
		
		reporter.addSubscriber(temperatureDisplay);
		reporter.addSubscriber(humidityDisplay);
		
		IWeatherData firstData = new WeatherData(10,10,10);
		reporter.setData(firstData);
		
		IWeatherData secondData = new WeatherData(20,20,20);
		reporter.setData(secondData);
		
		reporter.removeSubscriber(humidityDisplay);
		IWeatherData thirdData = new WeatherData(30,30,30);
		reporter.setData(thirdData);
	}
}
