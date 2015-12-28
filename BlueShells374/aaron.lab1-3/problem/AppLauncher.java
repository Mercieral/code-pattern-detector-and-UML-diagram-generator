package problem;

/*
 * Copyright (c) 2008, 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Modified By: 
 * 		Chandan R. Rupakheti
 */

import java.nio.file.*;
import java.util.Map;

import java.io.*;

/**
 * This class has been modified from the original WatchDir program found at: 
 * https://docs.oracle.com/javase/tutorial/essential/io/examples/WatchDir.java.
 * 
 * This program listens to three kind of events in a supplied directory:
 * <ol>
 * 	<li>ENTRY_CREATE - When a file/folder gets created.</li>
 * 	<li>ENTRY_DELETE - When a file/folder gets deleted.</li>
 * 	<li>ENTRY_MODIFY - When a file/folder get modified.</li>
 * </ol>
 * 
 * Based on the event it launches the relevant application through the
 * {@link #handleDirectoryEvent(String, Path)} method.
 * 
 * 
 * @author Chandan R. Rupakheti (chandan.rupakheti@rose-hulman.edu)
 * @author Mark Hays (hays@rose-hulman.edu)
 */
public class AppLauncher implements IObserver{
	
	private Map<String, IAppTypes> launchMap;

	/**
	 * Creates a WatchService and registers the given directory
	 */
	public AppLauncher(Map<String, IAppTypes> map) {
		this.launchMap = map;
	}

	/**
	 * This method gets called when ever the directory being monitored changes.

	 * @param eventName One of the following three strings:
	 * <ol>
	 * 	<li>ENTRY_CREATE - When a file/folder gets created.</li>
	 * 	<li>ENTRY_DELETE - When a file/folder gets deleted.</li>
	 * 	<li>ENTRY_MODIFY - When a file/folder get modified.</li>
	 * </ol>     
	 * @param filePath The file that generate the event
	 * @throws IOException 
	 */
	public void handleDirectoryEvent(IWatcher watcher) {
		String eventName = watcher.getData().getEvent();
		Path file = watcher.getData().getFile();

		// We are only interested in the new files that get dropped into the launcher folder
		if(!eventName.equals("ENTRY_CREATE"))
			return;
		
		String fileName = file.toString();
		System.out.println("Processing " + fileName + "...");
		
		IAppTypes launcher = this.launchMap.get(watcher.getData().getExtension());

		// Run the application if support is available
		try {
			System.out.format("Launching %s ...%n", launcher.getCommand());
			// Start and add the process to the processes list
			Process p = launcher.getProcess(file);
			watcher.addProcess(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}