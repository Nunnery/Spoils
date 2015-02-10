package me.topplethenun.spoils.api.names;

public enum NameType {

    PART_ONE("NamePartOne"),
    PART_TWO("NamePartTwo");

    private final String path;

    private NameType(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }

}
