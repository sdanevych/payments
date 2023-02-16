package com.epam.payments.model.dao.impl.mysql;

import com.epam.payments.exception.sql.DaoSQLException;
import com.epam.payments.exception.sql.PrepareQuerySQLException;
import com.epam.payments.model.dao.BaseCrudDao;
import com.epam.payments.model.dao.interfaces.BankCardDao;
import com.epam.payments.model.dao.mapper.RowMapperFactory;
import com.epam.payments.model.dao.mapper.impl.BankCardRowMapper;
import com.epam.payments.model.entity.account.BankCard;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.epam.payments.model.dao.impl.mysql.SQLQuery.BankCardQuery.*;

public class BankCardMySQLDao extends BaseCrudDao<BankCard> implements BankCardDao {
    private static final int ACCOUNT_ID_QUERY_INDEX = 1;
    private static final int CARD_NUMBER_QUERY_INDEX = 2;
    private static final int BANK_CARD_TYPE_QUERY_INDEX = 3;
    private static final int CURRENCY_QUERY_INDEX = 4;
    private static final int EXPIRATION_DATE_QUERY_INDEX = 5;
    private static final int CARDHOLDER_NAME_QUERY_INDEX = 6;
    private static final int BALANCE_QUERY_INDEX = 7;
    private static final int CVV_QUERY_INDEX = 8;
    private static final int ID_QUERY_INDEX = 9;
    private static BankCardMySQLDao instance;
    private static final BankCardRowMapper bankCardRowMapper = RowMapperFactory.getInstance().createBankCardRowMapper();

    private BankCardMySQLDao() {
    }

    public static BankCardMySQLDao getInstance() {
        if (instance == null) {
            synchronized (BankCardMySQLDao.class) {
                if (instance == null) {
                    instance = new BankCardMySQLDao();
                }
            }
        }
        return instance;
    }

    @Override
    public Long insert(BankCard entity) throws DaoSQLException {
        return insertEntity(entity, INSERT_BANKCARD);
    }

    @Override
    public void update(BankCard bankCard) throws DaoSQLException {
        updateEntity(bankCard, UPDATE_BANKCARD_BY_ACCOUNT_ID);

    }

    @Override
    public void delete(long id) throws DaoSQLException {

    }

    @Override
    public List<BankCard> selectAll() throws DaoSQLException {
        return null;
    }

    @Override
    public BankCard selectById(long id) throws DaoSQLException {
        return selectEntity(SELECT_BANKCARD_BY_ID, bankCardRowMapper, id);
    }

    public BankCard selectBankCardByAccountId(long accountId) throws DaoSQLException {
        return selectEntity(SELECT_BANKCARD_BY_ACCOUNT_ID, bankCardRowMapper, accountId);
    }

    @Override
    protected PreparedStatement setQueryParameters(PreparedStatement preparedStatement, BankCard bankCard) throws PrepareQuerySQLException {
        try {
            preparedStatement.setLong(ACCOUNT_ID_QUERY_INDEX, bankCard.getAccountId());
            preparedStatement.setString(CARD_NUMBER_QUERY_INDEX, bankCard.getCardNumber());
            preparedStatement.setString(BANK_CARD_TYPE_QUERY_INDEX, String.valueOf(bankCard.getBankCardType()));
            preparedStatement.setString(CURRENCY_QUERY_INDEX, String.valueOf(bankCard.getCurrency()));
            preparedStatement.setDate(EXPIRATION_DATE_QUERY_INDEX, bankCard.getExpirationDate());
            preparedStatement.setString(CARDHOLDER_NAME_QUERY_INDEX, bankCard.getCardholderName());
            preparedStatement.setBigDecimal(BALANCE_QUERY_INDEX, bankCard.getBalance());
            preparedStatement.setString(CVV_QUERY_INDEX, bankCard.getCvv());
        } catch (SQLException e) {
            throw new PrepareQuerySQLException(bankCard);
        }
        return preparedStatement;
    }

    @Override
    protected int getIdIndex() {
        return ID_QUERY_INDEX;
    }
}
