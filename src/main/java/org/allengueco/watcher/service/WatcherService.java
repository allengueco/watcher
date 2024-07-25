package org.allengueco.watcher.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.allengueco.watcher.model.Summoner;
import org.allengueco.watcher.model.match.Match;
import org.springframework.stereotype.Service;

@Service
public class WatcherService {
    MatchApi matchApi;
    AccountApi accountApi;

    WatcherService(MatchApi matchApi, AccountApi accountApi) {
        this.matchApi = matchApi;
        this.accountApi = accountApi;
    }

    public Optional<Summoner> getSummoner(String riotId) {
        return parseRiotId(riotId)
                .map(id -> accountApi.getByRiotId(id[0], id[1]));
    }

    public Optional<List<Match>> getLatestMatches(Summoner summoner, int n) {
        if (n < Range.CHUNK_SIZE) {
            List<Range> rs = chunkRanges(n);
        }

        List<Match> matches = matchApi.getMatchesByPuuid(Map.of("count", n), summoner.puuid())
                .stream()
                .map(matchApi::getMatch)
                .toList();

        if (matches.isEmpty())
            return Optional.empty();

        return Optional.of(matches);
    }

    public Optional<List<Match>> getLatestMatches(String riotId, int n) {
        return getSummoner(riotId)
                .flatMap(summoner -> this.getLatestMatches(summoner, n));
    }

    private Optional<String[]> parseRiotId(String riotId) {
        String[] id = riotId.split("-");
        if (id.length != 2)
            return Optional.empty();
        else
            return Optional.of(new String[] { id[0], id[1] });

    }

    private record Range(int startIndex, int count) {
        static final int CHUNK_SIZE = 100;
    }

    private List<Range> chunkRanges(int n) {
        int current = 0;
        List<Range> ranges = new ArrayList<>();
        while (n < Range.CHUNK_SIZE) {
            ranges.add(new Range(current, Range.CHUNK_SIZE));

            current += Range.CHUNK_SIZE;
            n -= Range.CHUNK_SIZE;
        }

        // if there are more to process that's less than CHUNK_SIZE
        if (n > 0) {
            ranges.add(new Range(current, current + n));
        }

        return ranges;
    }

}
