package com.ecnice.privilege.service.tokenAuthtication.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAP11Constants;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ecnice.privilege.constant.PrivilegeConstant;
import com.ecnice.privilege.service.tokenAuthtication.ITokenAuthtication;

//废弃
//@Service
public class TokenAuthticationImpl implements ITokenAuthtication {

	// 服务器地址

	private static EndpointReference targetEPR =null;
	private static Properties props = new Properties();
	static {
		try {
			InputStream pis = TokenAuthticationImpl.class.getClassLoader().getResourceAsStream("config/application.properties");
			props.load(pis);
			targetEPR = new EndpointReference(props.getProperty("idm_url"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getTokenResult(String Token, String Appkey) throws Exception {
		ServiceClient sender = new ServiceClient();
		sender.setOptions(buildSendTokenOptions());
		OMElement result = sender.sendReceive(buildSendTokenParam(Token, Appkey));
		// System.out.println(String.valueOf(result));
		loopfunc(result);
		System.out.println(String.valueOf(result));
		//return "admin";
		return parseXml(String.valueOf(result));
	}
	
	public static OMElement buildSendTokenParam(String Token, String Appkey) {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		// 命名空间
		OMNamespace omNs = fac.createOMNamespace("http://tempuri.org/", "");
		// 最外层元素
		OMElement data = fac.createOMElement("AuthticationByToken", omNs);
		// 内层元素
		OMElement inner = fac.createOMElement("Token", omNs);
		inner.setText(Token);
		data.addChild(inner);
		OMElement inner1 = fac.createOMElement("Appkey", omNs);
		inner1.setText(Appkey);
		data.addChild(inner1);
		return data;
	}

	private static Options buildSendTokenOptions() {
		Options options = new Options();
		options.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
		options.setAction("http://tempuri.org/AuthticationByToken");
		options.setTo(targetEPR);
		options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
		//options.setProperty(HTTPConstants.CHUNKED, "false");//设置不受限制.
		return options;
	}
 //-----------------------------------------------------------------------------------------
	public void getResetTokenResult(String Token, String Appkey) throws Exception {
		ServiceClient sender = new ServiceClient();
		sender.setOptions(buildResetTokenOptions());
		OMElement result = sender.sendReceive(buildResetTokenParam(Token, Appkey));
		// System.out.println(String.valueOf(result));
		/*loopfunc(result);
		return parseXml(String.valueOf(result));*/
	}
	
	public static OMElement buildResetTokenParam(String Token, String Appkey) {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		// 命名空间
		OMNamespace omNs = fac.createOMNamespace("http://tempuri.org/", "");
		// 最外层元素
		OMElement data = fac.createOMElement("ReletTokenByToken", omNs);
		// 内层元素
		OMElement inner = fac.createOMElement("Token", omNs);
		inner.setText(Token);
		data.addChild(inner);
		OMElement inner1 = fac.createOMElement("Appkey", omNs);
		inner1.setText(Appkey);
		data.addChild(inner1);
		return data;
	}

	private static Options buildResetTokenOptions() {
		Options options = new Options();
		options.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
		options.setAction("http://tempuri.org/ReletTokenByToken");
		options.setTo(targetEPR);
		options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
		return options;
	}
	
	
	public static void main(String[] args) {
		TokenAuthticationImpl demo = new TokenAuthticationImpl();
		String token = "74B76D356A7CC573E6F503FEBE26239BFD79B93B81E9E7210B9053879435771F049D36C8EFC44F137E6170B7FAEB2226272F65F30F051276B0C71A96E151A1AA";
		String Appkey = PrivilegeConstant.EHR_APPKEY;// 7D7C4A98-B7FD-4DA2-BDA2-CE1FC9B3B48F
		try {
			String result = demo.getTokenResult(token, Appkey);
			System.out.println("sendToekn>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : "+result);
		    demo.getResetTokenResult(token, Appkey);
			//System.out.println("resetToken>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : "+result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 将字符串转为XML 并解析数据 返回工号
	public  String parseXml(String xmlStr) {
		String workNum="";
		try {
			// 格式化xml
			xmlStr=fmtXmlStr(xmlStr);
			Document doc = DocumentHelper.parseText(xmlStr);
			Element root = doc.getRootElement();
			Element AuthticationByTokenResult = root.element("AuthticationByTokenResult");
			Element Info = AuthticationByTokenResult.element("Info");
			Element UserInfo = Info.element("UserInfo");
			Element EmployeeID = UserInfo.element("EmployeeID");
			if (null != EmployeeID) {
				workNum=EmployeeID.getTextTrim();
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return workNum;
	}
	
	// 格式化xml
	public  String fmtXmlStr(String xmlStr){
		xmlStr = xmlStr.replace("<;", "&lt;");
		xmlStr = xmlStr.replace("&amp;", "&");
		xmlStr = xmlStr.replace("&nbsp;", " ");
		xmlStr = xmlStr.replace("&gt;", ">");
		xmlStr = xmlStr.replace("&lt;", "<");
		xmlStr = xmlStr.replace("<br>", "\n");
		xmlStr = xmlStr.replace("&quot;", "\"");
		xmlStr = xmlStr.replace("&quot;", "\'");
		return xmlStr;
	}
	
	public static int loopfunc(OMElement ome) {
		Iterator iterator_layer1 = ome.getChildElements();
		if (ome == null) {
			// System.out.println( "ome is null");
			return 1;
		}
		Iterator it1 = ome.getChildElements();
		while (it1.hasNext()) {
			OMElement ome_l2 = (OMElement) it1.next();
			// System.out.println(ome_l2.getLocalName() + " -> " +
			// ome_l2.getText());
			loopfunc(ome_l2);
		}
		return 1;
	}

}
