package com.newsnow.imagewrapper.repository;

import com.newsnow.imagewrapper.domain.Task;
import com.newsnow.imagewrapper.repository.generated.tables.records.TaskRecord;

public class TaskRecordConverter {
    public static com.newsnow.imagewrapper.domain.Task asTask(TaskRecord record) {
        var taskBuilder = new com.newsnow.imagewrapper.domain.Task.TaskBuilder();

        taskBuilder.id(record.getId())
                .md5(record.getMd5())
                .url(record.getUrl())
                .fileName(record.getFilename())
                .height(record.getHeight())
                .width(record.getWidth());

        return taskBuilder.build();
    }

    public static TaskRecord asRecord(Task task) {
        var ret = new TaskRecord();
        ret.setId(task.getId());
        ret.setMd5(task.getMd5());
        ret.setUrl(task.getUrl());
        ret.setFilename(task.getFileName());
        ret.setHeight(task.getHeight());
        ret.setWidth(task.getWidth());
        return ret;
    }
}
