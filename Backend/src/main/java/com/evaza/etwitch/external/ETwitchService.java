package com.evaza.etwitch.external;

import com.evaza.etwitch.external.model.Clip;
import com.evaza.etwitch.external.model.Game;
import com.evaza.etwitch.external.model.Stream;
import com.evaza.etwitch.external.model.Video;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 把 ETwitchAPIClient 这个 HTTP client 的实现细节藏起来，让Client端用的更爽
 * */

@Service
public class ETwitchService {
    private final ETwitchApiClient etwitchApiClient;

    public ETwitchService(ETwitchApiClient etwitchApiClient) {
        this.etwitchApiClient = etwitchApiClient;
    }

    // 在哪里加cache取决于这个class的功能设计，如这个cache也可以加在ETwitchApiClient里，但是那样那个class就不那么纯粹了
    @Cacheable("top_games")
    public List<Game> getTopGames() {
        return etwitchApiClient.getTopGames().data();
    }

    @Cacheable("games_by_name")
    public List<Game> getGames(String name) {
        return etwitchApiClient.getGames(name).data();
    }

    public List<Stream> getStreams(List<String> gameIds, int first) {
        return etwitchApiClient.getStreams(gameIds, first).data();
    }

    public List<Video> getVideos(String gameId, int first) {
        return etwitchApiClient.getVideos(gameId, first).data();
    }

    public List<Clip> getClips(String gameId, int first) {
        return etwitchApiClient.getClips(gameId, first).data();
    }

    public List<String> getTopGameIds() {
        List<String> topGameIds = new ArrayList<>();
        for (Game game : getTopGames()) {
            topGameIds.add(game.id());
        }
        return topGameIds;
    }
}
