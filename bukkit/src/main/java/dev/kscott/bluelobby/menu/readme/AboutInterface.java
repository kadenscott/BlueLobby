package dev.kscott.bluelobby.menu.readme;

import dev.kscott.bluelobby.utils.Constants;
import dev.kscott.interfaces.core.transform.Transform;
import dev.kscott.interfaces.paper.pane.BookPane;
import dev.kscott.interfaces.paper.transform.PaperTransform;
import dev.kscott.interfaces.paper.type.BookInterface;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class AboutInterface extends BookInterface {

    @Override
    public @NonNull List<Transform<BookPane>> transformations() {
        return List.of(
                PaperTransform.bookText(Component.text()
                        .append(Constants.Chat.BAR_MEDIUM)
                        .append(Component.newline())
                        .append(Component.text("Welcome to mc.ksc.sh! This server showcases all the fun Minecraft projects and ideas I've worked on."))
                        .asComponent())
        );
    }

}
