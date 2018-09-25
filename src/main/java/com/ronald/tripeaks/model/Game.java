package com.ronald.tripeaks.model;

import lombok.Data;

import java.util.*;

/**
 * @author Chen Runwen
 * @version 1.0 2018/9/10
 */
@Data
public class Game {

    private TableCard[][] table;
    private int remainTableCardNum = 28;
    private Map<Integer, List<TableCard>> pointCardMap = new HashMap<>();
    /* 桌面上每张卡的行列数 */
    public static final int[] tableCardsRow = {0,0,0,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,3};
    public static final int[] tableCardsColumn = {0,3,6,0,1,3,4,6,7,0,1,2,3,4,5,6,7,8,0,1,2,3,4,5,6,7,8,9};

    private Card[] pile;

    private int pilePosition;

    public Game(String tableStr, String pileStr) {
        // 从点数字符串构造桌面和牌堆
        for (Card.Point point: Card.Point.values()) {
            pointCardMap.put(point.getIndex(), new ArrayList<>());
        }
        this.table = getTableFromStr(tableStr);
        this.pile = getPileFromStr(pileStr);
        this.pilePosition = 0;
    }

    public Boolean solve() {
        return solve(pile[0].getPoint());
    }

    private Boolean solve(Card.Point lastPoint) {
        List<TableCard> removeable = getRemoveableCards(lastPoint);
        if (removeable != null && !removeable.isEmpty()) {
            for (TableCard nextCard: removeable) {
                remove(nextCard);
                if (remainTableCardNum == 0) {
                    System.out.println("成功！");
                    System.out.print(nextCard);
                    return true;
                }
                if (solve(nextCard.getPoint())) {
                    System.out.print(" <- " + nextCard);
                    return true;
                }
                putback(nextCard);
            }
        }
        if (pilePosition == 23) return false;
        pilePosition += 1;
        if (solve(pile[pilePosition].getPoint())) {
            System.out.print(" <- " + pile[pilePosition]);
            return true;
        }
        pilePosition -= 1;
        return false;
    }

    private List<TableCard> getRemoveableCards(Card.Point lastPoint) {
        List<TableCard> cards = new ArrayList<>();
        for (int point: lastPoint.getSiblingPoints()) {
            for (TableCard card: pointCardMap.get(point)) {
                if (card.getState() == TableCard.State.FRONT) {
                    cards.add(card);
                }
            }
        }
        return cards;
    }

    private void remove(TableCard card) {
        card.setState(TableCard.State.MOVED);
        int row = card.getRow(), column = card.getColumn();
        if (!hasCard(row,  column - 1)) {
            turn(card.getRow() - 1, card.getColumn() - 1, TableCard.State.FRONT);
        }
        if (!hasCard(row,  column + 1)) {
            turn(card.getRow() - 1, card.getColumn(), TableCard.State.FRONT);
        }
    }

    private void putback(TableCard card) {
        card.setState(TableCard.State.FRONT);
        int row = card.getRow(), column = card.getColumn();
        if (hasCard(row,  column - 1)) {
            turn(card.getRow() - 1, card.getColumn() - 1, TableCard.State.BACK);
        }
        if (hasCard(row,  column + 1)) {
            turn(card.getRow() - 1, card.getColumn(), TableCard.State.BACK);
        }
    }

    private boolean hasCard(int row, int column) {
        return column >= 0 && column <= 9
                && row >= 0 && row <= 3
                && table[row][column] != null
                && table[row][column].getState() != TableCard.State.MOVED;
    }

    private void turn(int row, int column, TableCard.State state) {
        if (column < 0 || column > 9
                || row < 0 || row > 3
                || table[row][column] == null) return;
        table[row][column].setState(state);
    }

    private TableCard[][] getTableFromStr(String tableStr) {
        TableCard[][] table = new TableCard[4][10];
        Iterator<String> points = new ArrayList<>(Arrays.asList(tableStr.split(" "))).iterator();
        for (int i = 0; i < 28; i++) {
            int row = tableCardsRow[i], column = tableCardsColumn[i];
            table[row][column] = new TableCard(points.next(), row, column);
            pointCardMap.get(table[row][column].getPoint().getIndex()).add(table[row][column]);
        }
        return table;
    }

    private Card[] getPileFromStr(String pileStr) {
        Card[] pile = new Card[24];
        Iterator<String> points = new ArrayList<>(Arrays.asList(pileStr.split(" "))).iterator();
        for (int i = 0; i < 24; i++) {
            pile[i] = new Card(points.next());
        }
        return pile;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // 桌面情况
        for (int row = 0; row < 4; row++) {
            for (int i = 0; i < 3-row; i++) {
                sb.append(' ');
            }
            for (int column = 0; column < 7+row; column++) {
                sb.append(hasCard(row, column) ? table[row][column].getPoint() : ' ');
                sb.append(' ');
            }
            sb.append('\n');
        }
        // 牌堆情况
        sb.append("Pile top: ").append(pile[pilePosition].getPoint()).append(" , position: ").append(pilePosition);
        return sb.toString();
    }

    public static void main(String[] args) {
        Game game = new Game("A 6 8 4 Q 2 A 4 Q 9 9 Q A 2 5 K 3 5 9 J 6 6 4 7 7 2 8 K", "K 3 2 0 K 7 5 J 9 5 6 8 3 J Q 0 0 7 3 0 4 J 8 A");
        game.solve();
    }
}
