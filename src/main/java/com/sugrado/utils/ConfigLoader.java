package com.sugrado.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    public static Properties getConfig(String name) {
        Properties prop;
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(String.format("%s.properties", name))) {
            prop = new Properties();
            prop.load(input);

        } catch (IOException ex) {
            throw new RuntimeException("Konfigürasyon dosyası okunamadı.");
        }
        return prop;
    }
}
