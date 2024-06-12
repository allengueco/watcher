package org.allengueco.watcher.model.match;

import java.util.List;

public record Team(List<Ban> bans, Objectives objectives, int teamId, boolean win) {
}
