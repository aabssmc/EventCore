package cc.aabss.eventcore.hooks.skript;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.EnumUtils;
import cc.aabss.eventcore.api.VisibilityState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Types {
    static {
        EnumUtils<VisibilityState> states = new EnumUtils<>(VisibilityState.class, "visibilitystate");
        Classes.registerClass(new ClassInfo<>(VisibilityState.class, "visibilitystate")
                .user("visibility ?states?")
                .name("Visibility State")
                .description("Represents a visibility state.")
                .since("2.0")
                .parser(new Parser<>() {

                    @Override
                    @Nullable
                    public VisibilityState parse(@NotNull String input, @NotNull ParseContext context) {
                        return states.parse(input);
                    }

                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                        return true;
                    }

                    @Override
                    public @NotNull String toVariableNameString(VisibilityState state) {
                        return state.name().replaceAll("_", " ").toLowerCase();
                    }

                    @Override
                    public @NotNull String toString(VisibilityState state, int flags) {
                        return toVariableNameString(state);
                    }
                })
        );
    }
}
