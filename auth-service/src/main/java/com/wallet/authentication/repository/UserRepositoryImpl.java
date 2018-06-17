package com.wallet.authentication.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.wallet.authentication.model.User;

@Repository
public class UserRepositoryImpl implements UserRepository {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);
	private JdbcTemplate jdbcTemplate;
    
	@Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	public User findUser(String username) {
		User user = null;
    	String query = "SELECT u.username name, u.password password, a.role role FROM " +
    				  "tuser u INNER JOIN tauthorities a ON u.id=a.userId WHERE " + 
    				  "u.enabled=1 AND u.username=?";
    	try {
    		user = (User) jdbcTemplate.queryForObject(query, new Object[]{username},
    	    		new RowMapper<User>() {
    		            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
    		            	User user = new User();
    		                user.setUsername(rs.getString("name"));
    		                user.setPassword(rs.getString("password"));
    		                user.setRole(rs.getString("role"));
    		                return user;
    		            }
    	        });
    	}
    	catch(Exception e) {
    		LOGGER.error("Error occured while retreiving:{}", e);
    	}
    	return user;
    }
}
