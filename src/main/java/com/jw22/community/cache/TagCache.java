package com.jw22.community.cache;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TagCache {
    public static HashMap<String, List<String>> get() {
        HashMap<String, List<String>> tags = new HashMap<>();

        tags.put("language", Arrays.asList("java", "javascript", "css", "html", "python", "c", "c++", "golang", "c#", "x86"));
        tags.put("database", Arrays.asList("mongodb", "mysql", "redis", "nosql", "oracle"));
        tags.put("tool", Arrays.asList("github", "gitlab", "jet-brain", "vim", "visual-studio", "xcode"));

        return tags;
    }
}
