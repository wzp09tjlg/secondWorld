package com.example.wuzp.secondworld.view.wangdou.model.parse.book;

/**
 * Created by zyb on 2017/2/21.
 */
public class BookinfoFromH5 {
    /**
     * bookId : 5372731
     * intro : 十年前的绝世天才被废，十年后，一块神秘的玉石因缘出现。少年一朝觉醒，得无上传承，从此鱼跃龙门，拳碎虚空，以无上神威打入那神秘的远古世界，踏上横扫九天十地的逆袭之路。
     * title : 斗天武神
     * cover : http://vipbook.sinaedge.com/bookcover/dest170/59/cover_f16f0a1b21452146bbd9f3a897decb78.jpg
     * chapterNum : 1531
     * chargeMode : 3
     * firstChapter : {"chapterId":10323185,"isVip":"2","s_num":1,"title":"第001章 元修世界"}
     * chapters : {"chapterId":10323188,"isVip":"2","s_num":4,"title":"第004章 十印无双"}
     */

    private String bookId;
    private String intro;
    private String title;
    private String cover;
    private int chapterNum;
    private String chargeMode;
    private boolean flag = false;
    /**
     * chapterId : 10323185
     * isVip : 2
     * s_num : 1
     * title : 第001章 元修世界
     */

    private FirstChapterBean firstChapter;
    /**
     * chapterId : 10323188
     * isVip : 2
     * s_num : 4
     * title : 第004章 十印无双
     */

    private ChaptersBean chapters;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getChapterNum() {
        return chapterNum;
    }

    public void setChapterNum(int chapterNum) {
        this.chapterNum = chapterNum;
    }

    public String getChargeMode() {
        return chargeMode;
    }

    public void setChargeMode(String chargeMode) {
        this.chargeMode = chargeMode;
    }

    public FirstChapterBean getFirstChapter() {
        return firstChapter;
    }

    public void setFirstChapter(FirstChapterBean firstChapter) {
        this.firstChapter = firstChapter;
    }

    public ChaptersBean getChapters() {
        return chapters;
    }

    public void setChapters(ChaptersBean chapters) {
        this.chapters = chapters;
    }

    public static class FirstChapterBean {
        private String chapterId;
        private String isVip;
        private int s_num;
        private String title;

        public String getChapterId() {
            return chapterId;
        }

        public void setChapterId(String chapterId) {
            this.chapterId = chapterId;
        }

        public String getIsVip() {
            return isVip;
        }

        public void setIsVip(String isVip) {
            this.isVip = isVip;
        }

        public int getS_num() {
            return s_num;
        }

        public void setS_num(int s_num) {
            this.s_num = s_num;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class ChaptersBean {
        private String chapterId;
        private String isVip;
        private int s_num;
        private String title;

        public String getChapterId() {
            return chapterId;
        }

        public void setChapterId(String chapterId) {
            this.chapterId = chapterId;
        }

        public String getIsVip() {
            return isVip;
        }

        public void setIsVip(String isVip) {
            this.isVip = isVip;
        }

        public int getS_num() {
            return s_num;
        }

        public void setS_num(int s_num) {
            this.s_num = s_num;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
