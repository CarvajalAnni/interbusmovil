package Model;

public class Observaciones {

    String Observaciones;

    public Observaciones() {
    }

    public Observaciones(String observaciones) {
        Observaciones = observaciones;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String observaciones) {
        Observaciones = observaciones;
    }

    @Override
    public String toString() {
        return  Observaciones;
    }
}
