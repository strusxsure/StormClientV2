package dev.storm.client.modules.impl.combat;

import dev.storm.client.modules.Category;
import dev.storm.client.modules.Module;
import dev.storm.client.utils.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class KillAura extends Module {

    private final Timer timer = new Timer();

    public KillAura() {
        super("KillAura", "Automatically attacks entities in range.", Category.COMBAT, 0);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) {
            return;
        }

        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof PlayerEntity && entity != mc.player) {
                if (mc.player.distanceTo(entity) <= 4.0f && timer.hasPassed(500)) {
                    mc.interactionManager.attackEntity(mc.player, entity);
                    mc.player.swingHand(Hand.MAIN_HAND);
                    timer.reset();
                }
            }
        }
    }
}
