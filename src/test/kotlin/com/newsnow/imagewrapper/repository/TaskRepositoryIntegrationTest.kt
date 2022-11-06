package com.newsnow.imagewrapper.repository;

import com.newsnow.imagewrapper.domain.Task.TaskBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class TaskRepositoryIntegrationTest {

    @Autowired
    lateinit var taskRepository: TaskRepository;

    @Test
    fun `test simple select`(){
        assertEquals(1, taskRepository.selectAllTasks().size);
    }

    @Test
    fun `save task into database`(){
        val task = TaskBuilder().fileName("filename")
            .height(10)
            .width(10)
            .md5("asdfasdfasdfasdf")
            .timestamp(10)
            .url("http://localhost:8080/task/someimage.gif").build();

        val create = taskRepository.create(task);


        assertNotNull(create.id);
        task.id = create.id;
        assertEquals(task.toString(),create.toString())
        assertEquals(task, create)

    }
}