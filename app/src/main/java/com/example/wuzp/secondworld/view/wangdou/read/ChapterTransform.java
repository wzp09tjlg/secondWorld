package com.example.wuzp.secondworld.view.wangdou.read;


import com.example.wuzp.secondworld.view.wangdou.db.DBService;
import com.example.wuzp.secondworld.view.wangdou.model.parse.book.Book;
import com.example.wuzp.secondworld.view.wangdou.model.parse.book.Chapter;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 适配器模式思路，后期改进
 * Created by zyb on 2016/11/1.
 */
public class ChapterTransform {
    /**
     * 将在线下载书籍转化为阅读器适配模式
     * 暂时缺省书签和划线信息
     *
     * @param bookid bookid
     * @return ChapterForReader
     */
    public static ChapterForReader getChapterByDownloadBook(String bookid, String chapterid) {
        ChapterForReader chapterForReader = new ChapterForReader();
        Book book = DBService.queryBooksInfoBybookid(bookid);
        String filepath = book.getFilePath();
        File file = new File(filepath);
        Chapter customChapter = DBService.queryChapterInfoByTagWithCid(bookid, chapterid);
        int position = DBService.queryChapterPosByTagWithChapterId(bookid, chapterid);
        if (customChapter != null) {
            chapterForReader.setTitle(customChapter.getTitle());
            chapterForReader.setOrder(position);
            FileChannel channel;
            MappedByteBuffer buffer;
            try {
                RandomAccessFile readfile = new RandomAccessFile(file, "r");
                channel = readfile.getChannel();
                buffer = channel.map(FileChannel.MapMode.READ_ONLY, customChapter.getStartPos(), (int) customChapter.getLength());
                String stringValue = byteBufferToString(buffer, "utf-8");
                if (stringValue != null) {
                    stringValue = stringValue.replaceAll("\n", "\n" + "　　");
                }
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("\n" + customChapter.getTitle() + "\n");
                stringBuffer.append(stringValue);

              //  chapterForReader.setContent(StringEscapeUtils.unescapeHtml3(StringEscapeUtils.unescapeHtml3(stringBuffer.toString())));
            } catch (Exception e) {
                e.printStackTrace();
                customChapter.setLength(0);
                List<Chapter> chapters = new ArrayList<>();
                chapters.add(customChapter);
                DBService.saveChapterInfo(chapters);
            }
        }
        chapterForReader.setChapterId(chapterid);
        chapterForReader.setBookid(bookid);
        chapterForReader.setTag(bookid);
        chapterForReader.setMarkList(DBService.queryBookMark(bookid, chapterid));
        chapterForReader.setSummaryList(DBService.queryBookSummary(bookid, chapterid));
        return chapterForReader;
    }

    /**
     * 将在线阅读书籍转化为阅读器适配章节
     * 缺省书签及划线信息
     *
     * @param
     * @return ChapterForReader
     */
  /*  public static ChapterForReader getChapterByOnlineRead(final ChapterSingle chapterSingle) {
        final ChapterForReader chapterForReader = new ChapterForReader();
//        int position = DBService.queryChapterPosByTagWithChapterId(chapterSingle.getBook_id(), chapterSingle.getChapter_id());
//        chapterForReader.setOrder(position);
//        chapterForReader.setTitle(chapterSingle.getTitle());
//        chapterForReader.setTag(chapterSingle.getBook_id());
//        String content = new String(Base64.decode(chapterSingle.getContent().getBytes()));
//        content = content.replaceAll("\n", "\n" + "　　");
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append("\n" + chapterSingle.getTitle() + "\n");
//        stringBuffer.append(content);
//        chapterForReader.setContent(StringEscapeUtils.unescapeHtml3(stringBuffer.toString()));
//        chapterForReader.setChapterId(chapterSingle.getChapter_id());
//        chapterForReader.setBookid(chapterSingle.getBook_id());
//        chapterForReader.setMarkList(DBService.queryBookMark(String.valueOf(chapterSingle.getBook_id()), String.valueOf(chapterSingle.getChapter_id())));
//        chapterForReader.setSummaryList(DBService.queryBookSummary(String.valueOf(chapterSingle.getBook_id()), String.valueOf(chapterSingle.getChapter_id())));
        return chapterForReader;
    }*/

    public static ChapterForReader getChapterByLocal(String filepath, String chapterid) {
        ChapterForReader chapterForReader = new ChapterForReader();
        int position = DBService.queryChapterPosByTagWithChapterId(filepath, chapterid);
        chapterForReader.setOrder(position);
        File file = new File(filepath);
        Chapter customChapter = DBService.queryChapterInfoByTagWithCid(filepath, chapterid);
        if (customChapter != null) {
            chapterForReader.setTitle(customChapter.getTitle());
            FileChannel channel = null;
            MappedByteBuffer buffer;
            try {
                RandomAccessFile readfile = new RandomAccessFile(file, "r");
                channel = readfile.getChannel();
                buffer = channel.map(FileChannel.MapMode.READ_ONLY, customChapter.getStartPos(), (int) customChapter.getLength());
                String stringValue = "";//byteBufferToString(buffer, FileUtils.getFileIncode(file));
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("\n" + customChapter.getTitle() + "\n");
                stringBuffer.append(stringValue);
                chapterForReader.setContent(stringBuffer.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (channel != null) {
                        channel.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        chapterForReader.setTag(filepath);
        chapterForReader.setChapterId(chapterid);
        chapterForReader.setMarkList(DBService.queryBookMark(filepath, chapterid));
        chapterForReader.setSummaryList(DBService.queryBookSummary(filepath, chapterid));
        return chapterForReader;
    }

    public static String byteBufferToString(ByteBuffer buffer, String encode) {
        CharBuffer charBuffer;
        try {
            Charset charset = Charset.forName(encode);
            charBuffer = charset.decode(buffer);
            buffer.flip();
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


}
