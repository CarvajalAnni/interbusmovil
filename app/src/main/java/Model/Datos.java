package Model;

public class Datos {

    String Url;
    String Ubicacion;
    String Observaciones;

    public Datos() {
    }

    public Datos(String url, String ubicacion, String observaciones) {
        Url = url;
        Ubicacion = ubicacion;
        Observaciones = observaciones;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String observaciones) {
        Observaciones = observaciones;
    }
    @Override
    public String toString() {
        return Url + Ubicacion + Observaciones;
    }
}
