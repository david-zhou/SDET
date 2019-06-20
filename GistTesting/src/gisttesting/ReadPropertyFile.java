/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gisttesting;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author davidzhou
 */
public class ReadPropertyFile {
    private static Properties properties;
    public static void loadFile(String url) throws IOException {
        properties = new Properties();
        FileInputStream ip= new FileInputStream(url);
        properties.load(ip);
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
