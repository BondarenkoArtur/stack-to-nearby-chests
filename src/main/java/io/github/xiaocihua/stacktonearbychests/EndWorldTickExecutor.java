package io.github.xiaocihua.stacktonearbychests;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.PlayerScreenHandler;

import java.util.LinkedList;
import java.util.Queue;

public class EndWorldTickExecutor {

    private static final Queue<Runnable> tasks = new LinkedList<>();

    public static void init() {
        ClientTickEvents.END_WORLD_TICK.register(world -> {
            while (tasks.peek() != null) {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.player != null && client.currentScreen instanceof PlayerScreenHandler) {
                    client.player.closeHandledScreen(); // This might fix issue with ChestTracker
                }
                tasks.poll().run();
            }
        });
    }

    public static void execute(Runnable task) {
        tasks.add(task);
    }
}
