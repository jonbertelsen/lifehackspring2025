package app.entities;

public class LifehackSite {
    private String title;
    private String cta;
    private String url;
    private String thumbnail;

    public LifehackSite(String title, String cta, String url, String thumbnail) {
        this.title = title;
        this.cta = cta;
        this.url = url;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getCta() {
        return cta;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}


