package Model;

import android.widget.TextView;

public class Ubicacion {

    String latitud;
    String longitud;


    public Ubicacion() {
    }

    public Ubicacion(TextView latitud, TextView longitud) {
    }

    public Ubicacion(String latitud, String longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return  latitud + '\'' + longitud ;
    }
}
