package UserRelated;

public class IndoorConfig {
    private int id;
    private String luz;
    private String cooler;
    private String indoor;
    private String ventilador;
    private String maceta;

    public IndoorConfig(int id, String luz, String cooler, String indoor, String ventilador, String maceta) {
        this.id = id;
        this.luz = luz;
        this.cooler = cooler;
        this.indoor = indoor;
        this.ventilador = ventilador;
        this.maceta = maceta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLuz(String luz) {
        this.luz = luz;
    }

    public void setCooler(String cooler) {
        this.cooler = cooler;
    }

    public void setIndoor(String indoor) {
        this.indoor = indoor;
    }

    public void setVentilador(String ventilador) {
        this.ventilador = ventilador;
    }

    public void setMaceta(String maceta) {
        this.maceta = maceta;
    }

    public int getId() {
        return id;
    }

    public String getLuz() {
        return luz;
    }

    public String getCooler() {
        return cooler;
    }

    public String getIndoor() {
        return indoor;
    }

    public String getVentilador() {
        return ventilador;
    }

    public String getMaceta() {
        return maceta;
    }
}
