package com.epam.payments.model.dao.interfaces;

import com.epam.payments.model.dao.Dao;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.utils.sort.EntitySortManager;

import java.util.List;

public interface UserDao extends Dao<User> {
    Long selectCountByLogin(String login);

    Long selectCountByEmail(String email);

    Long selectCountByPhoneNumber(String phoneNumber);

    User selectUserByEmailAndPassword(String email, String password);

    List<User> selectInRange(Long offset, Long recordsCount);

    Long insert(User user, String password);

    List<User> selectAllUsersInRange(Long offset, Long recordsCount, EntitySortManager entitySortManager);

    Long selectAllCount();
}
