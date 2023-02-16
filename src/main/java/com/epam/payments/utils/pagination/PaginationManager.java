package com.epam.payments.utils.pagination;

import com.epam.payments.model.entity.Entity;
import com.epam.payments.utils.sort.SortOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaginationManager<E extends Entity> {
    List<E> paginationList = new ArrayList<>();
    private Long page;
    private Long totalPages;

    public PaginationManager() {
    }
    public List<E> getPaginationList() {
        return paginationList;
    }

    public Long getPage() {
        return page;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setPaginationList(List<E> paginationList) {
        this.paginationList = paginationList;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaginationManager<?> that = (PaginationManager<?>) o;

        if (!Objects.equals(paginationList, that.paginationList))
            return false;
        if (!Objects.equals(page, that.page)) return false;
        return Objects.equals(totalPages, that.totalPages);
    }

    @Override
    public int hashCode() {
        int result = paginationList != null ? paginationList.hashCode() : 0;
        result = 31 * result + (page != null ? page.hashCode() : 0);
        result = 31 * result + (totalPages != null ? totalPages.hashCode() : 0);
        return result;
    }
}
