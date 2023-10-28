package com.maxsella.fatmuscle.model.enums;

public enum Function {
    FAT(1),
    MUSCLE(2);

    private final int value;

    Function(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
