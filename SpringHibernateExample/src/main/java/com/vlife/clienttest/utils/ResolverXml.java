package com.vlife.clienttest.utils;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 封装的是解析xml文件的方法 1.首先实例化ResolverXML 2. 调用 assignXML来指定对哪一个xml文件进行操作 3.
 * 调用getNeedElement 来根据以一个属性俩确定到一个Element对象 4. 调用getElementValue 来获取到指定的属性的值
 * 
 * @author 高亚轩
 *
 */
public class ResolverXml {

	private static Element element2 = null;

	/**
	 * 用来指定XML
	 * 
	 * @param xmlPath xml文件的路径
	 * @return 这个XML文件的Element的对象
	 */

	public Element assignXML(String xmlPath) {

		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new File(xmlPath));
		} catch (DocumentException e) {
			// TODO 自动生成的 catch 块

			System.out.println("XML文件加载出错!请检查文件路径!");
			e.printStackTrace();
		}
		Element node = document.getRootElement();

		return node;

	}

	/**
	 * 用一个属性值来确认一个需要的elelment
	 * 
	 * @param element       把assignXML返回的对象传进去
	 * @param attributeName 用来确定element的属性值的名称
	 * @param value         用来确定element的属性值的value
	 * @return 定位到这个属性值的Element
	 */
	@SuppressWarnings("unchecked")
	public Element getNeedElement(Element element, String attributeName, String value) {

		Iterator<Element> itea = element.elementIterator();
		while (itea.hasNext()) {

			Element e = itea.next();

			List<Attribute> list2 = e.attributes();

			for (Attribute attr : list2) {

				if (attr.getName().equalsIgnoreCase(attributeName)) {

					if (attr.getText().equalsIgnoreCase(value)) {

						return e;

					}
				}

			}
			List<Element> list = e.elements();
			if (list.isEmpty()) {
				continue;
			} else {

				element2 = getNeedElement(e, attributeName, value);
			}

		}
		return element2;

	}

	/**
	 * 用来获取XML对象的对应属性名名的值
	 * 
	 * @param element           getNeedElement返回的对象传进去
	 * @param needAttributeName 需要这个element对象的那个属性名的值
	 * @return 返回需要属性名的值
	 */

	@SuppressWarnings("unchecked")
	public String getElementValue(Element element, String needAttributeName) {

		if (element == null) {
			return "element为null";
		}
		List<Attribute> list = element.attributes();
		for (Attribute attr : list) {
			if (attr.getName().equalsIgnoreCase(needAttributeName)) {
				return attr.getText();
			}
		}
		return "未找到!";

	}

	/**
	 * 用来计算出图标的中心点
	 * 
	 * @param coordinates 例如: [287,375][530,687]
	 * @return 返回的是上面那两组坐标的中心点
	 */

	public int[] calculateCentre(String coordinates) {

		String str = coordinates.trim();

		int i = str.indexOf("[");
		int j = str.lastIndexOf("]");
		String temp = str.substring(i + 1, j);
		int q = str.indexOf(",");
		int w = str.lastIndexOf(",");

		int k = temp.indexOf("[");
		int l = temp.indexOf("]");

		String a = temp.substring(0, q - 1);
		String b = temp.substring(q, l);

		String c = temp.substring(k + 1, w - 1);
		String d = temp.substring(w, temp.length());

		int x = Integer.parseInt(a);
		int y = Integer.parseInt(b);
		int x1 = Integer.parseInt(c);
		int y1 = Integer.parseInt(d);

		int centrex = (x1 - x) / 2 + x;
		int centrey = (y1 - y) / 2 + y;

		int arr[] = new int[] { centrex, centrey };
		return arr;
	}

}
