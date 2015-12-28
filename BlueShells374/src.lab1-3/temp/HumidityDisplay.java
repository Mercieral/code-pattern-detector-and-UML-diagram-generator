package temp;

public class HumidityDisplay implements ISubscriber {

	@Override
	public void handleChange(IWeatherData d) {
		System.out.format("Humidity: %.2f%n", d.getHumidity());
	}
}
