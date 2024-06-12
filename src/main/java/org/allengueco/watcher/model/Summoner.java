package org.allengueco.watcher.model;

public record Summoner(String gameName, String tagLine, String puuid) {
    /**
     * Valid if gameName is 3-16 characters and tagLine is 3-5 characters long.
     */
    boolean isValid() {
        return isLengthBetweenInclusive(this.gameName, 3, 16)
                && isLengthBetweenInclusive(this.tagLine, 3, 5);
    }
    boolean isLengthBetweenInclusive(String s, int start, int end) {
        var length = s.length();
        return length >= start && length <= end;
    }
}
