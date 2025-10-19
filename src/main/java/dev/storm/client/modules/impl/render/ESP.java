package dev.storm.client.modules.impl.render;

import dev.storm.client.modules.Category;
import dev.storm.client.modules.Module;

public class ESP extends Module {

    public ESP() {
        super("ESP", "Highlights entities.", Category.RENDER, 0);
    }

    // This module will require a mixin to render the outlines.
    // The logic will be added later in the appropriate mixin class.
}
