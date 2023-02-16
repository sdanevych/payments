package com.epam.payments.service;

import com.epam.payments.model.dao.interfaces.UserDao;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.utils.Encryption;
import com.epam.payments.utils.pagination.PaginationManager;
import com.epam.payments.utils.sort.EntitySortManager;

import java.util.List;

public class UserService {
    private static UserService instance;
    private UserDao userDao;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            synchronized (UserService.class) {
                if (instance == null) {
                    instance = new UserService();
                }
            }
        }
        return instance;
    }

    public boolean isUniqueLogin(String login) {
        long userLoginCount = userDao.selectCountByLogin(login);
        return userLoginCount == 0;
    }

    public boolean isUniqueEmail(String email) {
        long userEmailCount = userDao.selectCountByEmail(email);
        return userEmailCount == 0;
    }

    public boolean isUniquePhoneNumber(String phoneNumber) {
        long userPhoneNumberCount = userDao.selectCountByPhoneNumber(phoneNumber);
        return userPhoneNumberCount == 0;
    }

    public User getUserByEmailAndPassword(String email, String password) {
        password = Encryption.encrypt(password);
        return userDao.selectUserByEmailAndPassword(email, password);
    }

    public Long registerNewUser(User user, String password) {
        return userDao.insert(user, password);
    }

    public List<User> getAllUsers() {
        return userDao.selectAll();
    }

    public User getUser(Long userId) {
        return userDao.selectById(userId);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public List<User> getUsersInRange(Long offset, Long recordsCount) {
        return userDao.selectInRange(offset,recordsCount);
    }

    public PaginationManager<User> getAllUsersPaginationManager(Long page, Long itemsPerPage, EntitySortManager usersSortManager) {
        Long offset = (page - 1) * itemsPerPage;
        List<User> entitiesList = getAllUsersInRange(offset, itemsPerPage, usersSortManager);
        Long clientAccountsCount = getAllUsersCount();
        Long totalPages = (clientAccountsCount / itemsPerPage) + (clientAccountsCount % itemsPerPage == 0 ? 0 : 1);
        PaginationManager<User> paginationManager = new PaginationManager<>();
        paginationManager.setPaginationList(entitiesList);
        paginationManager.setPage(page);
        paginationManager.setTotalPages(totalPages);
        return paginationManager;
    }

    public List<User> getAllUsersInRange(Long offset, Long recordsCount, EntitySortManager entitySortManager) {
        return userDao.selectAllUsersInRange(offset, recordsCount, entitySortManager);
    }

    private Long getAllUsersCount() {
        return userDao.selectAllCount();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
