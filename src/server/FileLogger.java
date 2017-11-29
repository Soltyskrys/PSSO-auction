package server;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLogger {
	protected DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	protected PrintWriter logger;
	
	@Override
	protected void finalize() throws Throwable {
		logger.close();
		super.finalize();
	}

	public FileLogger(String fileName) {
		try {	
			FileWriter fOut = new FileWriter(fileName, true);
			logger = new PrintWriter(fOut);
		} catch (IOException e) {
			System.out.println("Logging failed!");
		}		
	}	

	public void log(String string) {			
		if (logger != null) {
			Date date = new Date();
			logger.println(dateFormat.format(date) + " " + string);
			logger.flush();
		}
	}
}
