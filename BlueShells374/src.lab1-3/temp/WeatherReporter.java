package temp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeatherReporter implements IWeatherReporter {
	private List<ISubscriber> subscribers;
	private IWeatherData data;
	
	
	public WeatherReporter() {
		subscribers = Collections.synchronizedList(new ArrayList<ISubscriber>());
		data = null;
	}

	@Override
	public void addSubscriber(ISubscriber s) {
		this.subscribers.add(s);
	}

	@Override
	public void removeSubscriber(ISubscriber s) {
		this.subscribers.remove(s);
	}
	
	protected void notifyObservers() {
		for(ISubscriber s : this.subscribers) {
			s.handleChange(this.data);
		}
	}

	@Override
	public void setData(IWeatherData d) {
		this.data = d;
		this.notifyObservers();
	}

	@Override
	public IWeatherData getData() {
		return this.data;
	}
}
