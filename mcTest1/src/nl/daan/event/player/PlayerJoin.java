package nl.daan.event.player;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import nl.daan.Main;

public class PlayerJoin implements Listener {
	
	private Main plugin;
	
	public PlayerJoin(Main pl) {
		plugin = pl;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		 Player player = event.getPlayer();
			 if(!player.hasPlayedBefore()) {
				 String gun;
				 gun = plugin.getConfig().getString("Gun");
				 ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				 String command = "shot give "+player.getName()+" "+ gun;
				 Bukkit.dispatchCommand(console, command);
		 }
	}
}
