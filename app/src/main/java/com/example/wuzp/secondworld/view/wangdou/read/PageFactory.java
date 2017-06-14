package com.example.wuzp.secondworld.view.wangdou.read;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.wuzp.secondworld.view.wangdou.db.DBService;
import com.example.wuzp.secondworld.view.wangdou.interf.PageFactoryListener;
import com.example.wuzp.secondworld.view.wangdou.model.parse.book.BookMark;
import com.example.wuzp.secondworld.view.wangdou.model.parse.book.BookSummary;
import com.example.wuzp.secondworld.view.wangdou.utils.ColorManager;
import com.example.wuzp.secondworld.view.wangdou.utils.DrawUtils;
import com.example.wuzp.secondworld.view.wangdou.utils.PixelUtil;
import com.example.wuzp.secondworld.view.wangdou.utils.ThemeManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主要处理图片的绘制，以及各种图片状态的控制
 * 划线 选词的绘制
 * Created by zyb on 2016/10/19.
 */
public class PageFactory {
    private int screenWidth; // 屏幕宽度
    private int screenHeight; // 屏幕高度
    private float headerHeight;//题目高度
    private float footerHeight;//页脚高度
    private int contentHeight;//正文高度
    private float visibleWidth; //屏幕中可显示文本的宽度
    private float visibleHeight; //屏幕中可显示文本的高度

    private int textColor; // 字体颜色
    private int fontSize; // 字体大小
    private int bgColor; // 背景颜色
    private int lineSpace = PixelUtil.sp2px(12); //行间距
    private float lineHgight; //每行的高度
    private int margin = PixelUtil.dp2px(16); // 左右与边缘的距离
    private Paint paintTitle;
    private Paint paintContent;
    private Paint paintOther;
    private Paint paintSelection;
    private Paint paintSummary;
    public List<ChapterForReader> chapterForReaderList = new ArrayList<>();
    private ChapterForReader preChapterForReader;
    private String content;
    private int electric;
    private int chapterLen; // 章节的长度
    private int charBegin; // 每一页第一个字符在章节中的位置
    private ArrayList<Line> linesVe = new ArrayList<>(); // 将章节內容分成行，并将每页按行存储到vector对象中
    private List<Page> pageList = new ArrayList<>();
    private ArrayList<ArrayList<Line>> pagesVe = new ArrayList<>();
    private List<BookMark> markList = new ArrayList<>();
    private List<BookSummary> summaryList = new ArrayList<>();
    private BookSummary singleSummary;
    int pageNum = -1;
    int curpageName = -1;
    int curpageTotalPage = -1;
    String curpageTitle = "";
    private int cacheChapterNum = 6;//SRPreferences.getInstance().getInt(SRPreferences.READ_CHAPTERCACHE_COUNT, 6);


    private PageFactoryListener listener;
    private boolean isHandSkipPage = false;//是否是认为点击进行的跳转
    private boolean preLoadEnd = false;//上一张准备完毕

    /**
     * 在新建一个BookPage对象时，需要向其提供数据，以支持屏幕翻页功能。
     */
    public PageFactory() {
        if (true) {
            this.screenHeight = PixelUtil.getScreenHeight();
        } else {
            this.screenHeight = PixelUtil.getScreenHeight() - PixelUtil.sp2px(25);//状态栏显示模式
        }
        this.screenWidth = PixelUtil.getScreenWidth();
        this.fontSize = 18;//SRPreferences.getInstance().getInt(SRPreferences.READ_FONTSIZE, 18);
        headerHeight = lineSpace + PixelUtil.sp2px(20);//头布局高度
        footerHeight = lineSpace + PixelUtil.sp2px(20);//尾布局高度
        visibleWidth = screenWidth - margin * 2;//屏幕可用于绘制内容的宽度
        visibleHeight = screenHeight - margin * 2;//屏幕可用于绘制内容的高度
        contentHeight = (int) (visibleHeight - headerHeight - footerHeight - 1);//可用于绘制内容的高度
    }

    private void initPaint() {
        paintContent = new Paint(Paint.ANTI_ALIAS_FLAG);//内容画笔
        paintContent.setTextAlign(Paint.Align.LEFT);
        paintContent.setAntiAlias(true);
        paintTitle = new Paint(Paint.ANTI_ALIAS_FLAG);//题目画笔
        paintTitle.setTextAlign(Paint.Align.LEFT);
        paintTitle.setAntiAlias(true);
        paintOther = new Paint(Paint.ANTI_ALIAS_FLAG);//其他部分画笔
        paintOther.setTextAlign(Paint.Align.LEFT);
        paintOther.setAntiAlias(true);
        paintOther.setTextSize(PixelUtil.sp2px(12));
        paintSelection = new Paint(Paint.ANTI_ALIAS_FLAG);//划线块画笔
        paintSelection.setAntiAlias(true);
        paintSelection.setColor(0x77ffcc33);
        paintSummary = new Paint(Paint.ANTI_ALIAS_FLAG);//下划线画笔
        paintSummary.setColor(0xffff9600);
        paintSummary.setStrokeWidth(3);
    }


    public void init() {//重新创建具体的属性，在属性改变的时候会重新调用
        if (true) {
            this.screenHeight = PixelUtil.getScreenHeight();
        } else {
            this.screenHeight = PixelUtil.getScreenHeight() - PixelUtil.sp2px(25);
        }
        this.screenWidth = PixelUtil.getScreenWidth();
        this.fontSize = 18;//SRPreferences.getInstance().getInt(SRPreferences.READ_FONTSIZE, 18);
        headerHeight = lineSpace + PixelUtil.sp2px(20);
        footerHeight = lineSpace + PixelUtil.sp2px(20);
        visibleWidth = screenWidth - margin * 2;//屏幕可用于绘制内容的宽度
        visibleHeight = screenHeight - margin * 2;//屏幕可用于绘制内容的高度
        contentHeight = (int) (visibleHeight - headerHeight - footerHeight - 1);
        initPaint();
        content = chapterForReaderList.get(0).getContent();//当前章的内容
        chapterLen = content.length();
        charBegin = 0;
        lineHgight = lineSpace + PixelUtil.sp2px(fontSize);

        setBgColor(Color.parseColor(ThemeManage.getTheme()));//根据主题设置颜色
        paintContent.setTextSize(PixelUtil.sp2px(fontSize));//设置画笔size
        paintTitle.setTextSize(PixelUtil.sp2px(fontSize * 7 / 5));

        markList = chapterForReaderList.get(0).getMarkList();//当前章书签属性
        summaryList = chapterForReaderList.get(0).getSummaryList();//当前章划线属性
        slicePage();//执行分页操作
    }

    /**
     * 分页操作，寻遍遍历段落内容，将段落分为页集合和行集合
     */
    public synchronized void slicePage() {
        int pagePos = 0;//页码position
        int paragraphPos = 0;//段落position
        List<Page> cachepageList = new ArrayList<>();//页集合
        ArrayList<ArrayList<Line>> cachepagesVe = new ArrayList<>();//行集合
        int curPos = 0;//游标position
        while (curPos < chapterLen) {//while遍历章节所有内容
            ArrayList<Line> lines = new ArrayList<>();
            charBegin = curPos;//页开始坐标
            Page page = new Page();//创建页对象，并设置相关属性
            pagePos++;
            page.setFirstpos(charBegin);
            page.setChapter(chapterForReaderList.get(0).getTitle());
            int usedHeight = 0;//屏幕已经使用高度
            while (contentHeight - usedHeight >= lineHgight && curPos < chapterLen) {//当前页剩余高度足够绘制下一行且没有到达章节尾
                int i = content.indexOf("\n", curPos);//获取截止到下一个换行符的长度
                if (i == -1) {//获取失败，本章只有一段的情况
                    i = content.length();
                }
                String paragraphStr = content.substring(curPos, i);//截取段string
                paragraphPos++;
                if (curPos == i) {//空段的情况
                    Line line = new Line();
                    usedHeight += lineHgight;
                    line.setContent("");
                    line.setCoordinateY(usedHeight + margin + headerHeight);
                    line.setFirstpos(curPos);
                    line.setLastpos(curPos);
                    lines.add(line);
                }
                while (paragraphStr.length() > 0) {
                    Line line = new Line();//初始化行对象，并设置相应的属性
                    line.setFirstpos(curPos);
                    int horSize;
                    if (pagePos == 1 && paragraphPos == 2) {
                        horSize = paintTitle.breakText(paragraphStr, true,
                                visibleWidth, null);//根据画笔属性以及屏幕宽度测量可以填充几个字符。
                    } else {
                        horSize = paintContent.breakText(paragraphStr, true,
                                visibleWidth, null);
                    }
                    curPos += horSize;
                    line.setLastpos(curPos);
                    if (pagePos == 1 && paragraphPos == 2) {
                        lineHgight = lineSpace + PixelUtil.sp2px(fontSize * 3 / 2);
                    } else {
                        lineHgight = lineSpace + PixelUtil.sp2px(fontSize);
                    }
                    usedHeight += lineHgight;
                    line.setCoordinateY(usedHeight + margin + headerHeight);
                    line.setContent(paragraphStr.substring(0, horSize));
                    lines.add(line);
                    paragraphStr = paragraphStr.substring(horSize);
                    if (contentHeight - usedHeight < lineHgight) {
                        paragraphPos--;
                        break;
                    }
                }
                if (paragraphStr.length() == 0) {
                    Line line = new Line();
                    line.setFirstpos(curPos);
                    curPos += "\n".length();
                    line.setLastpos(curPos - 1);
                    usedHeight += lineSpace * 2;
                    line.setCoordinateY(usedHeight + margin + headerHeight);
                    line.setContent("\n");
                    lines.add(line);
                }
            }
            page.setLastpos(curPos - 1);
            page.setPage(cachepageList.size());
            cachepageList.add(page);
            cachepagesVe.add(lines);
        }
        pagesVe.clear();
        pagesVe.addAll(cachepagesVe);
        pageList.clear();
        pageList.addAll(cachepageList);
    }

    public void drawJustContent(Canvas c) {
        drawJustContent(c, false);
    }

    /**
     * 绘制页内容  滑动翻页使用
     *
     * @param checkmark 是否需要检查书签  只有绘制当前页时检查书签
     */
    public void drawJustContent(Canvas c, boolean checkmark) {
        if (checkmark) {
            listener.isShowMark(isSetMarkShow());
            //绘制当前页时对总页数和当前页以及标题进行保存，处理上下滑动翻页时候最后一个页码闪烁问题
            curpageName = pageNum;
            curpageTotalPage = pagesVe.size();
            listener.changePage(curpageTotalPage, curpageName);
            if (chapterForReaderList.size() > 0) {
                curpageTitle = chapterForReaderList.get(0).getTitle();
            }
        }
        //遍历行集合绘制，并处理划线和待添加的划线以及标题下的横线
        int y = 0;
        int count_N = 0;
        boolean lineIsExist = false;
        drawBg(c);
        for (int i = 0; i < linesVe.size(); i++) {
            if (pageNum == 0 && count_N == 1) {
                lineHgight = lineSpace + PixelUtil.sp2px(fontSize * 3 / 2);
            } else {
                lineHgight = lineSpace + PixelUtil.sp2px(fontSize);
            }
            if (linesVe.get(i).getContent().equals("\n")) {
                y += lineSpace * 2;
                count_N++;
            } else {
                y += lineHgight;
            }
            if (pageNum == 0 && count_N == 1) {
                c.drawText(linesVe.get(i).getContent(), margin, y, paintTitle);
            } else {
                c.drawText(linesVe.get(i).getContent(), margin, y, paintContent);
            }
            if (pageNum == 0 && count_N == 2 && !lineIsExist) {
                c.drawLine(0, y, screenWidth, y, paintContent);
                lineIsExist = true;
            }
            if (summaryList != null && summaryList.size() > 0) {
                for (BookSummary booksummary : summaryList) {
                    if (pageNum == 0 && count_N == 1) {
                        drawLineByPos(linesVe.get(i), booksummary.getStartPos(), booksummary.getEndPos(), c, y, paintTitle);
                    } else {
                        drawLineByPos(linesVe.get(i), booksummary.getStartPos(), booksummary.getEndPos(), c, y, paintContent);
                    }
                }
            }
        }
    }

    /**
     * @param height 仿真和左右使用，内容不从第一行开始绘制，需要留出标题的空隙
     */
    public void drawJustContent(Canvas c, float height) {
        listener.isShowMark(isSetMarkShow());
        curpageName = pageNum;
        curpageTotalPage = pagesVe.size();
        listener.changePage(curpageTotalPage, curpageName);
        if (chapterForReaderList.size() > 0) {
            curpageTitle = chapterForReaderList.get(0).getTitle();
        }
        float y = height;
        int count_N = 0;
        boolean lineIsExist = false;
        drawBg(c);
        for (int i = 0; i < linesVe.size(); i++) {
            if (pageNum == 0 && count_N == 1) {
                lineHgight = lineSpace + PixelUtil.sp2px(fontSize * 3 / 2);
            } else {
                lineHgight = lineSpace + PixelUtil.sp2px(fontSize);
            }
            if (linesVe.get(i).getContent().equals("\n")) {
                y += lineSpace * 2;
                count_N++;
            } else {
                y += lineHgight;
            }
            if (singleSummary != null) {
                if (pageNum == 0 && count_N == 1) {
                    drawRectByPos(linesVe.get(i), singleSummary.getStartPos(), singleSummary.getEndPos(), c, y, paintTitle);
                } else {
                    drawRectByPos(linesVe.get(i), singleSummary.getStartPos(), singleSummary.getEndPos(), c, y, paintContent);
                }
            }

            if (pageNum == 0 && count_N == 1) {
                c.drawText(linesVe.get(i).getContent(), margin, y, paintTitle);
            } else {
                c.drawText(linesVe.get(i).getContent(), margin, y, paintContent);
            }

            if (pageNum == 0 && count_N == 2 && !lineIsExist) {
                c.drawLine(0, y, screenWidth, y, paintContent);
                lineIsExist = true;
            }
            if (summaryList != null && summaryList.size() > 0) {
                for (BookSummary booksummary : summaryList) {
                    if (pageNum == 0 && count_N == 1) {
                        drawLineByPos(linesVe.get(i), booksummary.getStartPos(), booksummary.getEndPos(), c, y, paintTitle);
                    } else {
                        drawLineByPos(linesVe.get(i), booksummary.getStartPos(), booksummary.getEndPos(), c, y, paintContent);
                    }
                }
            }
        }
    }

    public void drawBg(Canvas c) {
        c.drawColor(bgColor);
    }

    public void drawTitle(Canvas c) {
        Paint.FontMetrics fontMetrics = paintOther.getFontMetrics();
        DrawUtils.drawTitle(c, chapterForReaderList.get(0).getTitle(), margin,
                margin + lineHgight / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2, paintOther);
    }

    public void drawBottom(Canvas c) {
        DrawUtils.drawElectric(c, margin, screenHeight - margin, electric, textColor);
        DrawUtils.drawCurpage(c, pageNum, pagesVe.size(), screenWidth - margin, screenHeight - margin, paintOther);
        DrawUtils.drawTime(c, margin + 60, screenHeight - margin, paintOther);
    }

    public void draw(Canvas c) {
        drawJustContent(c, margin + lineHgight);
        drawTitle(c);
        drawBottom(c);
    }

    /**
     * 翻到下一页
     */
    public boolean nextPage() {
        if (isLastPage()) {
            if (!nextChapter()) // 如果已经到本书末尾，那么不能继续执行翻页代码
            {
                if (isHandSkipPage) {
                    listener.noContent(true);
                    isHandSkipPage = false;
                }
                return false;
            }
        }
        pageNum++;
        linesVe = pagesVe.get(pageNum <= pagesVe.size() - 1 ? pageNum : pagesVe.size() - 1);
        return true;
    }

    /**
     * 翻到上一页
     */
    public boolean prePage() {
        if (isFirstPage()) {
            if (!preChapter()) // 如果已经到本书第一章，就不能继续执行翻页代码
            {
                if (isHandSkipPage) {
                    listener.noContent(false);
                    isHandSkipPage = false;
                }
                return false;
            }
        }
        if (pageNum != 0) {
            pageNum--;
        }
        linesVe = pagesVe.get(pageNum <= pagesVe.size() - 1 ? pageNum : pagesVe.size() - 1);
        return true;
    }

    public boolean skipPage(int page) {
        if (page == -1) {
            page = 0;
        }
        linesVe = pagesVe.get(page <= pagesVe.size() - 1 ? page : pagesVe.size() - 1);
        pageNum = page <= pagesVe.size() - 1 ? page : pagesVe.size() - 1;
        return true;
    }

    public boolean skipPage() {
        if (pageNum == -1) {
            pageNum = 0;
        }
        pageNum = pageNum <= pagesVe.size() - 1 ? pageNum : pagesVe.size() - 1;
        linesVe = pagesVe.get(pageNum);
        return true;
    }

    /**
     * 跳到下一章，若返回值为false，则当前章节已经为最后一章
     */
    public boolean nextChapter() {
        if (chapterForReaderList.size() == 1) {
            return false;
        }
        preChapterForReader = chapterForReaderList.get(0);
        chapterForReaderList.remove(0);
        updateChapterIdInfo();
        content = chapterForReaderList.get(0).getContent();
        markList = chapterForReaderList.get(0).getMarkList();
        summaryList = chapterForReaderList.get(0).getSummaryList();
        chapterLen = content.length();
        charBegin = 0;
        slicePage();
        pageNum = -1;
        if (chapterForReaderList.size() < cacheChapterNum) {
            listener.nextChapter();
        }
        return true;
    }

    /**
     * 跳到上一章,若返回值为false，则当前章节已经为第一章
     */
    public boolean preChapter() {
        if (preChapterForReader == null) {
            return false;
        }
        chapterForReaderList.add(0, preChapterForReader);
        if (chapterForReaderList.size() == 4) {
            chapterForReaderList.remove(3);
        }
        preChapterForReader = null;
        updateChapterIdInfo();
        preLoadEnd = false;
        listener.preChapter();
        content = chapterForReaderList.get(0).getContent();
        markList = chapterForReaderList.get(0).getMarkList();
        summaryList = chapterForReaderList.get(0).getSummaryList();
        chapterLen = content.length();
        charBegin = chapterLen;
        slicePage();
        pageNum = pagesVe.size();
        return true;
    }

    /**
     * 更新内存中数据源
     * 会将内存的数据源信息更新到activity 并回调相应的行为
     * 做了部分容错处理，确保数据源的正确性
     */
    public synchronized void updateChapterList(ChapterForReader chapterForReader, boolean isNext, boolean isSkip) {
        if (isNext) {
            if (isSkip) {
                if (chapterForReader != null) {
                    chapterForReaderList.clear();
                    preChapterForReader = null;
                }
            }
            if (chapterForReader != null && chapterForReader.getContent() != null && !chapterForReaderList.contains(chapterForReader)) {
                if (chapterForReaderList.size() == 0) {
                    chapterForReaderList.add(chapterForReader);
                    listener.newAddChapter(chapterForReader);
                    updateChapterIdInfo();
                    listener.preChapter();//加载上一章
                    listener.contentLoadSuccsee();//告诉activity内容加载成功可以显示了
                } else {
                    int newNum = DBService.queryChapterPosByTagWithChapterId(chapterForReader.getTag(), chapterForReader.getChapterId());
                    int oldNum = DBService.queryChapterPosByTagWithChapterId(chapterForReaderList.get(chapterForReaderList.size() - 1).getTag(),
                            chapterForReaderList.get(chapterForReaderList.size() - 1).getChapterId());
                    if (newNum > oldNum) {//容差处理
                        chapterForReaderList.add(chapterForReader);
                        listener.newAddChapter(chapterForReader);//在听书的情况下，会将新加数据自动加入ttsdate中处理，一自动维持语音播放
                    } else {
                    }
                }
            }
        } else {
            if (preChapterForReader == null && chapterForReader != null && chapterForReader.getContent() != null) {
                int newNum = DBService.queryChapterPosByTagWithChapterId(chapterForReader.getTag(), chapterForReader.getChapterId());
                int oldNum = DBService.queryChapterPosByTagWithChapterId(chapterForReaderList.get(0).getTag(),
                        chapterForReaderList.get(0).getChapterId());
                if (newNum < oldNum) {//容差处理
                    preChapterForReader = chapterForReader;
                } else {
                    listener.preChapter();
                }
            }
            preLoadEnd = true;
        }
        updateChapterIdInfo();
        if (chapterForReaderList.size() < cacheChapterNum && isNext) {
            listener.nextChapter();//根据数据源判断是否需要自动加载下一章
        }
    }

    public void updateChapterList() {
        updateChapterIdInfo();
        if (chapterForReaderList.size() == 0) {//当size为零时，只用执行下一章的操作，后续会在第一章访问完成后自动缓存上一章和下一章
            listener.nextChapter();
        } else {
            if (preChapterForReader == null) {//size不为零时需要更新章节缓存
                listener.preChapter();
            }
            if (chapterForReaderList.size() < cacheChapterNum) {
                listener.nextChapter();
            }
        }
    }

    public void updateChapterIdInfo() {//回调数据源信息给activity
        if (chapterForReaderList.size() != 0) {
            listener.getChapterIdInfo(String.valueOf(chapterForReaderList.get(0).getChapterId()),
                    DBService.queryNextChapterIdByTagWithChapterId(chapterForReaderList.get(0).getTag(), chapterForReaderList.get(0).getChapterId(), false),
                    DBService.queryNextChapterIdByTagWithChapterId(chapterForReaderList.get(0).getTag(), chapterForReaderList.get(chapterForReaderList.size() - 1).getChapterId(), true),
                    String.valueOf(chapterForReaderList.get(chapterForReaderList.size() - 1).getChapterId()),
                    DBService.queryChapterPosByTagWithChapterId(chapterForReaderList.get(0).getTag(), chapterForReaderList.get(0).getChapterId()));
        }
    }

    public boolean isFirstPage() {
        return pageNum <= 0;
    }

    public boolean isLastPage() {
        return pageNum >= pagesVe.size() - 1;
    }

    public void setFontSize(int size) {
        fontSize = size;
        //SRPreferences.getInstance().setInt(SRPreferences.READ_FONTSIZE, fontSize);
        lineHgight = PixelUtil.sp2px(fontSize) + lineSpace;
        paintContent.setTextSize(PixelUtil.sp2px(fontSize));
        paintTitle.setTextSize(PixelUtil.sp2px(fontSize * 7 / 5));
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setElectric(int electric) {
        this.electric = electric;
    }

    public int getElectric() {
        return electric;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        int contentColor = 0;
        int otherColor = 0;
        switch (bgColor) {
            case ColorManager.FFebebebINT:
                contentColor = ColorManager.FF424242;
                otherColor = ColorManager.FF757575;
                break;
            case ColorManager.FFe6dbc8INT:
                contentColor = ColorManager.FF424242;
                otherColor = ColorManager.FF726d63;
                break;
            case ColorManager.FFcce9ceINT:
                contentColor = ColorManager.FF424242;
                otherColor = ColorManager.FF657466;
                break;
            case ColorManager.FF051c2cINT:
                contentColor = ColorManager.FF50606b;
                otherColor = ColorManager.FF374956;
                break;
            case ColorManager.FF393335INT:
                contentColor = ColorManager.FF747071;
                otherColor = ColorManager.FF605b5d;
                break;
            case ColorManager.FF3e3e3eINT:
                contentColor = ColorManager.FF787878;
                otherColor = ColorManager.FF646464;
                break;
            default:
                break;
        }
        this.textColor = contentColor;
        if (paintContent != null && paintTitle != null && paintOther != null) {
            paintContent.setColor(contentColor);
            paintTitle.setColor(contentColor);
            paintOther.setColor(otherColor);
        }
        if (listener != null) {
            listener.changeTheme();
        }
    }

    public int getTextColor() {
        return textColor;
    }

    public int reSlicePage() {
        //        if (pageNum < 0) {
        //            return 0;
        //        }
        //        Page page = pageList.get(pageNum <= pageList.size() - 1 ? pageNum : pageList.size() - 1);
        //        int indox = (page.getFirstpos() + page.getLastpos()) / 2;
        int indox = getIndox();
        pageNum = -1;
        slicePage();
        return getPageNumByPos(indox);
    }

    public int getIndox() {
        if (pageNum < 0) {
            return 0;
        }
        Page page = pageList.get(pageNum <= pageList.size() - 1 ? pageNum : pageList.size() - 1);
        int indox = (page.getFirstpos() + page.getLastpos()) / 2;
        return indox;
    }

    public int getPageNumByPos(int indox) {
        for (Page item : pageList) {
            if (indox >= item.getFirstpos() && indox <= item.getLastpos()) {
                return item.getPage();
            }
        }
        return 0;
    }

    public int getPageNum() {
        return pageNum >= 0 ? pageNum : 0;
    }

    public int getCurpagePageName() {
        return curpageName >= 0 ? curpageName : 0;
    }

    public int getCurpageTotalPage() {
        return curpageTotalPage >= 0 ? curpageTotalPage : 0;
    }

    public void setCacheChapter() {
        this.cacheChapterNum = 6;//SRPreferences.getInstance().getInt(SRPreferences.READ_CHAPTERCACHE_COUNT, 6);
    }

    public String getTitle() {
        if (chapterForReaderList.size() > 0) {
            return chapterForReaderList.get(0).getTitle();
        }
        return "";
    }

    public String getCurTitle() {
        return curpageTitle;
    }

    public Paint getPaintOther() {
        return paintOther;
    }

    public BookSummary getAddSummary() {
        return singleSummary;
    }

    public boolean isPreLoadEnd() {
        return preLoadEnd;
    }

    public int getContentHeight() {
        return contentHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setHandSkipPage(boolean handSkipPage) {
        isHandSkipPage = handSkipPage;
    }

    public void setListener(PageFactoryListener listener) {
        this.listener = listener;
    }

    public void addBookMark() {
        try { // chapterForReaderList有时候为空 获取0位置奔溃
            BookMark mark = new BookMark();
            mark.setBookid(chapterForReaderList.get(0).getTag());
            mark.setTitle(chapterForReaderList.get(0).getTitle());
            mark.setChapterId(String.valueOf(chapterForReaderList.get(0).getChapterId()));
            mark.setStartPos(pageList.get(pageNum <= pageList.size() - 1 ? pageNum : pageList.size() - 1).getFirstpos());
            mark.setEndPos(pageList.get(pageNum <= pageList.size() - 1 ? pageNum : pageList.size() - 1).getLastpos());
            mark.setContent(content.substring(pageList.get(pageNum <= pageList.size() - 1 ? pageNum : pageList.size() - 1).getFirstpos(), pageList.get(pageNum <= pageList.size() - 1 ? pageNum : pageList.size() - 1).getLastpos()));
            mark.setTime(String.valueOf(System.currentTimeMillis()));
            if (markList == null) {
                markList = new ArrayList<>();
            }
            markList.add(mark);
            chapterForReaderList.get(0).setMarkList(markList);
            DBService.insertBookMark(mark);
        } catch (Exception e) {
        }
    }

    public void addBookSummary(int startPos, int endPos) {
        BookSummary summary = new BookSummary();
        summary.setBookid(chapterForReaderList.get(0).getTag());
        summary.setTitle(chapterForReaderList.get(0).getTitle());
        summary.setChapterId(String.valueOf(chapterForReaderList.get(0).getChapterId()));
        summary.setStartPos(startPos);
        summary.setEndPos(endPos);
        summary.setTime(String.valueOf(System.currentTimeMillis()));
        summary.setContent(content.substring(startPos, endPos));
        singleSummary = null;
        singleSummary = summary;
    }

    public void addBookSummary2List() {
        if (summaryList == null) {
            summaryList = new ArrayList<>();
        }
        summaryList.add(singleSummary);
        chapterForReaderList.get(0).setSummaryList(summaryList);
        DBService.insertBookSummary(singleSummary);
        singleSummary = null;
    }

    public void detBookSummary() {
        if (summaryList.size() > 0) {
            summaryList.remove(singleSummary);
            chapterForReaderList.get(0).setSummaryList(summaryList);
            DBService.deteleBookSummary(singleSummary.getStartPos(), singleSummary.getEndPos(), singleSummary.getBookid());
            singleSummary = null;
        }
    }

    public void restoreAddSummary() {
        if (singleSummary != null) {
            singleSummary = null;
        }
    }

    /**
     * 根据坐标计算字节索引以及相应的坐标值
     */
    public Map<String, Object> mathPosByCoordinate(float x, float y) {
        Map<String, Object> map = new HashMap<>();
        try {
            int linesNum = 0;
            boolean end = true;
            for (int i = 0; i < linesVe.size(); i++) {
                if (linesVe.get(i).getCoordinateY() > y && end) {
                    linesNum = i;
                    map.put("coordinateEndY", linesVe.get(i).getCoordinateY());
                    map.put("coordinateStartY", linesVe.get(i).getCoordinateY() - lineHgight);
                    end = false;
                }
            }
            if (end) {
                return null;
            }
            for (int i = 0; i < linesVe.get(linesNum).getContent().length(); i++) {
                if (paintContent.measureText(linesVe.get(linesNum).getContent().substring(0, i)) + margin <= x && paintContent.measureText(linesVe.get(linesNum).getContent().substring(0, i + 1)) + margin >= x) {
                    map.put("coordinateStartX", paintContent.measureText(linesVe.get(linesNum).getContent().substring(0, i)) + margin);
                    map.put("coordinateEndX", paintContent.measureText(linesVe.get(linesNum).getContent().substring(0, i + 1)) + margin);
                    map.put("position", linesVe.get(linesNum).getFirstpos() + i);
                    return map;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 根据坐标值判断是否处于划线区域，并且获得此划线的首尾的上下坐标，以显示dialog
     * 1.获取坐标
     * 2.获取划线的坐标
     * 3.获取划线的上下值
     */
    public Map<String, Object> mathSummaryByCoordinate(float x, float y) {
        try {
            int linesNum = 0;
            boolean end = true;
            for (int i = 0; i < linesVe.size(); i++) {
                if (linesVe.get(i).getCoordinateY() > y && end) {
                    linesNum = i;
                    end = false;
                }
            }
            for (int i = 0; i < linesVe.get(linesNum).getContent().length(); i++) {
                if (paintContent.measureText(linesVe.get(linesNum).getContent().substring(0, i + 1)) + margin < x && paintContent.measureText(linesVe.get(linesNum).getContent().substring(0, i + 2)) + margin > x) {
                    int position = linesVe.get(linesNum).getFirstpos() + i + 1;
                    if (summaryList != null) {
                        for (int j = 0; j < summaryList.size(); j++) {
                            if (position <= summaryList.get(j).getEndPos() && position >= summaryList.get(j).getStartPos()) {
                                Map<String, Object> map = new HashMap<>();
                                singleSummary = summaryList.get(j);
                                int minY = -1;
                                int maxY = -1;
                                for (int k = 0; k < linesVe.size(); k++) {
                                    if (summaryList.get(j).getStartPos() >= linesVe.get(k).getFirstpos() && summaryList.get(j).getStartPos() <= linesVe.get(k).getLastpos()) {
                                        minY = (int) (linesVe.get(k).getCoordinateY() - lineHgight);
                                    }
                                    if (summaryList.get(j).getEndPos() >= linesVe.get(k).getFirstpos() && summaryList.get(j).getEndPos() <= linesVe.get(k).getLastpos()) {
                                        maxY = (int) (linesVe.get(k).getCoordinateY());
                                    }
                                }
                                if (minY != -1) {
                                    map.put("minY", minY);
                                } else {
                                    map.put("minY", (int) (linesVe.get(0).getCoordinateY() - lineHgight));
                                }
                                if (maxY != -1) {
                                    map.put("maxY", maxY);
                                } else {
                                    map.put("maxY", (int) (linesVe.get(linesVe.size() - 1).getCoordinateY()));
                                }
                                return map;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 划线方法封装
     */
    public void drawLineByPos(Line line, int startPos, int endPos, Canvas c, float height, Paint paint) {
        float offset = PixelUtil.dp2px(6);
        height = height + offset;
        if (line.getFirstpos() <= startPos && line.getLastpos() >= startPos && line.getLastpos() <= endPos) {
            float leftMagin = margin + paint.measureText(line.getContent().substring(0, startPos - line.getFirstpos()));
            c.drawLine(leftMagin, height, margin + paint.measureText(line.getContent()), height, paintSummary);
            return;
        }
        if (line.getFirstpos() >= startPos && line.getLastpos() <= endPos) {
            c.drawLine(margin + paint.measureText(line.getContent()) - paint.measureText(line.getContent().replaceAll("[ |　]", " ").trim()),
                    height, margin + paint.measureText(line.getContent()), height, paintSummary);
            return;
        }
        if (line.getFirstpos() >= startPos && line.getLastpos() > endPos && line.getFirstpos() <= endPos) {
            float leftMagin = margin + paint.measureText(line.getContent()) - paint.measureText(line.getContent().replaceAll("[ |　]", " ").trim());
            float rightMagin = margin + paint.measureText(line.getContent().substring(0, line.getContent().length() - line.getLastpos() + endPos));
            c.drawLine(leftMagin, height, rightMagin, height, paintSummary);
            return;
        }
        if (line.getFirstpos() < startPos && line.getLastpos() > endPos) {
            float leftmargin = margin + paint.measureText(line.getContent().substring(0, startPos - line.getFirstpos()));
            float rightmargin = margin + paint.measureText(line.getContent().substring(0, endPos - line.getFirstpos()));
            c.drawLine(leftmargin, height, rightmargin, height, paintSummary);
        }
    }

    /**
     * 增加划线方法封装
     */
    public void drawRectByPos(Line line, int startPos, int endPos, Canvas c, float height, Paint paint) {
        float offset = PixelUtil.dp2px(6);
        height = height + offset;
        if (line.getFirstpos() <= startPos && line.getLastpos() >= startPos && line.getLastpos() <= endPos) {
            float leftMagin = margin + paint.measureText(line.getContent().substring(0, startPos - line.getFirstpos()));
            c.drawRect(leftMagin, height + 6 - lineHgight, margin + paint.measureText(line.getContent()), height, paintSelection);
            return;
        }
        if (line.getFirstpos() >= startPos && line.getLastpos() <= endPos) {
            c.drawRect(margin + paint.measureText(line.getContent()) - paint.measureText(line.getContent().replaceAll("[ |　]", " ").trim()),
                    height + 6 - lineHgight, margin + paint.measureText(line.getContent()), height, paintSelection);
            return;
        }
        if (line.getFirstpos() >= startPos && line.getLastpos() > endPos && line.getFirstpos() <= endPos) {
            float leftMagin = margin + paint.measureText(line.getContent()) - paint.measureText(line.getContent().replaceAll("[ |　]", " ").trim());
            float rightMagin = margin + paint.measureText(line.getContent().substring(0, line.getContent().length() - line.getLastpos() + endPos));
            c.drawRect(leftMagin, height + 6 - lineHgight, rightMagin, height, paintSelection);
            return;
        }
        if (line.getFirstpos() < startPos && line.getLastpos() > endPos) {
            float leftmargin = margin + paint.measureText(line.getContent().substring(0, startPos - line.getFirstpos()));
            float rightmargin = margin + paint.measureText(line.getContent().substring(0, endPos - line.getFirstpos()));
            c.drawRect(leftmargin, height + 6 - lineHgight, rightmargin, height, paintSelection);
        }
    }


    /**
     * 判断当前页是否有书签
     */
    public boolean isSetMarkShow() {
        if (pageNum == -1) {
            return false;
        }
        Page page = pageList.get(pageNum <= pageList.size() - 1 ? pageNum : pageList.size() - 1);
        if (markList != null && markList.size() > 0) {
            for (int i = 0; i < markList.size(); i++) {
                int centerPos = (markList.get(i).getStartPos() + markList.get(i).getEndPos()) / 2;
                if (centerPos >= page.getFirstpos() && centerPos <= page.getLastpos()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 删除当前页书签
     */
    public void detCurpageMark() {
        Page page = pageList.get(pageNum <= pageList.size() - 1 ? pageNum : pageList.size() - 1);
        if (markList != null && markList.size() > 0) {
            for (int i = 0; i < markList.size(); i++) {
                int centerPos = (markList.get(i).getStartPos() + markList.get(i).getEndPos()) / 2;
                if (centerPos >= page.getFirstpos() && centerPos <= page.getLastpos()) {
                    DBService.deteleBookMark(markList.get(i).getStartPos(), markList.get(i).getEndPos(), markList.get(i).getBookid());
                    markList.remove(i);
                }
            }
        }
    }

    /**
     * 刷新书签和划线
     */
    public void reflushMarkWithSummary() {
        if (preChapterForReader != null) {
            preChapterForReader.setMarkList(DBService.queryBookMark(preChapterForReader.getTag(), preChapterForReader.getChapterId()));
            preChapterForReader.setSummaryList(DBService.queryBookSummary(preChapterForReader.getTag(), preChapterForReader.getChapterId()));
        }
        if (chapterForReaderList != null && chapterForReaderList.size() > 0) {
            for (int i = 0; i < chapterForReaderList.size(); i++) {
                chapterForReaderList.get(i).setMarkList(DBService.queryBookMark(chapterForReaderList.get(i).getTag(), chapterForReaderList.get(i).getChapterId()));
                chapterForReaderList.get(i).setSummaryList(DBService.queryBookSummary(chapterForReaderList.get(i).getTag(), chapterForReaderList.get(i).getChapterId()));
            }
        }
        if (chapterForReaderList != null && chapterForReaderList.size() > 0) {
            markList = chapterForReaderList.get(0).getMarkList();
            summaryList = chapterForReaderList.get(0).getSummaryList();
        }
    }


    public int getChapterForReaderListSize() {
        return chapterForReaderList.size();
    }

    public Page getCurpage() {
        return pageList.get(pageNum <= pageList.size() - 1 ? pageNum : pageList.size() - 1);
    }

    public List<ChapterForReader> getChapterForReaderList() {
        return chapterForReaderList;
    }

    public float getLineHgight() {
        return lineHgight;
    }
    //    public void setContentHeight(boolean showstatue){
    //        if (showstatue){
    //            this.screenHeight = BaseActivity.getScreenHeight() - PixelUtil.dp2px(25);
    //        }else {
    //            this.screenHeight = BaseActivity.getScreenHeight();
    //        }
    //        visibleHeight = screenHeight - margin * 2;//屏幕可用于绘制内容的高度
    //        contentHeight = (int) (visibleHeight - headerHeight - footerHeight - 1);
    //    }
}
