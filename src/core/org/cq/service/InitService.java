package org.cq.service;

import java.util.Iterator;

import org.cq.util.LogUtil;
import org.cq.util.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class InitService {

	public static final InitService service = new InitService();

	public void read(String xmlName) {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			String xmlPath = Thread.currentThread().getContextClassLoader()
					.getResource("org/cq/config/" + xmlName + ".xml")
					.getPath();
			document = reader.read(xmlPath);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element root = document.getRootElement();
		for (Iterator it = root.elementIterator("pojo"); it.hasNext();) {
			Element pojo = (Element) it.next();
			String tableName = pojo.attributeValue("name") + "_t";
			String tableType = pojo.attributeValue("type");
			if (!StringUtil.isEmpty(tableType) && tableType.equals("update")) {
				for (Iterator itSql = pojo.elementIterator("sql"); itSql
						.hasNext();) {
					Element sql = (Element) itSql.next();
					String[] strSql = sql.getStringValue().split("//");
					LogUtil.info("update from " + tableName + " set "
							+ strSql[0] + " where " + strSql[1]);
					// dao.getHelperDao().excute(
					// "update " + tableName + " set " + strSql[0]
					// + " where " + strSql[1]);
					Db.update("update " + tableName + " set " + strSql[0]
							+ " where " + strSql[1]);
				}
			} else {
				for (Iterator itSql = pojo.elementIterator("sql"); itSql
						.hasNext();) {
					Element sql = (Element) itSql.next();
					String[] strSql = sql.getStringValue().split("//");
					LogUtil.info("insert into  " + tableName + "   ("
							+ strSql[0] + ")   " + "values   (" + strSql[1]
							+ ")");
					Record re = new Record();
					String[] values = strSql[1].split(",");
					String[] columns = strSql[0].split(",");
					for (int i = 0; i < columns.length; i++) {
						if (columns[i].indexOf("price") > -1) {
							if (!values[i].equals("null")) {
								re.set(columns[i],
										Double.parseDouble(values[i]));
							}
						} else {
							re.set(columns[i], values[i]);
						}
					}
					Db.save(tableName, re);
				}
			}
		}
	}
}
