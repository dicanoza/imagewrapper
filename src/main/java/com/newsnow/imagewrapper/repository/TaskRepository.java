package com.newsnow.imagewrapper.repository;

import com.newsnow.imagewrapper.domain.Task;
import com.newsnow.imagewrapper.repository.generated.Tables;
import com.newsnow.imagewrapper.repository.generated.tables.records.TaskRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.newsnow.imagewrapper.repository.generated.tables.Task.TASK;

@Repository
public class TaskRepository {
    @Autowired
    private final DSLContext dsl;

    public TaskRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<Task> selectAllTasks() {
        return dsl.selectFrom(Tables.TASK).stream().map(TaskRecordConverter::asTask).collect(Collectors.toList());
    }

    public Optional<Task> selectTaskById(UUID taskId) {

        return dsl.selectFrom(Tables.TASK)
                .where(TASK.ID.eq(taskId))
                .fetch().stream()
                .map(TaskRecordConverter::asTask)
                .findFirst();
    }

    public Task create(Task task) {
        TaskRecord taskRecord = TaskRecordConverter.asRecord(task);
        taskRecord.setId(UUID.randomUUID());
        dsl.insertInto(Tables.TASK).set(taskRecord).execute();
        return selectTaskById(taskRecord.getId()).orElseThrow();
    }
}
