package me.kuuds.docker.client.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Pojo for HTTP response.
 *
 * @author KuuDS
 */
@ToString
@EqualsAndHashCode
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class RecentTags {
    // tags list
    private List<String> tags;

    public static final transient RecentTags EMPTY = new RecentTags(new ArrayList<>());
}
