package temp;

public interface IWeatherReporter {
	public void addSubscriber(ISubscriber s);
	public void removeSubscriber(ISubscriber s);
	public void setData(IWeatherData d);
	public IWeatherData getData();
}
