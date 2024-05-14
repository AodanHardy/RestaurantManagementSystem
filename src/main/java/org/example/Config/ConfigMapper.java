package org.example.Config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;


public class ConfigMapper {
    public static Config getConfig(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {

            return mapper.readValue(new File("config.json"), Config.class);

        } catch (Exception e) {
            return new Config();
        }
    }
}
