package Github.lz632339942.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImgDown {
	/**
     * 根据图片的URL下载的图片到本地的filePath
     * @param filePath 文件夹
     * @param imageUrl 图片的网址
     */
    public void downImages(String filePath,String imageUrl){
        // 截取图片的名称
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/"));
        
        //创建文件的目录结构
        File files = new File(filePath);
        
        // 判断文件夹是否存在，如果不存在就创建一个文件夹
        if(!files.exists()){           
            files.mkdirs();
        }
        
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            
            // 创建文件
            File file = new File(filePath+fileName);
            FileOutputStream out = new FileOutputStream(file);
            int i = 0;
            while((i = is.read()) != -1){
                out.write(i);
            }
            is.close();
            out.close();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
