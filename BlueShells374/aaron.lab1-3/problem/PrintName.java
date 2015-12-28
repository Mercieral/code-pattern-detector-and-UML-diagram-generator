package problem;

public class PrintName implements IObserver {

	@Override
	public void handleDirectoryEvent(IWatcher watcher) {
		System.out.println(watcher.getData().getFile().toString());
		
	}

}
