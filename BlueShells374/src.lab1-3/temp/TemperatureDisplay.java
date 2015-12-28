package temp;

public class TemperatureDisplay implements ISubscriber {

	@Override
	public void handleChange(IWeatherData d) {
		System.out.format("Temperature: %.2f%n", d.getTemperature());
	}

}
