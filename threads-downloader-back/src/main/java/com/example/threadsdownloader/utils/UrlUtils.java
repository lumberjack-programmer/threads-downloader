package com.example.threadsdownloader.utils;

import com.example.threadsdownloader.model.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtils {

    public static String extractFileNameFromUrl(String url) {
        //https://scontent.cdninstagram.com/v/t51.2885-15/363888691_1014755109548791_6924222308216904193_n.jpg?stp=dst-jpg_e35_s640x640_sh0.08&_nc_ht=scontent.cdninstagram.com&_nc_cat=107&_nc_ohc=I_Ddx_JWdkoAX9akMJl&edm=APs17CUBAAAA&ccb=7-5&ig_cache_key=MzE1Nzc5MzQ0MTg2MTA3NTI2Mg%3D%3D.2-ccb7-5&oh=00_AfAdExfjCA-l8s3IurF9vpswyBFHDVdiufn2n38ycfePvw&oe=64DEE018&_nc_sid=10d13b
        if (url == null || !url.startsWith("https://")) {
            return null;
        }
        String[] parts = url.split("/");
        String fileNameWithQuery = parts[parts.length - 1];
        return fileNameWithQuery.split("\\?")[0];
    }

    public static boolean checkPathUrl(String pathUrl) {
        Pattern pattern = Pattern.compile(Regex.REGEX_PATH_URL.regex);
        Matcher matcher = pattern.matcher(pathUrl);
        return matcher.matches();
    }
}
