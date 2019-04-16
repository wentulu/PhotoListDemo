package cn.stu.cusview.ruiz.myapplication.picture.bean;

public class PictureInfo {

    private String picUrl;
    private String picName,thumb;
    private int width,height,orentation;

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public int getOrentation() {
        return orentation;
    }

    public void setOrentation(int orentation) {
        this.orentation = orentation;
    }
}
