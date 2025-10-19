package dev.storm.client.modules.impl.render;

import dev.storm.client.modules.Category;
import dev.storm.client.modules.Module;

public class FPSBoost extends Module {

    public FPSBoost() {
        super("FPSBoost", "Disables entity shadows for a performance boost.", Category.RENDER, 0);
    }
}
