package pkg.metropoly.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import pkg.metropoly.Metropoly;

public class EntityDropListener implements Listener {
    private Metropoly metropoly;
    public EntityDropListener(Metropoly metropoly) {
        this.metropoly = metropoly;
    }
    @EventHandler
    public void entityDropEvent(EntityDeathEvent event) {
        if (event.getEntity().getType() == EntityType.ZOMBIFIED_PIGLIN) {
            if (!metropoly.getConfig().getBoolean("zombiePiglinDropGold"))
                event.getDrops().clear();
        }
    }
}
