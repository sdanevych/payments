package com.epam.payments.model.dao;

import com.epam.payments.exception.sql.*;
import com.epam.payments.model.dao.mapper.RowMapper;
import com.epam.payments.model.database.ConnectionProvider;
import com.epam.payments.model.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseCrudDao<E extends Entity> {
    private static final Logger LOGGER = LogManager.getLogger(BaseCrudDao.class);

    protected Long insertEntity(E entity, String query) {
        Connection connection = ConnectionProvider.getConnection();
        Long insertedEntityId = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                query, Statement.RETURN_GENERATED_KEYS)) {
            setQueryParameters(preparedStatement, entity);
            LOGGER.info("Query to execute: " + preparedStatement);
            preparedStatement.executeUpdate();
            LOGGER.info("Executed query: " + preparedStatement);
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                insertedEntityId = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new InsertEntitySQLException(String.format("Entity %s is NOT inserted in database", entity), e);
        }
        return insertedEntityId;
    }

    protected void updateEntity(E entity, String query) {
        Connection connection = ConnectionProvider.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setQueryParameters(preparedStatement, entity);
            preparedStatement.setLong(getIdIndex(), entity.getId());
            LOGGER.info("Query to execute: " + preparedStatement);
            preparedStatement.executeUpdate();
            LOGGER.info("Executed query: " + preparedStatement);
        } catch (SQLException e) {
            throw new UpdateEntitySQLException(String.format("Entity %s is not updated in database", entity), e);
        }
    }

    protected abstract int getIdIndex();

    protected void deleteEntity(E entity, String query) {
        Connection connection = ConnectionProvider.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query + entity.getId())) {
            LOGGER.info("Query to execute: " + preparedStatement);
            preparedStatement.executeUpdate();
            LOGGER.info("Executed query: " + preparedStatement);
        } catch (SQLException e) {
            throw new DeleteEntitySQLException(String.format("Entity %s is not deleted in database", entity), e);
        }
    }

    protected List<E> selectEntities(String query, RowMapper<E> rowMapper, Object... conditionParams) {
        Connection connection = ConnectionProvider.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < conditionParams.length; i++) {
                preparedStatement.setObject(i + 1, conditionParams[i]);
            }
            List<E> entities = new ArrayList<>();
            LOGGER.info("Query to execute: " + preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOGGER.info("Executed query: " + preparedStatement);
            while (resultSet.next()) {
                entities.add(rowMapper.map(resultSet));
            }
            return entities;
        } catch (SQLException e) {
            throw new SelectEntitySQLException(String.format("Entities queried by \"%s\" are not selected", query), e);
        }
    }

    protected E selectEntity(String query, RowMapper<E> rowMapper, Object... conditionParams) {
        List<E> selectedEntityList = selectEntities(query, rowMapper, conditionParams);
        int selectedEntitiesCount = selectedEntityList.size();
        if (selectedEntitiesCount > 1) {
            throw new DaoSQLException(String.format("Query \"%s\" is expected to return one entity" +
                    "but returned %s", query, selectedEntitiesCount));
        } else if (selectedEntitiesCount == 0) {
            return null;
        }
        return selectedEntityList.get(0);
    }

    protected Long selectEntitiesCount(String query, Object... conditionParams) {
        Connection connection = ConnectionProvider.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < conditionParams.length; i++) {
                preparedStatement.setObject(i + 1, conditionParams[i]);
            }
            LOGGER.info("Query to execute: " + preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOGGER.info("Executed query: " + preparedStatement);
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new SelectEntitySQLException(e.getMessage(), e);
        }
    }

    protected abstract PreparedStatement setQueryParameters(PreparedStatement preparedStatement, E entity) throws PrepareQuerySQLException;
}
