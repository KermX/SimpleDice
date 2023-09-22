package me.kermx.simpledice;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class SimpleDice extends JavaPlugin implements CommandExecutor {

    private int MAX_SIDES;
    private int MAX_DICE;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        MAX_SIDES = getConfig().getInt("MAX_SIDES");
        MAX_DICE = getConfig().getInt("MAX_DICE");
        getCommand("roll").setExecutor(this);
        getCommand("simpledice").setExecutor(this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + ">>" + ChatColor.GREEN + " SimpleDice enabled successfully");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /roll <sides> <amount>");
            return true;
        }

        int sides;
        int amount;

        try {
            sides = Integer.parseInt(args[0]);
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Invalid arguments. Please use numbers for sides and amount.");
            return true;
        }

        if (sides <= 0 || amount <= 0) {
            sender.sendMessage(ChatColor.RED + "Sides and amount must be positive numbers.");
            return true;
        }
        if (sides > MAX_SIDES){
            sender.sendMessage(ChatColor.RED + "The maximum number of sides is " + MAX_SIDES + "!");
            return true;
        }
        if (amount > MAX_DICE){
            sender.sendMessage(ChatColor.RED + "You can only roll up to " + MAX_SIDES + " dice at a time!");
        }

        Player player = (Player) sender;
        Random random = new Random();
        int total = 0;

        for (int i = 0; i < amount; i++) {
            int roll = random.nextInt(sides) + 1;
            total += roll;
            player.sendMessage(ChatColor.GREEN + "Roll " + (i + 1) + ": " + roll);
        }

        player.sendMessage(ChatColor.DARK_GREEN + "Total: " + total);
        return true;
    }


    public void loadConfig(){
        reloadConfig();
        MAX_SIDES = getConfig().getInt("MAX_SIDES");
        MAX_DICE = getConfig().getInt("MAX_DICE");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + ">>" + ChatColor.RED + " SimpleDice disabled");
    }
}