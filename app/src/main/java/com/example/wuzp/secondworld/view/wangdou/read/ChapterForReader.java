package com.example.wuzp.secondworld.view.wangdou.read;

import com.example.wuzp.secondworld.view.wangdou.model.parse.book.BookMark;
import com.example.wuzp.secondworld.view.wangdou.model.parse.book.BookSummary;

import java.util.List;

/**
 * 章节信息
 * title   content   order序号
 */
public class ChapterForReader {
    private String title;
    private String content;
    private int order;
    private List<BookMark> markList;
    private List<BookSummary> summaryList;
    private boolean isFirstChapter = false;
    private boolean isLastChapter = false;
    private String chapterId;
    private String bookid;
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public boolean isFirstChapter() {
        return isFirstChapter;
    }

    public void setFirstChapter(boolean firstChapter) {
        isFirstChapter = firstChapter;
    }

    public boolean isLastChapter() {
        return isLastChapter;
    }

    public void setLastChapter(boolean lastChapter) {
        isLastChapter = lastChapter;
    }

    public List<BookMark> getMarkList() {
        return markList;
    }

    public void setMarkList(List<BookMark> markList) {
        this.markList = markList;
    }

    public List<BookSummary> getSummaryList() {
        return summaryList;
    }

    public void setSummaryList(List<BookSummary> summaryList) {
        this.summaryList = summaryList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


}
