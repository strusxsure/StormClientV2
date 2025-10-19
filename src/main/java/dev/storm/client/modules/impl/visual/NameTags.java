package dev.storm.client.modules.impl.visual;

import dev.storm.client.modules.Category;
import dev.storm.client.modules.Module;

public class NameTags extends Module {

    public NameTags() {
        super("NameTags", "Makes nametags larger and more visible.", Category.VISUAL, 0);
    }

    // This module will also require a mixin to modify the rendering of nametags.
    // The logic will be added later in the appropriate mixin class.
}
