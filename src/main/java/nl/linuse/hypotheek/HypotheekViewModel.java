package nl.linuse.hypotheek;

public class HypotheekViewModel {
    private double hypotheekSom;
    private double wozWaarde;
    private double rente;
    private int looptijdMaanden;
    private double belastingSchijf;
    private String startDate;

    public HypotheekViewModel() {

    }

    public HypotheekViewModel(double hypotheekSom, double wozWaarde, double rente, int looptijdMaanden, double belastingSchijf, String startDate) {
        this.hypotheekSom = hypotheekSom;
        this.wozWaarde = wozWaarde;
        this.rente = rente;
        this.looptijdMaanden = looptijdMaanden;
        this.belastingSchijf = belastingSchijf;
        this.startDate = startDate;
    }

    public double getHypotheekSom() {
        return hypotheekSom;
    }

    public void setHypotheekSom(double hypotheekSom) {
        this.hypotheekSom = hypotheekSom;
    }

    public double getWozWaarde() {
        return wozWaarde;
    }

    public void setWozWaarde(double wozWaarde) {
        this.wozWaarde = wozWaarde;
    }

    public double getRente() {
        return rente;
    }

    public void setRente(double rente) {
        this.rente = rente;
    }

    public int getLooptijdMaanden() {
        return looptijdMaanden;
    }

    public void setLooptijdMaanden(int looptijdMaanden) {
        this.looptijdMaanden = looptijdMaanden;
    }

    public double getBelastingSchijf() {
        return belastingSchijf;
    }

    public void setBelastingSchijf(double belastingSchijf) {
        this.belastingSchijf = belastingSchijf;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
