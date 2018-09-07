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
		
		//获取元素节点等,观察爬取网页的HTML源代码
		List<Element> infoListEle = doc.getElementsByAttributeValue("class", "noRight");
		infoListEle.forEach(element -> {
            System.out.println(element.getElementsByAttributeValue("class", "txtBox overOneTxt").first().getElementsByAttributeValue("class", "title").text());
            String title = element.getElementsByAttributeValue("class", "txtBox overOneTxt").first().getElementsByAttributeValue("class", "title").text();
            
            //这里判断名字是否重复，重复会在后面加一个6位的随机数，博客书写需要
            if (!(pdcm.add(title)))title = title + "NO."+(int)(Math.random() * 1000000);  
            
            //去除名字中的“:”符号，博客书写需要
            title = title.replace(":", "");
            
            System.out.println(element.getElementsByAttributeValue("class", "txtBox overOneTxt").first().getElementsByAttributeValue("class", "peopleName overOneTxt").text());
            String zz = element.getElementsByAttributeValue("class", "txtBox overOneTxt").first().getElementsByAttributeValue("class", "peopleName overOneTxt").text();
            System.out.println(element.getElementsByAttributeValue("class", "pic").attr("style"));
            String url= element.getElementsByAttributeValue("class", "pic").attr("style");
            
            //由于获取的图片链接中包括background-image: url('')和一些后缀，前后格式固定，所以直接提取中间的url
            url = url.substring(23, url.length()-43);
            
            //将得到的名字作者以及图片url处理成博客需要的格式
            //String Makedown = title+":\n  full_link: "+url+"\n  thumb_link: "+url+"\n  descr: "+zz;
            String Makedown = title+":\n  full_link: /images/tk"+url.substring(url.lastIndexOf("/"))+"\n  thumb_link: /images/tk"+url.substring(url.lastIndexOf("/"))+"\n  descr: "+zz;
            
            //将内容写到文件里面
            writeFile("D:\\HEXO\\HEXO\\source\\_data\\gallery.yml",Makedown);
            
            //下载图片，另外写了一个类 ImgDown
            xz.downImages("D:\\HEXO\\HEXO\\themes\\hexo-theme-Mic_Theme\\source\\images\\tk", url);   
            
        });
	}
	
	
	//此处作用是把处理后的字符串写入文件
	public static void writeFile(String fileFullPath,String content) {
		//刚开始FileOutputStream中文会乱码，所以换成OutputStreamWriter
        //FileOutputStream fos = null;          
        OutputStreamWriter oStreamWriter = null;
        try {
            //true不覆盖已有内容
            //fos = new FileOutputStream(fileFullPath, true);
        	oStreamWriter = new OutputStreamWriter(new FileOutputStream(fileFullPath, true), "utf-8");
        	
            //写入
            //fos.write(content.getBytes());
            oStreamWriter.append(content);
            
            // 写入一个换行
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
	