package de.blaumeise03.homeWarp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class HomeWarp extends JavaPlugin {

    private static List<de.blaumeise03.homeWarp.Command> commands = new ArrayList<>();

    public static Plugin plugin;

    private static FileConfiguration configuration;
    private static File confF;

    @Override
    public void onEnable() {
        getLogger().info("Starting HomeWarp...");
        getLogger().info("HomeWarp by Blaumeise03");
        plugin = this;


        getLogger().info("Initializing Commands...");
        commands.add(new de.blaumeise03.homeWarp.Command("home-help", "Ruft die Hilfe auf.") {
            @Override
            public void onCommand(String[] args, CommandSender sender) {
                StringBuilder builder = new StringBuilder();
                builder.append("§6HomeWarp Hilfe:\n");
                for(de.blaumeise03.homeWarp.Command c : commands){
                    builder.append("§6").append(c.getLabel()).append("§a : ").append(c.getHelp()).append("\n");
                }
                sender.sendMessage(builder.toString());
            }
        });
        commands.add(new de.blaumeise03.homeWarp.Command("setHome", "Setzte deinen Homepunkt!") {
            @Override
            public void onCommand(String[] args, CommandSender sender) {
                if(sender instanceof Player){
                    Location loc = ((Player) sender).getLocation();
                    configuration.set("Warps." + ((Player) sender).getUniqueId() + ".x", loc.getX());
                    configuration.set("Warps." + ((Player) sender).getUniqueId() + ".y", loc.getY());
                    configuration.set("Warps." + ((Player) sender).getUniqueId() + ".z", loc.getZ());
                    configuration.set("Warps." + ((Player) sender).getUniqueId() + ".yaw", loc.getYaw());
                    configuration.set("Warps." + ((Player) sender).getUniqueId() + ".pitch", loc.getPitch());
                    configuration.set("Warps." + ((Player) sender).getUniqueId() + ".world", loc.getWorld().getUID().toString());
                    sender.sendMessage("§aHomepunkt gesetzt!");
                }else sender.sendMessage("Error! Executor must be a Player!");
            }
        });
        commands.add(new de.blaumeise03.homeWarp.Command("home", "Teleportiert dich nach Hause.") {
            @Override
            public void onCommand(String[] args, CommandSender sender) {
                if(sender instanceof Player) {
                    double x = configuration.getDouble("Warps." + ((Player) sender).getUniqueId() + ".x");
                    double y = configuration.getDouble("Warps." + ((Player) sender).getUniqueId() + ".y");
                    double z = configuration.getDouble("Warps." + ((Player) sender).getUniqueId() + ".z");
                    float yaw = (float) configuration.getDouble("Warps." + ((Player) sender).getUniqueId() + ".yaw");
                    float pitch = (float) configuration.getDouble("Warps." + ((Player) sender).getUniqueId() + ".pitch");
                    String worldUUID = configuration.getString("Warps." + ((Player) sender).getUniqueId() + ".world");

                    World world = Bukkit.getWorld(UUID.fromString(worldUUID));
                    //getLogger().info(world.getName());
                    Location warp = new Location(world,x, y, z, yaw ,pitch);
                    //Objects.requireNonNull(warp.getWorld()).loadChunk(warp.getChunk());
                    ((Player) sender).teleport(warp);
                    sender.sendMessage("§aDu wurdest zu deinem Home teleportiert!");
                }else sender.sendMessage("Error! Executor must be a Player!");
            }
        });

        getLogger().info("Setting up Configs...");
        createConfigs();

        getLogger().info("Enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Stopping HomeWarp...");

        getLogger().info("Saving Configs...");
        saveConfigs();

        getLogger().info("Goodbye!");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return super.onTabComplete(sender, command, alias, args);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (de.blaumeise03.homeWarp.Command c : commands){
            if(label.equalsIgnoreCase(c.getLabel())){
                c.onCommand(args, sender);
                return true;
            }
        }
        return false;
    }

    private void createConfigs(){
        confF = new File(getDataFolder(), "config.yml");
        if(!confF.exists()){
            confF.getParentFile().mkdirs();
            try {
                confF.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveResource("config.yml", false);
        }
        configuration = new YamlConfiguration();
        try {
            configuration.load(confF);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    private void saveConfigs(){
        try {
            configuration.save(confF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reloadConfigs(){
        createConfigs();
    }
}
