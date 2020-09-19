/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package me.kuuds.docker.client.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility.
 *
 * @author KuuDS
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppUtils {

    /**
     *
     * @param name
     * @return
     */
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
