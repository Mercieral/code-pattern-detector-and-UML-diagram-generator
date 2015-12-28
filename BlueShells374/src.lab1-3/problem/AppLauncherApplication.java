package problem;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AppLauncherApplication {
	// Setting up the logger for the application
	public static final String IN_OUT_DIR = "input_output";
	public static final String LOG_FILE = IN_OUT_DIR + "/" + "monitor.log";

	public static void main(String[] args) throws IOException, InterruptedException {
		// Register directory to be monitored
		Path dir = Paths.get(IN_OUT_DIR);
		
		// Let's create a directory monitor service to monitor the input_output dir
		IDirectoryMonitorService monitorService = new DirectoryMonitorService(dir);
		
		// Let's setup the launcher for exe, txt, htm, and html files
		IApplicationLauncher launcher = new ApplicationLauncher();
		
		// exe runner setup
		ProcessRunner exeRunner = new ExecutableFileRunner("");
		launcher.addRunner("exe", exeRunner);
		
		// Web page viewer setup
		ProcessRunner webPageViewer = new DataFileRunner("explorer");
		launcher.addRunner("htm", webPageViewer);
		launcher.addRunner("html", webPageViewer);
		
		// Text file viewer setup
		ProcessRunner txtViewer = new DataFileRunner("Notepad");
		launcher.addRunner("txt", txtViewer);
		
		// Lets register the launcher to the directory monitor service to receive notifications
		monitorService.addListener(launcher);
		
		// Add a custom directory change listener
		IDirectoryListener logger = new DirectoryChangeLogger(Paths.get(LOG_FILE));
		monitorService.addListener(logger);
		
		Thread worker = new Thread(monitorService);
		worker.start();

		System.out.println("Press the return key to stop the application ...");

		// Wait for the return key
		System.in.read();

		System.out.println("Application attempting to terminate gracefully ...");

		monitorService.stopGracefully();
		worker.join();

		System.out.println("Launcher stopped successfully ...");
	}
}
