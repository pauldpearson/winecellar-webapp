package com.cellar.wine.services;

import com.cellar.wine.models.Tasted;

public interface TastedService extends CrudService<Tasted, Long> {

    Tasted findByUser(Long wineId, Integer userId);

}
