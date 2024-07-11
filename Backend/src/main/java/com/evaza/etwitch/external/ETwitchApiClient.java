package com.evaza.etwitch.external;

import com.evaza.etwitch.external.model.ClipResponse;
import com.evaza.etwitch.external.model.GameResponse;
import com.evaza.etwitch.external.model.StreamResponse;
import com.evaza.etwitch.external.model.VideoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// 向外发请求： ETwitchApiClient - 要根据发的URL具体是什么来 call annotation - then call function
// How to specify? - 用一样的 annotation

@FeignClient(name = "twitch-api")
public interface ETwitchApiClient {
    @GetMapping("/games")
    GameResponse getGames(@RequestParam("name") String name);


    @GetMapping("/games/top")
    GameResponse getTopGames();


    @GetMapping("/videos/")
    VideoResponse getVideos(@RequestParam("game_id") String gameId, @RequestParam("first") int first);


    @GetMapping("/clips/")
    ClipResponse getClips(@RequestParam("game_id") String gameId, @RequestParam("first") int first);


    @GetMapping("/streams/")
    StreamResponse getStreams(@RequestParam("game_id") List<String> gameIds, @RequestParam("first") int first);
}
