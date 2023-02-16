package com.epam.payments.model.dao.impl.mysql;

import com.epam.payments.exception.sql.DaoSQLException;
import com.epam.payments.exception.sql.PrepareQuerySQLException;
import com.epam.payments.model.dao.BaseCrudDao;
import com.epam.payments.model.dao.interfaces.AccountDao;
import com.epam.payments.model.dao.mapper.RowMapperFactory;
import com.epam.payments.model.dao.mapper.impl.AccountRowMapper;
import com.epam.payments.model.entity.account.Account;
import com.epam.payments.utils.sort.EntitySortManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.epam.payments.model.dao.impl.mysql.SQLQuery.AccountQuery.*;

public class AccountMySQLDao extends BaseCrudDao<Account> implements AccountDao {
    private static final int ID_INDEX_QUERY_INDEX = 1;
    private static final int NAME_QUERY_INDEX = 2;
    private static final int STATUS_QUERY_INDEX = 3;
    private static final int BALANCE_QUERY_INDEX = 4;
    private static final int CREATE_TIME_QUERY_INDEX = 5;
    private static final int ID_QUERY_INDEX = 6;
    private static AccountMySQLDao instance;
    private static final AccountRowMapper accountRowMapper = RowMapperFactory.getInstance().createAccountRowMapper();
    private AccountMySQLDao() {
    }

    public static AccountMySQLDao getInstance() {
        if (instance == null) {
            synchronized (AccountMySQLDao.class) {
                if (instance == null) {
                    instance = new AccountMySQLDao();
                }
            }
        }
        return instance;
    }

    @Override
    public Long insert(Account account) throws DaoSQLException {
        return insertEntity(account, INSERT_ACCOUNT);
    }

    @Override
    public void update(Account account) throws DaoSQLException {
        updateEntity(account, UPDATE_ACCOUNT_BY_ID);
    }

    @Override
    public void delete(long id) throws DaoSQLException {}

    @Override
    public List<Account> selectAll() throws DaoSQLException {
        return selectEntities(SELECT_ALL_ACCOUNTS, accountRowMapper);
    }

    public List<Account> selectUserAccountsInRange(Long userId, Long offset, Long recordsCount, EntitySortManager entitySortManager) throws DaoSQLException {
        String query = String.format(QUERY_TO_FORMAT_SELECT_USER_ACCOUNTS_BY_USER_ID_ORDERED_IN_RANGE,
                entitySortManager.getSortColumn(), entitySortManager.getSortOrder());
        return selectEntities(query, accountRowMapper, userId, offset, recordsCount);
    }

    public List<Account> selectAllAccountsInRange(Long offset, Long recordsCount, EntitySortManager entitySortManager) throws DaoSQLException {
        String query = String.format(QUERY_TO_FORMAT_SELECT_ALL_ACCOUNTS_ORDERED_IN_RANGE,
                entitySortManager.getSortColumn(), entitySortManager.getSortOrder());
        return selectEntities(query, accountRowMapper, offset, recordsCount);
    }

    public List<Account> selectAccountsInRange(Long offset, Long recordsCount) {
        return selectEntities(SELECT_ACCOUNTS_IN_RANGE, accountRowMapper, offset, recordsCount);
    }

    @Override
    public List<Account> selectUserAccountsWithBankCards(Long userId) {
        return selectEntities(SELECT_USER_ACCOUNTS_WITH_BANK_CARDS, accountRowMapper, userId);
    }

    @Override
    public List<Account> selectUserActiveAccountsWithBankCards(Long userId) {
        return selectEntities(SELECT_ACTIVE_USER_ACCOUNTS_WITH_BANK_CARDS, accountRowMapper, userId);
    }

    @Override
    public Account selectById(long id) throws DaoSQLException {
        return selectEntity(SELECT_ACCOUNT_BY_ID, accountRowMapper, id);
    }

    @Override
    public Long selectCountByUserIdAndName(Long userId, String accountName) {
        return selectEntitiesCount(SELECT_COUNT_BY_USER_ID_AND_ACCOUNT_NAME, userId, accountName);
    }

    @Override
    public List<Account> selectActiveUserAccounts(Long userId) {
        return selectEntities(SELECT_ACTIVE_USER_ACCOUNTS, accountRowMapper, userId);
    }

    public List<Account> selectAccountsByUserId(Long userId) {
        return selectEntities(SELECT_USER_ACCOUNTS_BY_USER_ID, accountRowMapper, userId);
    }

    public Long selectCountByUserID(Long userId) {
        return selectEntitiesCount(SELECT_COUNT_BY_USER_ID, userId);
    }

    public Long selectAllCount() {
        return selectEntitiesCount(SELECT_COUNT_ALL_ACCOUNTS);
    }

    @Override
    protected int getIdIndex() {
        return ID_QUERY_INDEX;
    }

    @Override
    protected PreparedStatement setQueryParameters(PreparedStatement preparedStatement, Account account) throws PrepareQuerySQLException {
        try {
            preparedStatement.setLong(ID_INDEX_QUERY_INDEX, account.getUserId());
            preparedStatement.setString(NAME_QUERY_INDEX, account.getName());
            preparedStatement.setString(STATUS_QUERY_INDEX, String.valueOf(account.getStatus()));
            preparedStatement.setBigDecimal(BALANCE_QUERY_INDEX, account.getBalance());
            preparedStatement.setTimestamp(CREATE_TIME_QUERY_INDEX, account.getCreateTime());
        } catch (SQLException e) {
            throw new PrepareQuerySQLException(account);
        }
        return preparedStatement;
    }
}
