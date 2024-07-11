package com.evaza.etwitch.model;

import com.evaza.etwitch.db.entity.ItemEntity;

public record FavoriteRequestBody(
        ItemEntity favorite
) {
}
