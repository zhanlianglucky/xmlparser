package com.hzl.xmlparer.xml;

import com.hzl.xmlparer.xml.bean.BaseBean;
import com.hzl.xmlparer.xml.bean.OrderBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ParserUtil {
    private static XStream xStream = new XStream(new DomDriver());
    private static XStream xJson = new XStream(new JsonHierarchicalStreamDriver());
    static{
        XStream.setupDefaultSecurity(xStream);
        //TODO 注意：新增的类要初始化进去
        xStream.allowTypes(new Class[]{
                BaseBean.class,
                OrderBean.class
        });
    }

    public static String parseXml(Object obj){
        return xStream.toXML(obj);
    }
    public static String parseXml(String alias,Object obj){
        xStream.alias(alias,obj.getClass());
        return parseXml(obj);
    }
    public static String parseJson(String alias,Object obj){
        xJson.alias(alias,obj.getClass());
        return xJson.toXML(obj);
    }

    public static Object fromXml(String xml){
        return xStream.fromXML(xml);
    }

    public static void main(String [] args){

        OrderBean orderBean = new OrderBean();
        orderBean.setOrderId("001");
        orderBean.setOrderName("order名称");
        String xml = ParserUtil.parseXml("OrderBean",orderBean);
        System.out.println(xml);

        OrderBean orderBean2 = (OrderBean) fromXml(xml);
        System.out.println(orderBean2.getOrderId()+" : "+orderBean2.getOrderName());

        xJson.alias("orderbean",OrderBean.class);
        System.out.println(xJson.toXML(orderBean));
    }

}
