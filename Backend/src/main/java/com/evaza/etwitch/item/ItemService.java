package com.evaza.etwitch.item;

import com.evaza.etwitch.external.ETwitchService;
import com.evaza.etwitch.external.model.Clip;
import com.evaza.etwitch.external.model.Stream;
import com.evaza.etwitch.external.model.Video;
import com.evaza.etwitch.model.TypeGroupedItemList;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private static final int SEARCH_RESULT_SIZE = 20;
    private final ETwitchService etwitchService;

    public ItemService(ETwitchService etwitchService) {
        this.etwitchService = etwitchService;
    }

    @Cacheable("items")
    public TypeGroupedItemList getItems(String gameId) {
        List<Video> videos = etwitchService.getVideos(gameId, SEARCH_RESULT_SIZE);
        List<Clip> clips = etwitchService.getClips(gameId, SEARCH_RESULT_SIZE);
        List<Stream> streams = etwitchService.getStreams(List.of(gameId), SEARCH_RESULT_SIZE);
        return new TypeGroupedItemList(gameId, streams, videos, clips);
    }

}
