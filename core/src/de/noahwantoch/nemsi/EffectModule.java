package de.noahwantoch.nemsi;

public enum EffectModule{

    //Destruction
    DESTROY_N,
    DESTROY_ALL_OTHER,
    DESTROY_ALL,

    //Healing
    HEAL_N,
    HEAL_ALL_OTHER,
    HEAL_ALL,
    HEAL_TEAM,
    HEAL_HERO,

    //Damage
    DAMAGE_N,
    DAMAGE_HERO,
    DAMAGE_ALL,

    //Drawing
    DRAW_N,
    DRAW_SPECIFIC_CARD,

    //No effect
    NO_EFFECT
}
