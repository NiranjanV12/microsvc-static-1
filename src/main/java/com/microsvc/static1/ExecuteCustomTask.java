package com.microsvc.static1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;

import lombok.extern.java.Log;

@Log
public class ExecuteCustomTask extends Thread {
	private String task1;
	private int itr;

	public ExecuteCustomTask(String task1, int itr) {
		this.task1 = task1;
		this.itr = itr;
	}

	public void run() {
		String threadName = Thread.currentThread().getName();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		// LocalDateTime localDateTime = LocalDateTime.now();
		for (int i = itr; i >= 0; i--) {

			StringBuffer strbuff = new StringBuffer();
			log.log(Level.INFO, strbuff.append(LocalDateTime.now().format(formatter)).append(" :::: ").append(task1)
					.append(" -> ").append(i).toString());

			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
}