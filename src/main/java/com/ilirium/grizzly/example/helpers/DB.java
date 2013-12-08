package com.ilirium.grizzly.example.helpers;

import com.ilirium.grizzly.example.db.dao.UserDAO;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcConnectionPool;
import org.skife.jdbi.v2.DBI;

public class DB
{
    private static DBI dbi;
    
    public static void initialize()
    {
        DataSource ds = JdbcConnectionPool.create("jdbc:h2:mem:test", "username", "password");
        dbi = new DBI(ds);
        UserDAO dao = dbi.open(UserDAO.class);        
        dao.createTransactionTable();
        dao.insert(1, "User001");        
        dao.close();
    }
    
    public static DBI getDBI() 
    {
        return dbi;
    }
}
