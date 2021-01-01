package com.packtpub.gwtbook.samples.server;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.packtpub.gwtbook.samples.client.LogSpyService;

public class LogSpyServiceImpl extends RemoteServiceServlet implements
		LogSpyService {

	private long filePointer = 0;

	private File logfile = new File("test2.log");

	public ArrayList getAllLogEntries() {
		return readLogFile();
	}

	private ArrayList readLogFile() {
		ArrayList entries = new ArrayList();

		try {
			RandomAccessFile file = new RandomAccessFile(logfile, "r");

			long fileLength = logfile.length();
			if (fileLength > filePointer) {
				file.seek(filePointer);
				String line = file.readLine();
				while (line != null) {
					line = file.readLine();
					if (line != null && line.length() > 0) {
						entries.add(line);
					}
				}
				filePointer = file.getFilePointer();
			}

			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entries;
	}

	public ArrayList getNextLogEntries() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return readLogFile();
	}
}