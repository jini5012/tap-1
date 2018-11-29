package com.nemosw.spigot.tap.entity;

import com.nemosw.spigot.tap.LibraryLoader;
import org.bukkit.entity.Entity;

public abstract class TapEntitySupport
{

    private static final TapEntitySupport INSTANCE = LibraryLoader.load(TapEntitySupport.class);

    public static TapEntitySupport getInstance()
    {
        return INSTANCE;
    }

    public abstract <T extends TapEntity> T wrapEntity(Entity entity);

    public abstract <T extends TapEntity> T createEntity(Class<? extends Entity> entityClass);

}