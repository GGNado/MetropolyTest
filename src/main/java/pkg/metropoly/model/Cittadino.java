package pkg.metropoly.model;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import pkg.metropoly.enums.Ruolo;

import static pkg.metropoly.enums.Ruolo.CITTADINO;
import static pkg.metropoly.enums.Ruolo.SINDACO;

public class Cittadino {
    private OfflinePlayer player;
    private Integer soldi;
    private Ruolo ruolo;

    public Cittadino(Player player, Integer soldi, Ruolo ruolo) {
        this.player = player;
        this.soldi = soldi;
        this.ruolo = ruolo;
    }

    public Cittadino(OfflinePlayer player, Integer soldi, Ruolo ruolo) {
        this.player = player;
        this.soldi = soldi;
        this.ruolo = ruolo;
    }

    public Cittadino() {

    }

    // Metodi getter e setter
    public OfflinePlayer getPlayer() {
        return player;
    }

    public Integer getSoldi() {
        return soldi;
    }
    public void addSoldi(Integer integer){
        this.soldi += integer;
    }

    public void setSoldi(Integer soldi) {
        this.soldi = soldi;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        switch (ruolo) {
            case "sindaco":
                this.ruolo = SINDACO;
            default:
                this.ruolo = CITTADINO;
        }
    }

    @Override
    public String toString() {
        return "Cittadino{" +
                "player=" + player +
                ", soldi=" + soldi +
                ", ruolo=" + ruolo +
                '}';
    }

    public void removeSoldi(int i) {
        this.soldi -= i;
    }
}
