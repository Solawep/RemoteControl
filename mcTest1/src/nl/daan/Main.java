package nl.daan;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import nl.daan.event.player.PlayerJoin;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Main extends JavaPlugin {

	
	public void onEnable() {
		Logger logger = getLogger();
		try {
			open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
       PluginDescriptionFile pdffile = getDescription();
		logger.info(pdffile.getName() + " Has been enabled (V." + pdffile.getVersion() + ")");
	}
	public void onDisable() {
		PluginDescriptionFile pdffile = getDescription();
		Logger logger = getLogger();
		
		logger.info(pdffile.getName() + " Has been disabled (V." + pdffile.getVersion() + ")");
	}
	public void open() throws IOException {
		(new Thread(new HelloRunnable())).start();
	}
	
	public void registerCommands() {
	}
	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(new PlayerJoin(this), this);
	}

}
