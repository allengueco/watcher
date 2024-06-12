package org.allengueco.watcher.model.match;

import java.util.List;

public record Info(
        String endOfGameResult,
        Long gameCreation,
        Long gameDuration,
        Long gameEndTimestamp,
        Long gameId,
        String gameMode,
        String gameName,
        Long gameStartTimestamp,
        String gameType,
        String gameVersion,
        Integer mapId,
        List<Participant> participants,
        String platformId,
        Integer queueId,
        List<Team> teams,
        String tournamentCode
) {
}
