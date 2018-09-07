package Github.lz632339942.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImgDown {
	/**
     * ����ͼƬ��URL���ص�ͼƬ�����ص�filePath
     * @param filePath �ļ���
     * @param imageUrl ͼƬ����ַ
     */
    public void downImages(String filePath,String imageUrl){
        // ��ȡͼƬ������
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/"));
        
        //�����ļ���Ŀ¼�ṹ
        File files = new File(filePath);
        
        // �ж��ļ����Ƿ���ڣ���������ھʹ���һ���ļ���
        if(!files.exists()){           
            files.mkdirs();
        }
        
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            
            // �����ļ�
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
