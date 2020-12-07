package com.example.demo.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.example.demo.Model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(User user) {
        String sqlQuery = "insert into users(id, first_name, last_name) " + "values (?, ?, ?)";
        jdbcTemplate.update(sqlQuery, user.getId(), user.getFirstName(), user.getLastName());
    }

    public User findOne(long id) {
        String sqlQuery = "select id, first_name, last_name " + "from users where id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id);
    }

    public Optional<User> findByUsername(String userName) {
        String sqlQuery = "select * " + "from users where username = ?";
        User user = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, userName);
        return Optional.ofNullable(user);
    }

    public List<User> findAll() {
        String sqlQuery = "select id, first_name, last_name from users";
        return jdbcTemplate.query(sqlQuery, this::mapRowToUser);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder().id(resultSet.getLong("id")).firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name")).userName(resultSet.getString("username"))
                .active(resultSet.getBoolean("active")).password(resultSet.getString("password"))
                .roles(resultSet.getString("roles")).build();
    }
}
