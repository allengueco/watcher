package org.allengueco.watcher.extractors;

import org.allengueco.watcher.model.Summoner;
import org.allengueco.watcher.model.match.Match;
import org.allengueco.watcher.model.match.Participant;

public class MatchSummonerPerformance {
    String puuid;
    String matchId;
    String gameVersion;
    String gameMode;
    String championName;
    int totalDamageDealtToChampions;
    int kills, deaths, assists;

    MatchSummonerPerformance(Match match, Summoner summoner) {
        this(match, summoner.puuid());
    }
    public MatchSummonerPerformance(Match match, String puuid) {
        this.puuid = puuid;
        this.matchId = match.metadata().matchId();

        int index = match.metadata().participants().indexOf(this.puuid);
        Participant p = match.info().participants().get(index);

        this.totalDamageDealtToChampions = p.totalDamageDealtToChampions();
        this.kills = p.kills();
        this.deaths = p.deaths();
        this.assists = p.assists();
        this.championName = p.championName();
        this.gameVersion = match.info().gameVersion();
        this.gameMode = match.info().gameMode();
    }

    @Override
    public String toString() {
        return "MatchSummonerPerformance{" +
                "puuid='" + puuid + '\'' +
                ", matchId='" + matchId + '\'' +
                ", gameVersion='" + gameVersion + '\'' +
                ", gameMode='" + gameMode + '\'' +
                ", championName='" + championName + '\'' +
                ", totalDamageDealtToChampions=" + totalDamageDealtToChampions +
                ", kills=" + kills +
                ", deaths=" + deaths +
                ", assists=" + assists +
                '}';
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public int getTotalDamageDealtToChampions() {
        return totalDamageDealtToChampions;
    }

    public void setTotalDamageDealtToChampions(int totalDamageDealtToChampions) {
        this.totalDamageDealtToChampions = totalDamageDealtToChampions;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }
}
