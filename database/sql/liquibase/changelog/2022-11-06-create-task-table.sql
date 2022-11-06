create table task (
    id UUID primary key,
    md5 varchar(50) not null,
    fileName varchar(50),
    width NUMERIC(5),
    height NUMERIC(5),
    created timestamp
)
