package nl.daan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HelloRunnable implements Runnable {

	public void run() {
		System.out.println("Starting listen thread..");
		try {
			open();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Unable to start listen thread for whatever reason");
			e.printStackTrace();
		}
	}
	public void open() throws IOException, ClassNotFoundException {
		ServerSocket listener = new ServerSocket(9090);
		try {
			while (true) {
				Socket socket = listener.accept();
				try {
					BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					while ( true) { 
						String answer = (String) ois.readObject();
						//PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
						//out.println(answer);
						if(!answer.equals("exit")) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), answer);
						}
						else {
							socket.close();
						}
					}
				} finally {
					socket.close();
				}
			}
		} finally {
			listener.close();
		}
	}
}