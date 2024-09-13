package pkg.metropoly.event;

import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import pkg.metropoly.Metropoly;

public class CreeperGriefListener implements Listener {
    private Metropoly metropoly;

    public CreeperGriefListener(Metropoly metropoly) {
        this.metropoly = metropoly;
    }
    @EventHandler
    public void onCreeperGrief(EntityExplodeEvent e) {
        if (e.getEntity() instanceof Creeper) {
            if (!metropoly.getConfig().getBoolean("creeperGrief")){
                e.blockList().clear();
            }
        }
    }
}
