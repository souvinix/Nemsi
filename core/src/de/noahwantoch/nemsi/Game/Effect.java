package de.noahwantoch.nemsi.Game;

import de.noahwantoch.nemsi.EffectModule;

public class Effect {
    private String description;
    private Element target;
    private EffectModule module;
    private int amount;

    public Effect(String description, EffectModule module, int amount){
        init(description, module, amount);
        this.target = Element.NO_ELEMENT;
    }

    public Effect(String description, EffectModule module, int amount, Element target){
        init(description, module, amount);
        this.target = target;
    }

    private void init(String description, EffectModule module, int amount){
        this.description = description;
        this.module = module;
        this.amount = amount;
    }

    public String getDescription(){ return description; }
}
