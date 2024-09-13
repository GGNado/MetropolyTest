package pkg.metropoly.model;

import java.util.*;

public class Town {
    private Cittadino sindaco;
    private Map<UUID, Cittadino> cittadini;  // Mappa di cittadini per un accesso rapido
    private List<Cuboid> chunks;
    private Integer numeroClaim;
    private Double banca;
    private String nomeCitta;

    public Town(Cittadino sindaco, Map<UUID, Cittadino> cittadini, List<Cuboid> chunks, Integer numeroClaim, Double banca, String nomeCitta) {
        this.sindaco = sindaco;
        this.cittadini = cittadini;
        this.chunks = chunks;
        this.numeroClaim = numeroClaim;
        this.banca = banca;
        this.nomeCitta = nomeCitta;
    }

    public Town() {
        this.cittadini = new HashMap<>();
    }

    // Getter e setter

    public Cittadino getSindaco() {
        return sindaco;
    }

    public void setSindaco(Cittadino sindaco) {
        this.sindaco = sindaco;
    }

    public Collection<Cittadino> getCittadini() {
        return cittadini.values();  // Restituisce i cittadini come collezione
    }

    public void addCittadino(Cittadino cittadino) {
        this.cittadini.put(cittadino.getPlayer().getUniqueId(), cittadino);
    }

    public Cittadino getCittadino(UUID uuid) {
        return this.cittadini.get(uuid);
    }

    public void removeCittadino(UUID uuid) {
        this.cittadini.remove(uuid);
    }

    public List<Cuboid> getChunks() {
        return chunks;
    }

    public void setChunks(List<Cuboid> chunks) {
        this.chunks = chunks;
    }

    public Integer getNumeroClaim() {
        return numeroClaim;
    }

    public void setNumeroClaim(Integer numeroClaim) {
        this.numeroClaim = numeroClaim;
    }

    public void addNumeroClaim(Integer numeroClaim) {
        this.numeroClaim += 1;
    }

    public Double getBanca() {
        return banca;
    }

    public void setBanca(Double banca) {
        this.banca = banca;
    }

    public String getNomeCitta() {
        return nomeCitta;
    }

    public void setNomeCitta(String nomeCitta) {
        this.nomeCitta = nomeCitta;
    }

    @Override
    public String toString() {
        return "Town{" +
                "sindaco=" + sindaco +
                ", cittadini=" + cittadini +
                ", chunks=" + chunks +
                ", numeroClaim=" + numeroClaim +
                ", banca=" + banca +
                ", nomeCitta='" + nomeCitta + '\'' +
                '}';
    }
}
