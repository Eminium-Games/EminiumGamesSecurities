package fr.eminium.adminauth;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EminiumAdminAuth extends JavaPlugin implements Listener {
    
    private final Set<UUID> authenticatedAdmins = new HashSet<>();
    private final Set<String> adminCommands = new HashSet<>();
    private final String adminPermission = "security.admin";
    @Override
    public void onEnable() {
        // Charger la configuration
        getConfig().options().copyDefaults(true);
        saveConfig();
        
        // Enregistrer les événements
        getServer().getPluginManager().registerEvents(this, this);
        
        // Initialize admin commands that require authentication
        adminCommands.add("gamemode");
        adminCommands.add("give");
        adminCommands.add("tp");
        adminCommands.add("teleport");
        adminCommands.add("ban");
        adminCommands.add("pardon");
        adminCommands.add("kick");
        adminCommands.add("op");
        adminCommands.add("deop");
        adminCommands.add("whitelist");
        adminCommands.add("stop");
        adminCommands.add("reload");
        
        getLogger().info("EminiumAdminAuth has been enabled!");
    }
    
    @Override
    public void onDisable() {
        authenticatedAdmins.clear();
        getLogger().info("EminiumAdminAuth has been disabled!");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (command.getName().equalsIgnoreCase("admin")) {
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "Utilisation: /admin <motdepasse>");
                return true;
            }
            
            // Vérification des espaces dans le mot de passe
            if (args[0].contains(" ")) {
                player.sendMessage(ChatColor.RED + "Erreur : Le mot de passe ne peut pas contenir d'espaces !");
                return true;
            }
            
            // Récupération du mot de passe depuis la configuration
            String password = getConfig().getString("admin-password", "MotDePasse1234!");
            if (args[0].equals(password)) {
                authenticatedAdmins.add(player.getUniqueId());
                player.sendMessage(ChatColor.GREEN + "Authentification réussie en tant qu'administrateur !");
            } else {
                player.sendMessage(ChatColor.RED + "Mot de passe incorrect !");
            }
            return true;
        }
        
        if (command.getName().equalsIgnoreCase("adminlogout")) {
            authenticatedAdmins.remove(player.getUniqueId());
            player.sendMessage(ChatColor.YELLOW + "Vous avez été déconnecté de la session administrateur.");
            return true;
        }
        
        return false;
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Déconnexion automatique lors de la déconnexion du joueur
        authenticatedAdmins.remove(event.getPlayer().getUniqueId());
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission(adminPermission)) {
            authenticatedAdmins.remove(player.getUniqueId());
            player.sendMessage(ChatColor.YELLOW + "=============================================");
            player.sendMessage(ChatColor.YELLOW + "Vous devez vous authentifier pour jouer");
            player.sendMessage(ChatColor.YELLOW + "Utilisez /admin <motdepasse>");
            player.sendMessage(ChatColor.YELLOW + "=============================================");
        }
    }
    
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        
        // Autoriser uniquement la commande /admin et /adminlogout si non authentifié
        if (player.hasPermission(adminPermission) && !authenticatedAdmins.contains(player.getUniqueId())) {
            String message = event.getMessage().toLowerCase();
            if (!message.startsWith("/admin ") && !message.equals("/admin") && !message.equals("/adminlogout")) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "Veuvez vous authentifier avec /admin <motdepasse> avant d'utiliser des commandes.");
            }
        }
    }
    
    // Empêche les mouvements des admins non authentifiés
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission(adminPermission) && !authenticatedAdmins.contains(player.getUniqueId())) {
            // Empêche tout mouvement
            event.setTo(event.getFrom());
        }
    }
    
    // Empêche l'utilisation d'objets
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission(adminPermission) && !authenticatedAdmins.contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }
    
    // Empêche les dégâts
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.hasPermission(adminPermission) && !authenticatedAdmins.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
    
    // Empêche la faim de baisser
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.hasPermission(adminPermission) && !authenticatedAdmins.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
    
    // Empêche le cassage de blocs
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission(adminPermission) && !authenticatedAdmins.contains(player.getUniqueId())) {
            event.setCancelled(true);
            sendAuthMessage(player);
        }
    }
    
    // Empêche le placement de blocs
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission(adminPermission) && !authenticatedAdmins.contains(player.getUniqueId())) {
            event.setCancelled(true);
            sendAuthMessage(player);
        }
    }
    
    // Empêche l'utilisation de la main gauche/clic droit avec des objets
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission(adminPermission) && !authenticatedAdmins.contains(player.getUniqueId())) {
            event.setCancelled(true);
            sendAuthMessage(player);
        }
    }
    
    // Méthode utilitaire pour envoyer le message d'authentification
    private void sendAuthMessage(Player player) {
        player.sendMessage(ChatColor.RED + "Veuillez vous authentifier avec /admin <motdepasse> pour effectuer cette action.");
    }
}
