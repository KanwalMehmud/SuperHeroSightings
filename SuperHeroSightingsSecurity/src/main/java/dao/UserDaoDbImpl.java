package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserDaoDbImpl implements UserDao {

    private static final String SQL_INSERT_USER
            = "insert into users (username, password, enabled) values (?, ?, 1)";
    private static final String SQL_INSERT_AUTHORITY
            = "insert into authorities (username, authority) values (?, ?)";
    private static final String SQL_DELETE_USER
            = "delete from users where user_id = ?";
    private static final String SQL_DELETE_AUTHORITIES
            = "delete from authorities where username = ?";
    private static final String SQL_GET_ALL_USERS
            = "select * from users";
    private static final String SQL_SELECT_USER
            = "select * from users where user_id = ?";
    private static final String SQL_UPDATE_USER
            = "update users set password = ? where user_id =  ?";
    private static final String SQL_UPDATE_USER_STATUS
            = "update users set enabled = ? where user_id =  ?";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUser(User newUser) {
        jdbcTemplate.update(SQL_INSERT_USER,
                newUser.getUsername(),
                newUser.getPassword());
        newUser.setUserId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class));

        ArrayList<String> authorities = newUser.getAuthorities();
        for (String authority : authorities) {
            jdbcTemplate.update(SQL_INSERT_AUTHORITY,
                    newUser.getUsername(),
                    authority);
        }

        return newUser;
    }

    @Override
    public void deleteUser(long userId) {
        User user = getUserById(userId);
        jdbcTemplate.update(SQL_DELETE_AUTHORITIES, user.getUsername());
        jdbcTemplate.update(SQL_DELETE_USER, userId);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(SQL_GET_ALL_USERS, new UserMapper());
    }

    @Override
    public User getUserById(long userId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_USER,
                    new UserMapper(), userId);
        } catch (EmptyResultDataAccessException ex) {

            return null;
        }
    }

    @Override
    public void editUser(User editUser) {
        jdbcTemplate.update(SQL_UPDATE_USER,
                editUser.getPassword(),
                editUser.getUserId());
    }

    @Override
    public void changeUserStatus(long userId) {
        User user = getUserById(userId);
        if (user.getIsEnabled()) {
            jdbcTemplate.update(SQL_UPDATE_USER_STATUS,
                    0, user.getUserId());
        } else {
            jdbcTemplate.update(SQL_UPDATE_USER_STATUS,
                    1, user.getUserId());
        }
    }

    private static final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            int isEnabled = rs.getInt("enabled");

            if (isEnabled == 1) {
                user.setIsEnabled(true);
            } else {
                user.setIsEnabled(false);
            }
            return user;
        }
    }

}
