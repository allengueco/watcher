package org.allengueco.watcher.controller;

import org.allengueco.watcher.extractors.MatchSummonerPerformance;
import org.allengueco.watcher.model.Summoner;
import org.allengueco.watcher.model.match.Match;
import org.allengueco.watcher.service.AccountApi;
import org.allengueco.watcher.service.MatchApi;
import org.allengueco.watcher.service.WatcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class SummaryController {
    private static final Logger log = LoggerFactory.getLogger(SummaryController.class);
    WatcherService watcherService;

    SummaryController(WatcherService watcherService) {
        this.watcherService = watcherService;
    }

    /**
     * Get last match for the given riotId. The riotId is made with `gameName-tagLine`
     * @param riotId
     * @return
     */
    @GetMapping("/summary")
    public ResponseEntity<MatchSummonerPerformance> getLastMatchPerformance(@RequestParam String riotId) {
        Optional<Summoner> summoner = this.watcherService.getSummoner(riotId);
        if (summoner.isEmpty()) {
            log.error("not found {}", riotId);
            return ResponseEntity.notFound().build();
        }
        Summoner s = summoner.get();

        Optional<Match> matches = this.watcherService.getLatestMatches(s, 1)
                .map(List::getFirst);
        matches.ifPresentOrElse(
                m -> log.info("m: {}", m.metadata().matchId()),
                () -> log.error("not found match"));
        return ResponseEntity.of(matches.map(m -> new MatchSummonerPerformance(m, s.puuid())));
    }

    @GetMapping("/games/{n}")
    public ResponseEntity<List<Match>> getMatches(@RequestParam String riotId, @PathVariable int n) {
        Optional<Summoner> summoner = this.watcherService.getSummoner(riotId);
        if (summoner.isEmpty()) {
            log.error("not found {}", riotId);
            return ResponseEntity.notFound().build();
        }
        Summoner s = summoner.get();

        Optional<List<Match>> matches = this.watcherService.getLatestMatches(s, n);

        matches.ifPresentOrElse(
                m -> log.info("matches found: {}", m.size()),
                () -> log.error("no matches found"));
        return ResponseEntity.of(matches);
    }
}
