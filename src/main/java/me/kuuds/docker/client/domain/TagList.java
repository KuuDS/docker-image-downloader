/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package me.kuuds.docker.client.domain;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Pojo for response from Docker Registry.
 *
 * @author KuuDS
 * @see <a href=
 *      "https://docs.docker.com/registry/spec/api/#listing-image-tags">
 *      Registry API</a>
 */
@ToString
@EqualsAndHashCode
@Data
public class TagList {
    private String name;
    private List<String> tags;
}
