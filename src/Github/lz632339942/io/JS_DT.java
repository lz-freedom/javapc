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
	
	//�����������ȡjs��̬��ҳ���ݵ�
	public void test() {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);//�½�һ��ģ��ȸ�Chrome�������������ͻ��˶���

        webClient.getOptions().setThrowExceptionOnScriptError(false);//��JSִ�г����ʱ���Ƿ��׳��쳣, ����ѡ����Ҫ
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//��HTTP��״̬��200ʱ�Ƿ��׳��쳣, ����ѡ����Ҫ
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);//�Ƿ�����CSS, ��Ϊ����Ҫչ��ҳ��, ���Բ���Ҫ����
        webClient.getOptions().setJavaScriptEnabled(true); //����Ҫ������JS
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//����Ҫ������֧��AJAX

        HtmlPage page = null;
        try {
            page = webClient.getPage("http://localhost:4000/gallery/");//���Լ�������ͼƬ���Ӹ�������ҳ
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            webClient.close();
        }

        webClient.waitForBackgroundJavaScript(30000);//�첽JSִ����Ҫ��ʱ,���������߳�Ҫ����30��,�ȴ��첽JSִ�н���

        String pageXml = page.asXml();//ֱ�ӽ�������ɵ�ҳ��ת����xml��ʽ���ַ���

        //TODO ����Ĵ�����Ƕ��ַ����Ĳ�����,������������,�õ��˱ȽϺ��õ�Jsoup��

        Document document = Jsoup.parse(pageXml);//��ȡhtml�ĵ�
        System.out.println(document);
        List<Element> infoListEle = document.getElementsByAttributeValue("class", "photo_thumbnail jg-entry entry-visible");//��ȡԪ�ؽڵ��
        
        infoListEle.forEach(element -> {
            System.out.println(element.getElementsByTag("div").first().getElementsByTag("span").text());
            System.out.println(element.getElementsByTag("a").first().getElementsByTag("img").attr("src"));
        });
    }

}
