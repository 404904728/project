package org.cq.util;

import java.util.Properties;
import java.util.ResourceBundle;

import com.jfinal.kit.PropKit;

/**
 * 配置文件获取
 *
 * @author hejian
 *
 */
public class ConfigLoad {

    /**
     * jfinal 自带的配置文件获取
     *
     * @return
     */
    public static Properties loadPropertyFile() {
        return loadPropertyFile("org/cq/hmq/config/db_config.txt", "UTF-8");
    }

    public static Properties loadPropertyFile(String fileName) {
        return loadPropertyFile(fileName, "UTF-8");
    }

    public static Properties loadPropertyFile(String fileName, String encoding) {
        return PropKit.use(fileName, encoding).getProperties();
    }

    /**
     * 获取class下文件的绝对路径
     *
     * @param classPath
     * @return
     */
    public static String loadPropertiesFilePath(String classPath) {
        //org/cq/hmq/config/" + xmlName + ".xml
        return Thread.currentThread().getContextClassLoader().getResource(classPath).getPath();
    }

    /**
     * java自带的配置文件获取
     *
     * @param key 键
     * @return
     */
    public static String getProPertiesValue(String key) {
        return ResourceBundle.getBundle("org/cq/hmq/config/config").getString(
                key);
    }

    /**
     * snaker 配置文件获取
     *
     * @param key 键
     * @return
     */
    public static String getProPertiesValue_snaker(String key) {
        return ResourceBundle.getBundle("snaker").getString(
                key);
    }

}
