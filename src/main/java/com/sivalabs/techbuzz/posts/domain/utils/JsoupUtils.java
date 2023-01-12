package com.sivalabs.techbuzz.posts.domain.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsoupUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsoupUtils.class);

    public static String getTitle(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            return doc.title();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return url;
    }
}
