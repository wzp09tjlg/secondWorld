package com.example.wuzp.secondworld.view.wangdou.read;

/**
 * Created by zyb on 2016/10/19.
 */
public class Page {
    String chapter;
    String content;
    int firstpos;
    int lastpos;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    int page;

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFirstpos() {
        return firstpos;
    }

    public void setFirstpos(int firstpos) {
        this.firstpos = firstpos;
    }

    public int getLastpos() {
        return lastpos;
    }

    public void setLastpos(int lastpos) {
        this.lastpos = lastpos;
    }
}
