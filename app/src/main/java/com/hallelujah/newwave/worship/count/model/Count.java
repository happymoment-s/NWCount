package com.hallelujah.newwave.worship.count.model;

import io.realm.RealmObject;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Count extends RealmObject {
    public enum Type {
        MAN,
        WOMAN
    }

    private int manCount = 0;
    private int womanCount = 0;

    public void addManCount() {
        this.manCount++;
    }

    public void addWomanCount() {
        this.womanCount++;
    }

    public void removeManCount() {
        this.manCount--;
    }

    public void removeWomanCount() {
        this.womanCount--;
    }
}
