package com.gusain.arogya;

/**
 * Created by akshanshgusain on 4/12/17.
 */

public class FLP {
    private String title;
    private String desc;
    private String image;

    public FLP(){

    }

    public FLP(String title, String desc, String image) {
        this.title = title;
        this.desc = desc;
        this.image = image;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
