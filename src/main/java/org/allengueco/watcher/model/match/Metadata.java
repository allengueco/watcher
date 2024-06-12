package org.allengueco.watcher.model.match;

import java.util.List;

public record Metadata(String dataVersion, String matchId, List<String> participants) {
}
