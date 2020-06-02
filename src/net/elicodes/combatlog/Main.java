package net.elicodes.combatlog;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.elicodes.combatlog.listeners.PlayerChat;
import net.elicodes.combatlog.listeners.PlayerDamager;
import net.elicodes.combatlog.listeners.PlayerDisconnect;
import net.elicodes.combatlog.listeners.PlayerTeleport;

public class Main extends JavaPlugin {
	public static Plugin plugin;
	public static ArrayList<String> allow;
	public static String Entrou_em_comabate;
	public static String Provocou_o_combate;
	public static String Deny_Cmd;
	public static String Deny_EnderPearl;
	public static String Batalha_finalizada;
	public static String Broad_cast;
	public static String In_combat;
	public static boolean Block_Chat;
	public static boolean Block_EnderPearl;
	public static int Combat_time;

	static {
		allow = new ArrayList<String>();
	}

	public void onEnable() {
		initAll();
	}

	public void onDisable() {
		super.onDisable();
	}

	public void initAll() {
		((Main) (plugin = (Plugin) this)).registerEvents();
		this.saveDefaultConfig();
		this.loadParameters();
	}

	private void registerEvents() {
		Bukkit.getServer().getPluginManager().registerEvents((Listener) new PlayerTeleport(), (Plugin) this);
		Bukkit.getServer().getPluginManager().registerEvents((Listener) new PlayerDamager(), (Plugin) this);
		Bukkit.getServer().getPluginManager().registerEvents((Listener) new PlayerDisconnect(), (Plugin) this);
		Bukkit.getServer().getPluginManager().registerEvents((Listener) new PlayerChat(), (Plugin) this);
	}

	private void loadParameters() {
		Entrou_em_comabate = this.getConfig().getString("Mensagens.Entrou_em_comabate").replace("&", "§");
		Provocou_o_combate = this.getConfig().getString("Mensagens.Provocou_o_combate").replace("&", "§");
		Batalha_finalizada = this.getConfig().getString("Mensagens.Batalha_finalizada").replace("&", "§");
		Deny_Cmd = this.getConfig().getString("Mensagens.Deny_Cmd").replace("&", "§");
		Deny_EnderPearl = this.getConfig().getString("Mensagens.Deny_EnderPearl").replace("&", "§");
		Broad_cast = this.getConfig().getString("Mensagens.Broad_cast").replace("&", "§");
		In_combat = this.getConfig().getString("Action_Bar.In_combat").replace("&", "§");
		Block_Chat = this.getConfig().getBoolean("Actions.Block_Chat");
		Block_EnderPearl = this.getConfig().getBoolean("Actions.Block_EnderPearl");
		Combat_time = this.getConfig().getInt("Combat_time");
		allow = (ArrayList<String>) this.getConfig().getStringList("Allowed_Commands");
	}

	public static boolean isInteger(String args) {
		try {
			Integer.parseInt(args);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean onCommand(CommandSender p, Command cmd, String lb, String[] a) {
		if (lb.equalsIgnoreCase("combatlog")) {
			if (!p.hasPermission("combatlog.admin")) {
				p.sendMessage("§cVocê não possui permissão suficiente.");
				return true;
			}
			if (a.length == 0) {
				p.sendMessage("§cSintaxe incorreta, utilize: /combatlog <settempo, addcmd, removecmd>");
				return true;
			}
			if (a[0].equalsIgnoreCase("settempo")) {
				if (a.length == 1) {
					p.sendMessage("§cSintaxe incorreta, utilize: /combatlog settempo <segundos>");
					return true;
				}
				if (!isInteger(a[1])) {
					p.sendMessage("§cVocê precisa digitar um número inteiro!");
					return true;
				}
				if(Integer.valueOf(a[1]) < 1) {
					p.sendMessage("§cO número precisa ser maior do que zero!");
					return true;
				}
				this.getConfig().set("Combat_time", Integer.valueOf(a[1]));
				this.saveConfig();
				p.sendMessage("§eTempo setado para: §f" + a[1] + " §esegundos.");
			} else if (a[0].equalsIgnoreCase("addcmd")) {
				if (a.length == 1) {
					p.sendMessage("§cSintaxe incorreta, utilize: /combatlog addcmd <comando>");
					return true;
				}
				List<String> list = getConfig().getStringList("Allowed_Commands");
				if(list.contains(a[1])) {
					p.sendMessage("§cO comando '" + a[1] + "' já existe na lista de comandos liberados.");
					return true;
				}
				list.add(a[1]);
				this.getConfig().addDefault("Allowed_Commands", a[1]);
				this.getConfig().set("Allowed_Commands", list);
				this.saveConfig();
				p.sendMessage("§eO comando §f'" + a[1] + "' §efoi adicionado a lista de comandos liberados.");
			} else if (a[0].equalsIgnoreCase("removecmd")) {
				if (a.length == 1) {
					p.sendMessage("§cSintaxe incorreta, utilize: /combatlog removecmd <comando>");
					return true;
				}
				List<String> list = getConfig().getStringList("Allowed_Commands");
				if(!list.contains(a[1])) {
					p.sendMessage("§cA lista de comandos liberados não possui o comando '" + a[1] + "'.");
					return true;
				}
				list.remove(a[1]);
				this.getConfig().set("Allowed_Commands", list);
				this.saveConfig();
				p.sendMessage("§eO comando §f'" + a[1] + "' §efoi removido da lista de comandos liberados.");
			} else if (!a[0].equalsIgnoreCase("settempo") || !a[0].equalsIgnoreCase("removecmd") || !a[0].equalsIgnoreCase("addcmd")) {
				p.sendMessage("§cSintaxe incorreta, utilize: /combatlog <settempo, addcmd, removecmd>");
			}
		}
		return false;
	}
}
