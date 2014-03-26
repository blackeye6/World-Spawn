
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.mcstats.Metrics;

public class Main extends JavaPlugin {
    Logger logger = Logger.getLogger("Minecraft");

    @Override
    public void onEnable(){
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            logger.info("Failed to submit Metrics stats.");
            // Failed to submit the stats :-(
        }
    }


    public boolean onCommand(CommandSender sender, Command cmd,
                             String commandLabel, String[] args) {
        String prefix = ChatColor.GRAY + "[" + ChatColor.DARK_AQUA
                + "WorldSpawn" + ChatColor.GRAY + "] ";

        if (commandLabel.equalsIgnoreCase("worldSpawn")
                || commandLabel.equalsIgnoreCase("WS")) {

            if (args.length == 0) {
                sender.sendMessage(ChatColor.DARK_AQUA + "§m---------§r§3["
                        + ChatColor.DARK_AQUA + "World Spawn Help"
                        + ChatColor.DARK_AQUA + "]§m---------");
                sender.sendMessage(ChatColor.DARK_AQUA + "/ws setworldspawn"
                        + ChatColor.GRAY + " Sets the world spawn.");
                sender.sendMessage(ChatColor.DARK_AQUA
                        + "/ws tptospawn <playername>" + ChatColor.GRAY
                        + " Teleports a player to the world spawn.");
                sender.sendMessage(ChatColor.DARK_AQUA + "/ws tospawn"
                        + ChatColor.GRAY + " Teleports you to the world spawn.");
                sender.sendMessage(ChatColor.DARK_AQUA + "/ws about"
                        + ChatColor.GRAY + " Useless information.");
            }

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("setworldspawn")) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("Only players can use this command!");
                    } else {
                        if (sender.hasPermission("worldspawn.set")) {
                            Player player = (Player) sender;
                            Location playerLoc = player.getLocation();
                            Vector vector = player.getLocation().getDirection();
                            playerLoc.setDirection(vector);
                            float X = playerLoc.getBlockX();
                            float Y = playerLoc.getBlockY();
                            float Z = playerLoc.getBlockZ();
                            player.getWorld().setSpawnLocation((int) X,
                                    (int) Y, (int) Z);
                            player.sendMessage(prefix+ ChatColor.GRAY+"World spawn set!");
                        }
                    }
                }

                if (args[0].equalsIgnoreCase("tospawn")) {
                    if (sender instanceof Player) {
                        if(sender.hasPermission("worldspawn.to")){
                            ((Player) sender).teleport(((Player) sender).getWorld()
                                    .getSpawnLocation());
                            sender.sendMessage(prefix
                                    + "Teleported to the World Spawn");
                        }
                    } else {
                        sender.sendMessage(prefix
                                + "Only players can use this command!");
                    }
                }
                if (args[0].equalsIgnoreCase("about")) {
                    sender.sendMessage(prefix
                            + "Created by xTDKx, A.K.A blackeye6");
                }

            }

            if (args.length == 2){
                if (args[0].equalsIgnoreCase("tptospawn")) {
                    if(sender.hasPermission("worldspawn.send")){
                        if (!(args[1] == null)) {
                            if (Bukkit.getServer().getPlayer(args[1]) != null) {
                                Player targetPlayer = Bukkit.getServer().getPlayer(
                                        args[1]);
                                sender.sendMessage(prefix + ChatColor.DARK_AQUA
                                        + "Sent " + targetPlayer.getDisplayName()
                                        + " to the World Spawn.");
                                targetPlayer.teleport(targetPlayer.getWorld()
                                        .getSpawnLocation());
                            } else {
                                sender.sendMessage(prefix + "Player not found!");
                            }

                        } else {
                            sender.sendMessage(prefix+"Incorrect usage: /ws tptospawn <player>.");
                        }

                    }

                } else {
                    sender.sendMessage(prefix + "Use /ws for help.");
                }
            }
            if(args.length > 2){
                sender.sendMessage(prefix+"Use /ws for help.");
            }

        }
        return false;
    }

}
