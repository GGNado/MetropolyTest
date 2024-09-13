package pkg.metropoly.command.tabs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TownTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1){
            return StringUtil.copyPartialMatches(strings[0], Arrays.asList("info", "create <town_name>", "claim", "infoc", "list", "set spawn", "spawn", "deposit"), new ArrayList<>());
        } else if (strings.length == 2 && strings[0].contains("set")){
            return StringUtil.copyPartialMatches(strings[2], Arrays.asList("anvil", "table", "furnace", "beacon", "cauldron", "brewing", "enchant", "chest"), new ArrayList<>());
        }
        return null;
    }
}
