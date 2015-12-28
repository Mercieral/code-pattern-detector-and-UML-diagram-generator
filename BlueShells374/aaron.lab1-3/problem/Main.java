package problem;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		// Register directory to the launcher
		Path dir = Paths.get("./input_output");
		
		Map<String, IAppTypes> launchmap = new HashMap<String, IAppTypes>();
		launchmap.put("txt", new TXT());
		launchmap.put("html", new HTML());
		launchmap.put("htm", new HTML());
		launchmap.put("png", new PNG());
		
		Watcher watcher = new Watcher(dir);
		
		IObserver applaunch = new AppLauncher(launchmap);
		IObserver printname = new PrintName();
		IObserver printback = new PrintBack();
		
		
		
		watcher.addObserver(applaunch);
		watcher.addObserver(printname);
		watcher.addObserver(printback);
		
		watcher.start();

		System.out.format("Launcher started watching %s ...%nPress the return key to stop ...", dir);

		// Wait for an input
		System.in.read();

		watcher.stopGracefully();
		watcher.join();

		System.out.println("Directory watching stopped ...");
	}
}
