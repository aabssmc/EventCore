package lol.aabss.eventcore.hooks.skript;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.EnumUtils;
import lol.aabss.eventcore.events.VisibilityEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Types {
    static {
        EnumUtils<VisibilityEvent.VisibilityState> states = new EnumUtils<>(VisibilityEvent.VisibilityState.class, "visibilitystate");
        Classes.registerClass(new ClassInfo<>(VisibilityEvent.VisibilityState.class, "visibilitystate")
                .user("visibility ?states?")
                .name("Visibility State")
                .description("Represents a visibility state.")
                .since("2.0")
                .parser(new Parser<>() {

                    @Override
                    @Nullable
                    public VisibilityEvent.VisibilityState parse(@NotNull String input, @NotNull ParseContext context) {
                        return states.parse(input);
                    }

                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                        return true;
                    }

                    @Override
                    public @NotNull String toVariableNameString(VisibilityEvent.VisibilityState state) {
                        return state.name().replaceAll("_", " ").toLowerCase();
                    }

                    @Override
                    public @NotNull String toString(VisibilityEvent.VisibilityState state, int flags) {
                        return toVariableNameString(state);
                    }
                })
        );
    }
}
