package temp;

public class WeatherData implements IWeatherData {
	private double temperature;
	private double pressure;
	private double humidity;
	
	public WeatherData(double temperature, double pressure, double humidity) {
		this.temperature = temperature;
		this.pressure = pressure;
		this.humidity = humidity;
	}

	@Override
	public double getTemperature() {
		return temperature;
	}

	@Override
	public double getHumidity() {
		return humidity;
	}

	@Override
	public double getPressure() {
		return pressure;
	}
}
