package Model;

import java.util.Date;

public class Datos {

    Date tiempo;
    String Url;
    String Ubicacion;
    String Observaciones;
    String IdDocumento;

    public Datos() {
    }

    public Datos(Date tiempo, String url, String ubicacion, String observaciones, String Iddocumento) {
        this.tiempo = tiempo;
        Url = url;
        Ubicacion = ubicacion;
        Observaciones = observaciones;
        IdDocumento = Iddocumento;
    }

    public String getIdDocumento() {
        return IdDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        IdDocumento = idDocumento;
    }

    public Date getTiempo() {
        return tiempo;
    }

    public void setTiempo(Date tiempo) {
        this.tiempo = tiempo;
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
