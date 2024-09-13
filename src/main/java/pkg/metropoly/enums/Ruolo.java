package pkg.metropoly.enums;

public enum Ruolo {
    SINDACO,
    CITTADINO;

    public Ruolo getByString(String string){
        return switch (string) {
            case "sindaco" -> SINDACO;
            default -> CITTADINO;
        };
    }
}
