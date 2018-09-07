# **JAVA图片爬虫**

## **用于本博客的图库建设，爬取网站为蜂鸟摄影**

### **首先观察网站的源码，用的Google Chrome，F12就能看到**
![cmd-markdown-logo](https://ww1.sinaimg.cn/large/005zWjpngy1fv14cjekf9j30f60bat9b.jpg)

### **下面贴代码**

#### **使用此代码需要导入的jar包会在后面补图**

```java
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
            System.out.println(element.getElementsByAttributeValue("class", "txtBox overOneTxt")
				.first().getElementsByAttributeValue("class", "title").text());
				
            String title = element.getElementsByAttributeValue("class", "txtBox overOneTxt").first()
				.getElementsByAttributeValue("class", "title").text();
            
            //这里判断名字是否重复，重复会在后面加一个6位的随机数，博客书写需要
            if (!(pdcm.add(title)))title = title + "NO."+(int)(Math.random() * 1000000);  
            
            //去除名字中的“:”符号，博客书写需要
            title = title.replace(":", "");
            
            System.out.println(element.getElementsByAttributeValue("class", "txtBox overOneTxt")
				.first().getElementsByAttributeValue("class", "peopleName overOneTxt").text());
				
            String zz = element.getElementsByAttributeValue("class", "txtBox overOneTxt")
				.first().getElementsByAttributeValue("class", "peopleName overOneTxt").text();
				
            System.out.println(element.getElementsByAttributeValue("class", "pic").attr("style"));
            String url= element.getElementsByAttributeValue("class", "pic").attr("style");
            
            //由于获取的图片链接中包括background-image: url('')和一些后缀，前后格式固定，所以直接提取中间的url
            url = url.substring(23, url.length()-43);
            
            //将得到的名字作者以及图片url处理成博客需要的格式
            //String Makedown = title+":\n  full_link: "+url+"\n  thumb_link: "+url+"\n  descr: "+zz;
			
            String Makedown = title+":\n  full_link: /images/tk"+url.substring(url.lastIndexOf("/"))
				+"\n  thumb_link: /images/tk"+url.substring(url.lastIndexOf("/"))+"\n  descr: "+zz;
            
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
```

#### **这里是获取图片URL后用于下载图片到本地的class**

```java
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

```
#### **还有一个用于JS动态网页内容获取的类（蜂鸟摄影是静态网页，所以没用到）**

```java
package Github.lz632339942.io;

import java.util.List;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class JS_DT {

	public static void main(String[] args) {
		JS_DT Cs = new JS_DT();
		Cs.test();

	}
	
	//这个是用来获取js动态网页内容的
	public void test() {
		//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		
		//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnScriptError(false);
		
		//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setActiveXNative(false);
		
		//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setCssEnabled(false);
		
		//很重要，启用JS
        webClient.getOptions().setJavaScriptEnabled(true); 
		
		//很重要，设置支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        HtmlPage page = null;
        try {
		
			//尝试加载上面图片例子给出的网页
            page = webClient.getPage("http://localhost:4000/gallery/");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            webClient.close();
        }
		
		//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束
        webClient.waitForBackgroundJavaScript(30000);

		//直接将加载完成的页面转换成xml格式的字符串
        String pageXml = page.asXml();

        //TODO 下面的代码就是对字符串的操作了,常规的爬虫操作,用到了比较好用的Jsoup库
		
		//获取html文档
        Document document = Jsoup.parse(pageXml);
        System.out.println(document);
		
		//获取元素节点等
        List<Element> infoListEle = document.getElementsByAttributeValue("class", "photo_thumbnail jg-entry entry-visible");
        
        infoListEle.forEach(element -> {
            System.out.println(element.getElementsByTag("div").first().getElementsByTag("span").text());
            System.out.println(element.getElementsByTag("a").first().getElementsByTag("img").attr("src"));
        });
    }

}

```
### **这是需要导入的jar包，Jsoup和Htmlunit**
![cmd-markdown-logo](https://ww1.sinaimg.cn/large/005zWjpngy1fv04n3f3b9j30ab0f5mxr.jpg)

# **各位高手多多指导**