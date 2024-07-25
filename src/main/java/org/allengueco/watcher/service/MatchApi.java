package org.allengueco.watcher.service;

import org.allengueco.watcher.model.match.Match;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;
import java.util.Map;

@HttpExchange(url = "/lol/match/v5/matches")
public interface MatchApi {
    @GetExchange("/by-puuid/{puuid}/ids")
    List<String> getMatchesByPuuid(@RequestParam Map<String, ?> requestParameter, @PathVariable String puuid);

    @GetExchange("/{matchId}")
    Match getMatch(@PathVariable String matchId);
}
