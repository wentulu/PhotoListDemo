package cn.stu.cusview.ruiz.myapplication.picture.bean;

public class BasePicInfo<T> {

    private int itemType;
    private T data;

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
