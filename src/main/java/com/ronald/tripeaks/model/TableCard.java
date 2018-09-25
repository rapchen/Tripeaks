package com.ronald.tripeaks.model;

import lombok.Data;

/**
 * @author Chen Runwen
 * @version 1.0 2018/9/10
 */
@Data
public class TableCard extends Card {

    public enum State {
        MOVED(0, "Moved"), BACK(1, "Back"), FRONT(2, "Front");
        private int index;
        private String name;

        State(int index, String name) {
            this.index = index;
            this.name = name;
        }
    }

    private State state;
    private int row;
    private int column;

    public TableCard(String suitStr, String pointStr, State state, int row, int column) {
        super(suitStr, pointStr);
        this.state = state;
        this.row = row;
        this.column = column;
    }

    public TableCard(String pointStr, int row, int column) {
        super(pointStr);
        this.state = row == 3 ? State.FRONT : State.BACK;
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return "TableCard{" +
                "card=" + super.toString() +
                ", state=" + state +
                ", row=" + row +
                ", column=" + column +
                '}';
    }
}
