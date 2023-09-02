package com.example.blisgoproject.product;

public class Product {

    private int pnum;
    private String category;
    private String pname;
    private String pclass;
    private String pcontent1;
    private String pcontent2;
    private String pname2;
    private String img;

    public Product(int pnum, String category, String pname, String pclass, String pcontent1, String pcontent2, String pname2, String img) {
        this.pnum = pnum;
        this.category = category;
        this.pname = pname;
        this.pclass = pclass;
        this.pcontent1 = pcontent1;
        this.pcontent2 = pcontent2;
        this.pname2 = pname2;
        this.img = img;
    }

    public Product() {

    }

    public int getPnum() {
        return pnum;
    }

    public void setPnum(int pnum) {
        this.pnum = pnum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPclass() {
        return pclass;
    }

    public void setPclass(String pclass) {
        this.pclass = pclass;
    }

    public String getPcontent1() {
        return pcontent1;
    }

    public void setPcontent1(String pcontent1) {
        this.pcontent1 = pcontent1;
    }

    public String getPcontent2() {
        return pcontent2;
    }

    public void setPcontent2(String pcontent2) {
        this.pcontent2 = pcontent2;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPname2() {
        return pname2;
    }
    public void setPname2(String pname2) {
        this.pname2 = pname2;
    }
}
