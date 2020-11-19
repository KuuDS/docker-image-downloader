package me.kuuds.docker.client.biz;

import me.kuuds.docker.client.domain.TaskInfo;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SimpleTaskVault implements TaskVault {
    @Override
    public <T> TaskInfo<T> discover(int taskId, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> TaskInfo<T> saw(TaskInfo<T> taskInfo) {
        return null;
    }

    @Override
    public <T> TaskInfo<T> remove(int taskId, Class<T> clazz) {
        return null;
    }

    @Override
    public int taskId() {
        return 0;
    }

    @Override
    public <T> TaskInfo<T> task(T object, int total) {
        return TaskInfo.of(taskId(), total, object);
    }
}
