package com.epam.payments.model.dao.impl.mysql;

import com.epam.payments.exception.sql.DaoSQLException;
import com.epam.payments.exception.sql.InsertEntitySQLException;
import com.epam.payments.exception.sql.PrepareQuerySQLException;
import com.epam.payments.model.dao.BaseCrudDao;
import com.epam.payments.model.dao.interfaces.UserDao;
import com.epam.payments.model.dao.mapper.RowMapperFactory;
import com.epam.payments.model.dao.mapper.impl.UserRowMapper;
import com.epam.payments.model.database.ConnectionProvider;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.utils.sort.EntitySortManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

import static com.epam.payments.model.dao.impl.mysql.SQLQuery.UserQuery.*;

public class UserMySQLDao extends BaseCrudDao<User> implements UserDao {
    private static final int LOGIN_QUERY_INDEX = 1;
    private static final int ROLE_QUERY_INDEX = 2;
    private static final int STATUS_QUERY_INDEX = 3;
    private static final int FIRST_NAME_QUERY_INDEX = 4;
    private static final int SECOND_NAME_QUERY_INDEX = 5;
    private static final int PHONE_NUMBER_QUERY_INDEX = 6;
    private static final int EMAIL_QUERY_INDEX = 7;
    private static final int CREATE_TIME_QUERY_INDEX = 8;
    private static final int ID_QUERY_INDEX = 9;
    private static UserMySQLDao instance;
    private static final UserRowMapper userRowMapper = RowMapperFactory.getInstance().createUserRowMapper();
    private static final Logger LOGGER = LogManager.getLogger(UserMySQLDao.class);


    private UserMySQLDao() {
    }

    public static UserMySQLDao getInstance() {
        if (instance == null) {
            synchronized (UserMySQLDao.class) {
                if (instance == null) {
                    instance = new UserMySQLDao();
                }
            }
        }
        return instance;
    }

    @Override
    public Long insert(User user) throws DaoSQLException {
        return null;
    }

    @Override
    public void update(User user) throws DaoSQLException {
        updateEntity(user, UPDATE_USER_BY_ID);
    }

    @Override
    public void delete(long id) throws DaoSQLException {

    }

    @Override
    public List<User> selectAll() throws DaoSQLException {
        return selectEntities(SELECT_ALL_USERS, userRowMapper);
    }

    @Override
    public User selectById(long id) throws DaoSQLException {
        return selectEntity(SELECT_USER_BY_ID, userRowMapper, id);
    }

    public Long selectCountByLogin(String login) {
        return selectEntitiesCount(SELECT_COUNT_BY_USER_LOGIN, login);
    }

    @Override
    public Long selectCountByEmail(String email) {
        return selectEntitiesCount(SELECT_COUNT_BY_USER_EMAIL, email);
    }

    @Override
    public Long selectCountByPhoneNumber(String phoneNumber) {
        return selectEntitiesCount(SELECT_COUNT_BY_USER_PHONE_NUMBER, phoneNumber);
    }

    @Override
    public User selectUserByEmailAndPassword(String email, String password) {
        return selectEntity(SELECT_USER_BY_EMAIL_AND_PASSWORD, userRowMapper, email, password);
    }

    @Override
    public Long insert(User user, String password) {
        Connection connection = ConnectionProvider.getConnection();
        Long insertedEntityId = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(LOGIN_QUERY_INDEX, user.getLogin());
            preparedStatement.setString(ROLE_QUERY_INDEX, String.valueOf(user.getRole()));
            preparedStatement.setString(STATUS_QUERY_INDEX, String.valueOf(user.getStatus()));
            preparedStatement.setString(FIRST_NAME_QUERY_INDEX, String.valueOf(user.getFirstName()));
            preparedStatement.setString(SECOND_NAME_QUERY_INDEX, String.valueOf(user.getSecondName()));
            preparedStatement.setString(PHONE_NUMBER_QUERY_INDEX, user.getPhoneNumber());
            preparedStatement.setString(EMAIL_QUERY_INDEX, user.getEmail());
            preparedStatement.setTimestamp(CREATE_TIME_QUERY_INDEX, user.getCreateTime());
            preparedStatement.setString(9, password);
            LOGGER.info("Query to execute: " + preparedStatement);
            preparedStatement.executeUpdate();
            LOGGER.info("Executed query: " + preparedStatement);
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                insertedEntityId = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new InsertEntitySQLException(String.format("Entity %s is NOT inserted in database", user), e);
        }
        return insertedEntityId;
    }

    public List<User> selectInRange(Long offset, Long recordsCount) {
        return selectEntities(SELECT_USERS_IN_RANGE, userRowMapper, offset, recordsCount);
    }

    @Override
    protected PreparedStatement setQueryParameters(PreparedStatement preparedStatement, User user) throws PrepareQuerySQLException {
        try {
            preparedStatement.setString(LOGIN_QUERY_INDEX, user.getLogin());
            preparedStatement.setString(ROLE_QUERY_INDEX, String.valueOf(user.getRole()));
            preparedStatement.setString(STATUS_QUERY_INDEX, String.valueOf(user.getStatus()));
            preparedStatement.setString(FIRST_NAME_QUERY_INDEX, user.getFirstName());
            preparedStatement.setString(SECOND_NAME_QUERY_INDEX, user.getSecondName());
            preparedStatement.setString(PHONE_NUMBER_QUERY_INDEX, user.getPhoneNumber());
            preparedStatement.setString(EMAIL_QUERY_INDEX, user.getEmail());
            preparedStatement.setTimestamp(CREATE_TIME_QUERY_INDEX, user.getCreateTime());
        } catch (SQLException e) {
            throw new PrepareQuerySQLException(user);
        }
        return preparedStatement;
    }

    @Override
    protected int getIdIndex() {
        return ID_QUERY_INDEX;
    }

    @Override
    public List<User> selectAllUsersInRange(Long offset, Long recordsCount, EntitySortManager entitySortManager) {
        String query = String.format(QUERY_TO_FORMAT_SELECT_ALL_USERS_ORDERED_IN_RANGE,
                entitySortManager.getSortColumn(), entitySortManager.getSortOrder());
        return selectEntities(query, userRowMapper, offset, recordsCount);
    }

    @Override
    public Long selectAllCount() {
        return selectEntitiesCount(SELECT_COUNT_ALL_USERS);
    }
}
