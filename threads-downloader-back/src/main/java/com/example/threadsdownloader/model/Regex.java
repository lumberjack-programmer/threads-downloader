package com.example.threadsdownloader.model;

public enum Regex {
    REGEX_PATH_URL("^https\\:\\/\\/scontent\\.cdninstagram\\.com\\/v\\/.*\\/([^.*]+\\.(?:mp4|jpg))");
    public final String regex;
    Regex(String regex) {
        this.regex = regex;
    }
}
