package com.example.wuzp.secondworld.view.nio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.example.wuzp.secondworld.R;
import java.io.File;
import java.util.List;

/**
 * Created by wuzp on 2017/6/7.
 * 关注新语言在旧的语言上有什么提升还是很重要的，因为在语言上就存在很多的性能的提升，语言本身存在，只不过你不知道而已
 * 这里希望使用 Java4 中新的技术 nio (new input or output)
 * Path Paths Files WriterService ~ (还有一个东西记不住名字了)
 * 想想看看 能不能使用这些新的技术来加快数据的访问速度
 * 在安卓上不能使用nio的框架 不知道为什么
 */
public class NioActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnScan;
    private ListView list;

    //初始路径
    private String mOrigin = "/storage/sdcard0";//SDCard的路径
    //当前的路径
    private String curPath = mOrigin;

    //当前路径下的各个文件的集合
    private List<File> curFiles;//路径和文件都是File

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nio);
        initView();
    }

    private void initView(){
       btnScan = (Button) findViewById(R.id.btn_scan);
        list = (ListView)findViewById(R.id.list);
        initData();
    }

    private void initData(){
       btnScan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_scan:
                scan();
                break;
        }
    }

    private void scan(){
        //java.nio.file
     //Path Paths Files WatchService FileSystem

    }

    private void doTestNio(){
      /*  Path rootPath = Paths.get("data");
        String fileToFind = File.separator + "README.txt";

        try {
            Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String fileString = file.toAbsolutePath().toString();
                    //System.out.println("pathString = " + fileString);

                    if(fileString.endsWith(fileToFind)){
                        System.out.println("file found at path: " + file.toAbsolutePath());
                        return FileVisitResult.TERMINATE;
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch(IOException e){
            e.printStackTrace();
        }*/
    }
}
