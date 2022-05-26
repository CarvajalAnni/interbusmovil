package Model;

public class Datos {

    String Url;

    public Datos() {
    }

    public Datos(String url) {
        Url = url;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    @Override
    public String toString() {
        return  Url ;
    }

}
