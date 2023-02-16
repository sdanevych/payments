package com.epam.payments.model.entity;

import com.epam.payments.utils.constant.SettingsConstant;
import com.epam.payments.utils.resource_manager.PageManager;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    private Long id;
    private static Long itemsPerPage;

    static {
        itemsPerPage = Long.valueOf(PageManager.getProperty(SettingsConstant.PAGINATION_DEFAULT_ITEMS_PER_PAGE));
    }

    public Entity() {
    }

    public Entity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static Long getItemsPerPage() {
        return Entity.itemsPerPage;
    }

    public static void setItemsPerPage(Long itemsPerPage) {
        Entity.itemsPerPage = itemsPerPage;
    }
}