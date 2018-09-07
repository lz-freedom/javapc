package Github.lz632339942.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class fnpc {

	public static void main(String[] args) throws Exception {
		fnpc a = new fnpc();
		a.getHtml();
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getHtml() throws Exception{
		ImgDown xz = new ImgDown();
		HashSet pdcm = new HashSet();
		Document doc = Jsoup.connect("https://photo.fengniao.com/f_595.html").get();
		
		//��ȡԪ�ؽڵ��,�۲���ȡ��ҳ��HTMLԴ����
		List<Element> infoListEle = doc.getElementsByAttributeValue("class", "noRight");
		infoListEle.forEach(element -> {
            System.out.println(element.getElementsByAttributeValue("class", "txtBox overOneTxt").first().getElementsByAttributeValue("class", "title").text());
            String title = element.getElementsByAttributeValue("class", "txtBox overOneTxt").first().getElementsByAttributeValue("class", "title").text();
            
            //�����ж������Ƿ��ظ����ظ����ں����һ��6λ���������������д��Ҫ
            if (!(pdcm.add(title)))title = title + "NO."+(int)(Math.random() * 1000000);  
            
            //ȥ�������еġ�:�����ţ�������д��Ҫ
            title = title.replace(":", "");
            
            System.out.println(element.getElementsByAttributeValue("class", "txtBox overOneTxt").first().getElementsByAttributeValue("class", "peopleName overOneTxt").text());
            String zz = element.getElementsByAttributeValue("class", "txtBox overOneTxt").first().getElementsByAttributeValue("class", "peopleName overOneTxt").text();
            System.out.println(element.getElementsByAttributeValue("class", "pic").attr("style"));
            String url= element.getElementsByAttributeValue("class", "pic").attr("style");
            
            //���ڻ�ȡ��ͼƬ�����а���background-image: url('')��һЩ��׺��ǰ���ʽ�̶�������ֱ����ȡ�м��url
            url = url.substring(23, url.length()-43);
            
            //���õ������������Լ�ͼƬurl����ɲ�����Ҫ�ĸ�ʽ
            //String Makedown = title+":\n  full_link: "+url+"\n  thumb_link: "+url+"\n  descr: "+zz;
            String Makedown = title+":\n  full_link: /images/tk"+url.substring(url.lastIndexOf("/"))+"\n  thumb_link: /images/tk"+url.substring(url.lastIndexOf("/"))+"\n  descr: "+zz;
            
            //������д���ļ�����
            writeFile("D:\\HEXO\\HEXO\\source\\_data\\gallery.yml",Makedown);
            
            //����ͼƬ������д��һ���� ImgDown
            xz.downImages("D:\\HEXO\\HEXO\\themes\\hexo-theme-Mic_Theme\\source\\images\\tk", url);   
            
        });
	}
	
	
	//�˴������ǰѴ������ַ���д���ļ�
	public static void writeFile(String fileFullPath,String content) {
		//�տ�ʼFileOutputStream���Ļ����룬���Ի���OutputStreamWriter
        //FileOutputStream fos = null;          
        OutputStreamWriter oStreamWriter = null;
        try {
            //true��������������
            //fos = new FileOutputStream(fileFullPath, true);
        	oStreamWriter = new OutputStreamWriter(new FileOutputStream(fileFullPath, true), "utf-8");
        	
            //д��
            //fos.write(content.getBytes());
            oStreamWriter.append(content);
            
            // д��һ������
            //fos.write("\r\n\r\n".getBytes());
            oStreamWriter.append("\r\n\r\n");  
            
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(oStreamWriter != null){
                try {
                    //fos.flush();
                    oStreamWriter.flush();
                    //fos.close(); 
                    oStreamWriter.close();
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
}
	