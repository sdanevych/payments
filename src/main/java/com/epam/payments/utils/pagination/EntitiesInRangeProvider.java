package com.epam.payments.utils.pagination;

import com.epam.payments.model.entity.Entity;

import java.util.List;
import java.util.function.BiFunction;

public interface EntitiesInRangeProvider extends BiFunction<Long, Long, List<? extends Entity>> {
    List<? extends Entity> apply(Long offset, Long itemsPerPage);
}