package me.kuuds.docker.client.biz;

import me.kuuds.docker.client.domain.TaskInfo;

public interface TaskVault {

    <T> TaskInfo<T> discover(int taskId, Class<T> clazz);

    <T> TaskInfo<T> saw(TaskInfo<T> taskInfo);

    <T> TaskInfo<T> remove(int taskId, Class<T> clazz);

    int taskId();

    <T> TaskInfo<T> task(T object, int total);

}
