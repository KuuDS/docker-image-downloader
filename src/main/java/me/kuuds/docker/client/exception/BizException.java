/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package me.kuuds.docker.client.exception;

/**
 * Exception for Biz Layer
 *
 * @author KuuDS
 */
public class BizException extends Exception {
    private static final long serialVersionUID = 4980849154496784984L;

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }
}
