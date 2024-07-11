package com.evaza.etwitch.recommendation;

import com.evaza.etwitch.db.entity.ItemEntity;
import com.evaza.etwitch.db.entity.UserEntity;
import com.evaza.etwitch.external.ETwitchService;
import com.evaza.etwitch.external.model.Clip;
import com.evaza.etwitch.external.model.Stream;
import com.evaza.etwitch.external.model.Video;
import com.evaza.etwitch.favorite.FavoriteService;
import com.evaza.etwitch.model.TypeGroupedItemList;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecommendationService {
    private static final int MAX_GAME_SEED = 3;         // 只依据3个game做推荐
    private static final int PER_PAGE_ITEM_SIZE = 20;   // 每个 category(clip, stream, video...) 只推荐20个

    private final ETwitchService etwitchService;
    private final FavoriteService favoriteService;

    public RecommendationService(ETwitchService etwitchService, FavoriteService favoriteService) {
        this.etwitchService = etwitchService;
        this.favoriteService = favoriteService;
    }

    // 若缓存时间内，特别快地喜欢过一个item然后刷新，得到的推荐还是基于之前的！
    // 要改变这个情况，就要在 add/unset favorite 时把缓存清掉
    @Cacheable("recommend_items")
    public TypeGroupedItemList recommendItems(UserEntity userEntity) {
        List<String> gameIds;
        Set<String> exclusions = new HashSet<>();
        if (userEntity == null) {
            gameIds  = etwitchService.getTopGameIds();
        } else {
            List<ItemEntity> items = favoriteService.getFavoriteItems(userEntity);
            if (items.isEmpty()) {
                gameIds = etwitchService.getTopGameIds();
            } else {
                Set<String> uniqueGameIds = new HashSet<>();
                for (ItemEntity item : items) {
                    uniqueGameIds.add(item.gameId());
                    exclusions.add(item.twitchId());
                }
                gameIds = new ArrayList<>(uniqueGameIds);
            }
        }

        int gameSize = Math.min(gameIds.size(), MAX_GAME_SEED);
        int perGameListSize = PER_PAGE_ITEM_SIZE / gameSize;

        List<ItemEntity> streams = recommendStreams(gameIds, exclusions);
        List<ItemEntity> clips = recommendClips(gameIds.subList(0, gameSize), perGameListSize, exclusions);
        List<ItemEntity> videos = recommendVideos(gameIds.subList(0, gameSize), perGameListSize, exclusions);

        return new TypeGroupedItemList(streams, videos, clips);
    }

    private List<ItemEntity> recommendStreams(List<String> gameIds, Set<String> exclusions) {
        List<Stream> streams = etwitchService.getStreams(gameIds, PER_PAGE_ITEM_SIZE);
        List<ItemEntity> resultItems = new ArrayList<>();
        for (Stream stream: streams) {
            if (!exclusions.contains(stream.id())) {
                resultItems.add(new ItemEntity(stream));
            }
        }
        return resultItems;
    }

    private List<ItemEntity> recommendVideos(List<String> gameIds, int perGameListSize, Set<String> exclusions) {
        List<ItemEntity> resultItems = new ArrayList<>();
        for (String gameId : gameIds) {
            List<Video> listPerGame = etwitchService.getVideos(gameId, perGameListSize);
            for (Video video : listPerGame) {
                if (!exclusions.contains(video.id())) {
                    resultItems.add(new ItemEntity(gameId, video));
                }
            }
        }
        return resultItems;
    }

    private List<ItemEntity> recommendClips(List<String> gameIds, int perGameListSize, Set<String> exclusions) {
        List<ItemEntity> resultItem = new ArrayList<>();
        for (String gameId : gameIds) {
            List<Clip> listPerGame = etwitchService.getClips(gameId, perGameListSize);
            for (Clip clip : listPerGame) {
                if (!exclusions.contains(clip.id())) {
                    resultItem.add(new ItemEntity(clip));
                }
            }
        }
        return resultItem;
    }

}
