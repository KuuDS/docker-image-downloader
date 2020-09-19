/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package me.kuuds.docker.client.rs;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@QuarkusTest
@RequiredArgsConstructor(onConstructor = @__({ @Inject }))
public class ImageApiTest {
    private static final String TARGET_IMAGE = "alpine:3";

    @Disabled
    @Test
    public void shouldGetImageSuccess() throws Exception {
        RestAssured
            .given()
            .param("name", TARGET_IMAGE)
            .when()
            .get("/image")
            .then()
            .statusCode(Status.OK.getStatusCode());
    }
}
