#!/bin/sh
# liquibase-migrate.sh

liquibase --defaults-file=/liquibase/changelog/liquibase.properties --search-path=/liquibase/changelog  migrate --changelog-file='2022-11-06-create-task-table.sql'