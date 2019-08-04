package de.blaumeise03.homeWarp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class HomeWarp extends JavaPlugin {
    private static List<de.blaumeise03.homeWarp.Command> commands = new ArrayList<>();

    @Override
    public void onEnable() {
        getLogger().info("Starting HomeWarp...");

        getLogger().info("Initializing Commands...");
        commands.add(new de.blaumeise03.homeWarp.Command("home-help", "Ruft die Hilfe auf.") {
            @Override
            public void onCommand(String[] args, CommandSender sender) {
                StringBuilder builder = new StringBuilder();
                builder.append("§6HomeWarp Hilfe:\n");
                for(de.blaumeise03.homeWarp.Command c : commands){
                    builder.append("§4").append(c.getLabel()).append("§4 : ").append(c.getHelp());
                }
                sender.sendMessage(builder.toString());
            }
        });
        commands.add(new de.blaumeise03.homeWarp.Command("setHome", "Setzte deinen Homepunkt!") {
            @Override
            public void onCommand(String[] args, CommandSender sender) {
                sender.sendMessage("test");
            }
        });
        commands.add(new de.blaumeise03.homeWarp.Command("home", "Teleportiert dich nach Hause.") {
            @Override
            public void onCommand(String[] args, CommandSender sender) {
                sender.sendMessage("test2");
            }
        });
    }

    @Override
    public void onDisable() {
        getLogger().info("Stopping HomeWarp...");
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
}
