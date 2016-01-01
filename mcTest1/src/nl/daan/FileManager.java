package nl.daan;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class FileManager {

	public File myPlayerData = new File("plugins/data.yml");
	public FileConfiguration pdata = YamlConfiguration.loadConfiguration(myPlayerData);

	public FileManager() {
		if (myPlayerData.exists()) {
			try {
				pdata.load(myPlayerData);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			pdata.set("waypointAmount", '0');
			pdata.set("waypoints", new ArrayList<String>());
			save();
		}
	}

	public void loadFile() {
		if (myPlayerData.exists()) {
			try {
				pdata.load(myPlayerData);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			save();
			pdata.set("waypointAmount", "0");
			pdata.set("waypoints", "0");
		}
	}

	public void save() {
		try {
			pdata.save(myPlayerData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addWaypoint(String name, Location loc, Player p) {
		pdata.set(name, null);
		String amount = (String) pdata.get("waypointAmount");
		int amountInt = Integer.parseInt(amount);
		amountInt++;
		amount = Integer.toString(amountInt);
		pdata.set("waypointAmount", amount);
		double x = loc.getX();
		double z = loc.getZ();
		double y = loc.getY();
		pdata.set(name + ".DiscoveredBy", new ArrayList<String>());
		pdata.set(name + ".xPos", x);
		pdata.set(name + ".zPos", z);
		pdata.set(name + ".yPos", y);
		List<String> waypoints = pdata.getStringList("waypoints");
		waypoints.add(name);
		pdata.set("waypoints", waypoints);

		List<String> discoveredBy = pdata.getStringList("sup.DiscoveredBy");
		discoveredBy.add(p.getName());
		pdata.set(name + ".DiscoveredBy", discoveredBy);
		p.sendMessage("A waypoint has been created!");
		save();
	}

	public String getWaypoints(Player p) {
		List<String> waypoints = pdata.getStringList("waypoints");
		String result = "Waypoints: ";
		boolean first = true;
		for (String wp : waypoints) {
			if (checkIfDiscovered(p, wp)) {
				if (first) {
					result = result + "" + wp;
					first = false;
				} else {
					result = result + ", " + wp;
				}
			}
		}
		return result;
	}

	public boolean checkIfDiscovered(Player p, String target) {
		List<String> db = pdata.getStringList(target + ".DiscoveredBy");
		if (db.contains(p.getName())) {
			return true;
		}
		return false;
	}

	public void addPlayer(Player p, String target) {
		String name = p.getName();
		List<String> db = pdata.getStringList(target + ".DiscoveredBy");
		db.add(name);
		pdata.set(target + ".DiscoveredBy", db);
		save();
	}

	public Location getLocation(String target, Location location) {
		Double x = (Double) pdata.get(target + ".xPos");
		Double z = (Double) pdata.get(target + ".zPos");
		Double y = (Double) pdata.get(target + ".yPos");
		World w = location.getWorld();
		Location loc = new Location(w, x, y, z);
		return loc;
	}
}