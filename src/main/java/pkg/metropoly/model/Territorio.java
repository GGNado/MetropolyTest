package pkg.metropoly.model;

import pkg.metropoly.enums.Permesso;
import pkg.metropoly.enums.Tipologia;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Territorio {
    private Integer x;
    private Integer z;
    private Cuboid cuboid;
    private Map<Permesso, Boolean> permessi;
    private String nome;
    private Cittadino proprietario;
    private Tipologia tipologia;
    private String nomeCitta;

    public Territorio() {
        this.permessi = new HashMap<>();
    }

    public String getNomeCitta() {
        return nomeCitta;
    }

    public void setNomeCitta(String nomeCitta) {
        this.nomeCitta = nomeCitta;
    }

    public Cuboid getCuboid() {
        return cuboid;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }

    public void setCuboid(Cuboid cuboid) {
        this.cuboid = cuboid;
    }

    public Map<Permesso, Boolean> getPermessi() {
        return permessi;
    }

    public void setPermessi(Map<Permesso, Boolean> permessi) {
        this.permessi = permessi;
    }

    public String getNome() {
        if (nome.equalsIgnoreCase(""))
            return "Non inserito";
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Cittadino getProprietario() {
        return proprietario;
    }

    public void setProprietario(Cittadino proprietario) {
        this.proprietario = proprietario;
    }

    public Tipologia getTipologia() {
        return tipologia;
    }

    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }
}
