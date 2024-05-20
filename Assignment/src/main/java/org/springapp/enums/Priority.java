package org.springapp.enums;

public enum Priority {
    HIGH(0),MEDIUM(1),LOW(2);

    private int priority;

    Priority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
