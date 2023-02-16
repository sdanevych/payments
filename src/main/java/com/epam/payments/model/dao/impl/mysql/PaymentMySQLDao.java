package com.epam.payments.model.dao.impl.mysql;

import com.epam.payments.exception.sql.DaoSQLException;
import com.epam.payments.exception.sql.PrepareQuerySQLException;
import com.epam.payments.model.dao.BaseCrudDao;
import com.epam.payments.model.dao.interfaces.PaymentDao;
import com.epam.payments.model.dao.mapper.RowMapperFactory;
import com.epam.payments.model.dao.mapper.impl.PaymentRowMapper;
import com.epam.payments.model.entity.payment.Payment;
import com.epam.payments.utils.constant.SettingsConstant;
import com.epam.payments.utils.resource_manager.PageManager;
import com.epam.payments.utils.sort.EntitySortManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.epam.payments.model.dao.impl.mysql.SQLQuery.PaymentQuery.*;

public class PaymentMySQLDao extends BaseCrudDao<Payment> implements PaymentDao {
    private static final int SENDER_ACCOUNT_ID_QUERY_INDEX = 1;
    private static final int RECEIVER_ACCOUNT_ID_QUERY_INDEX = 2;
    private static final int AMOUNT_QUERY_INDEX = 3;
    private static final int CURRENCY_QUERY_INDEX = 4;
    private static final int PAYMENT_STATUS_QUERY_INDEX = 5;
    private static final int CREATE_TIME_QUERY_INDEX = 6;
    private static final int SEND_TIME_QUERY_INDEX = 7;
    private static final int ID_QUERY_INDEX = 8;
    private static PaymentMySQLDao instance;
    private static final PaymentRowMapper paymentRowMapper = RowMapperFactory.getInstance().createPaymentRowMapper();

    private PaymentMySQLDao() {
    }

    public static PaymentMySQLDao getInstance() {
        if (instance == null) {
            synchronized (PaymentMySQLDao.class) {
                if (instance == null) {
                    instance = new PaymentMySQLDao();
                }
            }
        }
        return instance;
    }

    @Override
    public Long insert(Payment payment) throws DaoSQLException {
        return insertEntity(payment, INSERT_PAYMENT);
    }

    @Override
    public void update(Payment payment) throws DaoSQLException {
        updateEntity(payment, UPDATE_PAYMENT_BY_ID);
    }

    @Override
    public void delete(long id) throws DaoSQLException {
    }

    @Override
    public List<Payment> selectAll() throws DaoSQLException {
        return selectEntities(SELECT_ALL_PAYMENTS, paymentRowMapper);
    }

    @Override
    public Payment selectById(long id) throws DaoSQLException {
        return selectEntity(SELECT_PAYMENT_BY_ID, paymentRowMapper, id);
    }

    @Override
    protected int getIdIndex() {
        return ID_QUERY_INDEX;
    }

    @Override
    protected PreparedStatement setQueryParameters(PreparedStatement preparedStatement, Payment payment) throws PrepareQuerySQLException {
        try {
            preparedStatement.setLong(SENDER_ACCOUNT_ID_QUERY_INDEX, payment.getSenderAccountId());
            preparedStatement.setLong(RECEIVER_ACCOUNT_ID_QUERY_INDEX, payment.getReceiverAccountId());
            preparedStatement.setBigDecimal(AMOUNT_QUERY_INDEX, payment.getAmount());
            preparedStatement.setString(CURRENCY_QUERY_INDEX, String.valueOf(payment.getCurrency()));
            preparedStatement.setString(PAYMENT_STATUS_QUERY_INDEX, String.valueOf(payment.getPaymentStatus()));
            preparedStatement.setTimestamp(CREATE_TIME_QUERY_INDEX, payment.getCreateTime());
            setSendTime(preparedStatement, payment);
        } catch (SQLException e) {
            throw new PrepareQuerySQLException(payment);
        }
        return preparedStatement;
    }

    public List<Payment> selectPaymentsByUserId(Long userId) {
        return selectEntities(SELECT_PAYMENTS_BY_USER_ID, paymentRowMapper, userId, userId);
    }

    public Long selectCountPaymentsByUserId(Long userId) {
        return selectEntitiesCount(SELECT_PAYMENTS_COUNT_BY_USER_ID, userId, userId);
    }

    @Override
    public List<Payment> selectAllPaymentsInRange(Long offset, Long recordsCount, EntitySortManager entitySortManager) {
        String query = String.format(QUERY_TO_FORMAT_SELECT_ALL_PAYMENTS_ORDERED_IN_RANGE,
                entitySortManager.getSortColumn(), entitySortManager.getSortOrder());
        return selectEntities(query, paymentRowMapper, offset, recordsCount);
    }

    @Override
    public Long selectAllCount() {
        return selectEntitiesCount(SELECT_COUNT_ALL_PAYMENTS);
    }

    private static void setSendTime(PreparedStatement preparedStatement, Payment payment) throws SQLException {
        if (payment.getSendTime() == null) {
            preparedStatement.setNull(SEND_TIME_QUERY_INDEX, Types.NULL);
        } else {
            preparedStatement.setTimestamp(SEND_TIME_QUERY_INDEX,
                    Timestamp.valueOf(new SimpleDateFormat(PageManager
                            .getProperty(SettingsConstant.APPLICATION_DATE_TIME_FORMAT))
                            .format(payment.getSendTime())));
        }
    }

    @Override
    public List<Payment> selectPaymentsFromUser(Long userId) {
        return selectEntities(SELECT_PAYMENTS_FROM_USER, paymentRowMapper, userId);
    }

    @Override
    public List<Payment> selectPaymentsReceivedByUser(Long userId) {
        return selectEntities(SELECT_RECEIVED_PAYMENTS_BY_USER_ID, paymentRowMapper, userId);
    }
    @Override
    public List<Payment> selectUserPaymentsInRange(Long userId, Long offset, Long recordsCount, EntitySortManager entitySortManager) throws DaoSQLException {
        String query = String.format(QUERY_TO_FORMAT_SELECT_USER_PAYMENTS_BY_USER_ID_ORDERED_IN_RANGE,
                entitySortManager.getSortColumn(), entitySortManager.getSortOrder());
        return selectEntities(query, paymentRowMapper, userId, userId, offset, recordsCount);
    }

    public Long selectCountPaymentsBySenderAccountId(Long senderAccountId) {
        return selectEntitiesCount(SELECT_COUNT_PAYMENTS_BY_SENDER_ACCOUNT_ID, senderAccountId);
    }


}
