package me.kuuds.docker.client.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppUtils {

    public static String createFileName(String name) {
        String[] split = name.split("/");
        if (split.length < 1) {
            throw new IllegalArgumentException("invalid image name " + name);
        }
        String nameAndTag = split[split.length - 1];
        String[] nameAndTagSplit = nameAndTag.split(":");
        if (nameAndTagSplit.length != 2) {
            throw new IllegalArgumentException("invalid image name " + name);
        }
        String shortName = nameAndTagSplit[0];
        String tag = nameAndTagSplit[1];
        return shortName + '-' + tag + ".tar";
    }
}
