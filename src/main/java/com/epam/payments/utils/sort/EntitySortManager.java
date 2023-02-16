package com.epam.payments.utils.sort;

public class EntitySortManager {
    String sortColumn = "id";
    SortOrder sortOrder = SortOrder.ASC;

    public EntitySortManager() {
    }

    public EntitySortManager(String sortColumn, SortOrder sortOrder) {
        this.sortColumn = sortColumn;
        this.sortOrder = sortOrder;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }
}
