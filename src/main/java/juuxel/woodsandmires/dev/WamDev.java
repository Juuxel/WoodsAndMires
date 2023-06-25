package juuxel.woodsandmires.dev;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static net.minecraft.server.command.CommandManager.literal;

public final class WamDev {
    private static final Gson GSON = new Gson();
    private static final SimpleCommandExceptionType NO_MARKED_POSITION = new SimpleCommandExceptionType(new LiteralMessage("No marked position"));
    private static final DynamicCommandExceptionType EXCEPTION_COMMAND =
        new DynamicCommandExceptionType(exception -> new LiteralMessage("Exception: " + ((Exception) exception).getMessage()));
    private static Path marked;

    public static void init() {
        try {
            Path wamDir = FabricLoader.getInstance().getConfigDir().resolve("woods_and_mires");
            Files.createDirectories(wamDir);
            marked = wamDir.resolve("marked_position.json");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("wam")
                .then(literal("mark").requires(source -> source.hasPermissionLevel(4)).executes(WamDev::mark))
                .then(literal("recall").requires(source -> source.hasPermissionLevel(4)).executes(WamDev::recall)));
        });
    }

    private static int mark(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        try {
            BlockPos pos = context.getSource().getPlayer().getBlockPos();
            JsonElement json = BlockPos.CODEC.encodeStart(JsonOps.INSTANCE, pos)
                .getOrThrow(false, error -> {});
            Files.writeString(marked, GSON.toJson(json));
            context.getSource().sendFeedback(() -> Text.literal("Marked " + pos.toShortString()).formatted(Formatting.GREEN), false);
        } catch (Exception e) {
            throw EXCEPTION_COMMAND.create(e);
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int recall(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        try {
            if (Files.notExists(marked)) {
                throw NO_MARKED_POSITION.create();
            }

            JsonElement json = GSON.fromJson(Files.readString(marked), JsonElement.class);
            BlockPos pos = BlockPos.CODEC.decode(JsonOps.INSTANCE, json)
                .getOrThrow(false, error -> {})
                .getFirst();
            context.getSource().sendFeedback(() -> Text.literal("Recalling " + pos.toShortString()).formatted(Formatting.GREEN), false);
            context.getSource().getPlayer().teleport(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        } catch (CommandSyntaxException e) {
            throw e;
        } catch (Exception e) {
            throw EXCEPTION_COMMAND.create(e);
        }

        return Command.SINGLE_SUCCESS;
    }
}
