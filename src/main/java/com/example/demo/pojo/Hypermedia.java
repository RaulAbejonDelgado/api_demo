package com.example.demo.pojo;

public class Hypermedia {

    private String rel;
    private String href;

    public Hypermedia() {
    }

    public Hypermedia(String rel, String href) {
        this.rel = rel;
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
