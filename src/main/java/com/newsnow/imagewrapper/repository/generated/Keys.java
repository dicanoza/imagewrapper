/*
 * This file is generated by jOOQ.
 */
package com.newsnow.imagewrapper.repository.generated;


import com.newsnow.imagewrapper.repository.generated.tables.Databasechangeloglock;
import com.newsnow.imagewrapper.repository.generated.tables.Task;
import com.newsnow.imagewrapper.repository.generated.tables.records.DatabasechangeloglockRecord;
import com.newsnow.imagewrapper.repository.generated.tables.records.TaskRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<DatabasechangeloglockRecord> DATABASECHANGELOGLOCK_PKEY = Internal.createUniqueKey(Databasechangeloglock.DATABASECHANGELOGLOCK, DSL.name("databasechangeloglock_pkey"), new TableField[] { Databasechangeloglock.DATABASECHANGELOGLOCK.ID }, true);
    public static final UniqueKey<TaskRecord> TASK_PKEY = Internal.createUniqueKey(Task.TASK, DSL.name("task_pkey"), new TableField[] { Task.TASK.ID }, true);
}
