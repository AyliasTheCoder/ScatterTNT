package com.aylias.minecraft.mods.scattertnt.blocks.properties;

import net.minecraft.util.IStringSerializable;

public enum TNTType implements IStringSerializable {
    COBBLESTONE("cobblestone");


    private String field;

    private TNTType(String field) {

        this.field = field;
    }

    @Override
    public String func_176610_l() {
        return field;
    }
}
