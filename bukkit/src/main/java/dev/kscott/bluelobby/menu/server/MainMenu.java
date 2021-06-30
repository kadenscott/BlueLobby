package dev.kscott.bluelobby.menu.server;

import dev.kscott.bluelobby.menu.Menu;
import dev.kscott.interfaces.paper.type.ChestInterface;
import org.checkerframework.checker.nullness.qual.NonNull;

public class MainMenu implements Menu<ChestInterface> {

    @Override
    public @NonNull ChestInterface get() {
        return ChestInterface.builder()
                .rows(1)
                .build();
    }
}
