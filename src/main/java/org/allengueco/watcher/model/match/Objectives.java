package org.allengueco.watcher.model.match;

public record Objectives(
        Objective baron,
        Objective champion,
        Objective dragon,
        Objective horde,
        Objective inhibitor,
        Objective riftHerald,
        Objective tower
) {
}
