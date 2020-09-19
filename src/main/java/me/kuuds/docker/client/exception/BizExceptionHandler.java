/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package me.kuuds.docker.client.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link BizException} interceptor for controller.
 *
 * @author KuuDS
 */
@Provider
@Slf4j
public class BizExceptionHandler implements ExceptionMapper<BizException> {

    @Override
    public Response toResponse(BizException exception) {
        log.error("biz exception.", exception);
        return Response.serverError().build();
    }
}
