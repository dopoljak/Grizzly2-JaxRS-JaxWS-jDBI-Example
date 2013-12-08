package com.ilirium.grizzly.example.db.dao;

import com.ilirium.grizzly.example.db.entity.User;
import com.ilirium.grizzly.example.db.entity.User.UserMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

public interface UserDAO
{
    @SqlUpdate("create table USER (id int primary key, name varchar(100))")
    void createTransactionTable();

    @SqlUpdate("insert into USER (id, name) values (:id, :name)")
    void insert(@Bind("id") int id, @Bind("name") String name);
    
    @SqlQuery("select name from USER where id = :id")
    String findNameById(@Bind("id") int id);

    @SqlQuery("select id, name from USER where id = :id")
    @RegisterMapper(UserMapper.class)
    User findUserById(@Bind("id") int id);
    
    void close();
}
