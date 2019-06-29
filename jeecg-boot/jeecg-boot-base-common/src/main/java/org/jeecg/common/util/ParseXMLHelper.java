package org.jeecg.common.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.xml.sax.InputSource;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class ParseXMLHelper {

    @SuppressWarnings("unused")
	private static final String xmlHead = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";

    private static final ConcurrentMap<String, XStream> xstreamMap = new ConcurrentHashMap<String, XStream>();

    private static XStream getXStream(Class<?> clazz) {
        String clazzName = clazz.getSimpleName();
        if (xstreamMap.get(clazzName) == null) {
            HierarchicalStreamDriver driver = null;
            driver = new Xpp3Driver(new XmlFriendlyNameCoder("$", "_"));
            XStream xstream = new XStream(driver);
            xstream.processAnnotations(clazz);
            xstream.ignoreUnknownElements();
            xstreamMap.putIfAbsent(clazzName, xstream);
        }
        return xstreamMap.get(clazzName);
    }

    @SuppressWarnings("unchecked")
    public static <T> T parseXMLToObject(String xml, Class<T> clazz) {
        XStream xStream = getXStream(clazz);
        return (T) xStream.fromXML(xml);
    }

    /**
     * 如果不需要定义xml头，在第三个参数里传入false
     * @param obj
     * @param clazz
     * @param includeHead
     * @return
     */
    public static String parseObjectToXML(Object obj, Class<?> clazz, boolean includeHead) {
        XStream xStream = getXStream(clazz);
        return includeHead ?  formatXml(xStream.toXML(obj)): xStream.toXML(obj);
    }

    public static String parseObjectToXML(Object obj, Class<?> clazz) {
        XStream xStream = getXStream(clazz);
        return formatXml(xStream.toXML(obj));
    }

    /**
     * 获取解析好的文档对象
     *
     * @param xml 字符流
     * @return 文档对象
     */
    public static Document getDocument(String xml) {
        Document document = null;
        try {
            document = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return document;
    }

    private static String formatXml(String xml) {
        ByteArrayOutputStream out =null;
        try {
            Transformer serializer = SAXTransformerFactory.newInstance().newTransformer();
            serializer.setOutputProperty(OutputKeys.INDENT, "no");
            serializer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            Source xmlSource = new SAXSource(new InputSource(new ByteArrayInputStream(xml.getBytes())));
            StreamResult res = new StreamResult(new ByteArrayOutputStream());
            serializer.transform(xmlSource, res);
            out = (ByteArrayOutputStream) res.getOutputStream();
            String str = new String(out.toByteArray());
            out.close();
            return str;
        } catch (Exception e) {
            return xml;
        }finally {
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("",e);
                }
            }
        }
    }

}
