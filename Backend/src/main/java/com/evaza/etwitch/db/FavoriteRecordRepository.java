package com.evaza.etwitch.db;

import com.evaza.etwitch.db.entity.FavoriteRecordEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface FavoriteRecordRepository extends ListCrudRepository<FavoriteRecordEntity, Long> {

    // SELECT * FROM favorite_records WHERE user_id = ?
    List<FavoriteRecordEntity> findAllByUserId(Long userId);

    // SELECT * FROM favorite_records WHERE user_id = ? AND item_id = ?
    boolean existsByUserIdAndItemId(Long userId, Long itemId);

    // 手写query, 这个的函数名可以随意，但是query里面的一定要写正确
    @Query("SELECT item_id FROM favorite_records WHERE user_id = :userId")
    List<Long> findFavoriteItemIdsByUserId(Long userId);

    // 除了read操作，写操作insert, update, insert要annotate @Modifying
    @Modifying
    @Query("DELETE FROM favorite_records WHERE user_id = :userId AND item_id = :itemId")
    void delete(Long userId, Long itemId);
}
