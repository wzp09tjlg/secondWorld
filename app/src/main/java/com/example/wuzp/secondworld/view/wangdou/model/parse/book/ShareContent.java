package com.example.wuzp.secondworld.view.wangdou.model.parse.book;


import com.example.wuzp.secondworld.view.wangdou.db.table.BookTable;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by zyb on 2016/10/11.
 */
public class ShareContent implements Serializable {
    private String title;
    private String imageUrl;
    private String intro;
    private String type;


    public ShareContent(Map<String, String> bookinfor) {
        title = bookinfor.get(BookTable.TITLE);
        imageUrl = bookinfor.get(BookTable.IMAGE_URL);
        intro = bookinfor.get(BookTable.INTRO);
        type = bookinfor.get("type");
    }

    public ShareContent(String content, String title) {
        this.title = title;
        this.intro = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
