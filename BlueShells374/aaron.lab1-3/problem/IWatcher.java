package problem;

import java.io.IOException;

public interface IWatcher extends Runnable{
	public void addObserver(IObserver s);
	public void removeObserver(IObserver s);
	public void notifyObservers();
	public void setData(IData data);
	public IData getData();
	public void run();
	public void stopGracefully() throws IOException ;
	public void addProcess(Process p);
	public boolean isRunning();
	public int getApplicationsCount();
}
