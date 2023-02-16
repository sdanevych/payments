package com.epam.payments.model.entity.user;

import com.epam.payments.model.entity.Entity;
import com.epam.payments.utils.constant.SettingsConstant;
import com.epam.payments.utils.resource_manager.PageManager;

import java.sql.Timestamp;

public class User extends Entity {
    private String login;
    private Role role;
    private UserStatus status;
    private String firstName;
    private String secondName;
    private String phoneNumber;
    private String email;
    private Timestamp createTime = new Timestamp(System.currentTimeMillis());

    private static Long itemsPerPage = Long.valueOf(PageManager.getProperty(SettingsConstant.PAGINATION_USER_ITEMS_PER_PAGE));




    public User(String login, Role role, UserStatus status, String firstName, String secondName, String phoneNumber, String email) {
        this.login = login;
        this.role = role;
        this.status = status;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public User() {

    }
    public java.lang.String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public java.lang.String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public java.lang.String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static Long getItemsPerPage() {
        return User.itemsPerPage;
    }

    public static void setItemsPerPage(Long itemsPerPage) {
        User.itemsPerPage = itemsPerPage;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!login.equals(user.login)) return false;
        if (role != user.role) return false;
        if (status != user.status) return false;
        if (!firstName.equals(user.firstName)) return false;
        if (!secondName.equals(user.secondName)) return false;
        if (!phoneNumber.equals(user.phoneNumber)) return false;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + secondName.hashCode();
        result = 31 * result + phoneNumber.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
