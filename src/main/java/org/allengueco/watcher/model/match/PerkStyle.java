package org.allengueco.watcher.model.match;

import java.util.List;

public record PerkStyle(String description, List<PerkStyleSelection> selections, int style) {
}
