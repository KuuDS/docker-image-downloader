package me.kuuds.docker.client.domain;

import lombok.*;


@RequiredArgsConstructor(staticName = "of")
public class TaskInfo<T> {

    private static final int FAILED = -1;
    private static final int PENDING = 0;

    @Getter
    private final Integer taskId;
    private final Integer total;
    @Getter
    private Integer progress = PENDING;
    @Getter
    private final T metadata;

    public synchronized void nextStage() {
        synchronized (metadata) {
            if (progress > FAILED && progress < total) {
                progress++;
            }
        }
    }

    public synchronized void complete() {
        synchronized (metadata) {
            if (progress > FAILED && progress <= total) {
                progress = total;
            }
        }
    }

    public synchronized void fail() {
        synchronized (metadata) {
            if (progress < total) {
                progress = FAILED;
            }
        }
    }


}
