package com.ilirium.grizzly.example.db.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;


public class User
{
    Integer id;
    String name;

    public User(Integer id, String name)
    {
        this.id = id;
        this.name = name;
    }
    
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    public static class UserMapper implements ResultSetMapper<User>
    {
        public User map(int index, ResultSet r, StatementContext ctx) throws SQLException
        {
            return new User(r.getInt("id"), r.getString("name"));
        }
    }
    
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append("id = ").append(id);
        sb.append(", name = ").append(name);
        return sb.toString();
    }
}
