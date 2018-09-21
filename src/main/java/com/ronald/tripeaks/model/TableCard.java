package com.ronald.tripeaks.model;

/**
 * @author Chen Runwen
 * @version 1.0 2018/9/10
 */
public class TableCard extends Card {

    public enum TableCardState {
        MOVED(0, "Moved"), BACK(1, "Back"), FRONT(2, "Front");
        private int index;
        private String name;

        TableCardState(int index, String name) {
            this.index = index;
            this.name = name;
        }
    }

    private TableCardState state;
    private int row;
    private int column;

    public TableCard(String suitStr, String pointStr, TableCardState state, int row, int column) {
        super(suitStr, pointStr);
        this.state = state;
        this.row = row;
        this.column = column;
    }

    public TableCard(String pointStr, int row, int column) {
        super(pointStr);
        this.state = row == 3 ? TableCardState.FRONT : TableCardState.BACK;
        this.row = row;
        this.column = column;
    }
}
