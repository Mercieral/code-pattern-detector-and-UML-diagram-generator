package problem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PrintBack implements IObserver {

	@Override
	public void handleDirectoryEvent(IWatcher watcher) {
		if (watcher.getData().getExtension().equals("txt")){
			String file = watcher.getData().getFile().toString();
			FileReader filereader;
			try {
				filereader = new FileReader(file);
				BufferedReader buffer = new BufferedReader(filereader);
				StringBuilder finalString = new StringBuilder();
				String line = buffer.readLine();
				
				while(line != null){
					finalString.append(line);
					line = buffer.readLine();
				}
				
				finalString.reverse();
				System.out.println(finalString.toString());
				buffer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
