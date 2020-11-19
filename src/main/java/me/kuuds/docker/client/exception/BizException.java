/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package me.kuuds.docker.client.exception;

import lombok.Getter;

import javax.ws.rs.core.Response;

/**
 * Exception for Biz Layer
 *
 * @author KuuDS
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 4980849154496784984L;

    @Getter
    private final Response.Status status;

    /**
     * Constructor for default error code {@link  Response.Status#INTERNAL_SERVER_ERROR}
     *
     * @param message exception message
     * @param cause   cause
     */
    public BizException(String message, Throwable cause) {
        this(Response.Status.INTERNAL_SERVER_ERROR, message, cause);
    }

    /**
     * Constructor for default error code {@link  Response.Status#INTERNAL_SERVER_ERROR}
     *
     * @param status  {@link Response.Status} error code
     * @param message exception message
     * @param cause   cause
     */
    public BizException(Response.Status status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
