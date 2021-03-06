package com.ott.recommend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecommendConfig {
    @Value("${ottrecommend.filepath}")
    private String ottRecommendFilepath;
    @Value("${ottrecommend.authtoken}")
    private String ottRecommendAuthToken;

    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String AUTHTOKEN = "P@ssword!";

    @Bean
    public BestOTT loadBestOTT() {
        BestOTT bestOTT = new BestOTT();

        Map<String, String> datas = new HashMap<>();

        log.info("Auth Token ==> {}", ottRecommendAuthToken);
        if (!AUTHTOKEN.equals(ottRecommendAuthToken)) {
            log.error("##### Fail to authenticate !");
            return bestOTT;
        }

        String filepath = System.getProperty("user.home") + File.separator + ottRecommendFilepath;
        log.info("FILE PATH==>{}", filepath);

        String[] info = null;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filepath), StandardCharsets.UTF_8))) {
            String str;

            while ((str = reader.readLine()) != null) {
                log.info(str);
                if (!"".equals(str.trim())) {
                    info = str.split("=");
                    datas.put(info[0], info[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bestOTT.setBestOTTData(datas);

        return bestOTT;
    }
}
