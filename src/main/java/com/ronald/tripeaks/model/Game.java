package com.ronald.tripeaks.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Chen Runwen
 * @version 1.0 2018/9/10
 */
@Data
public class Game {

    private TableCard[][] table;
    private int remainTableCardNUm = 28;
    /* 桌面上每张卡的行列数 */
    public static final int[] tableCardsRow = {0,0,0,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,3};
    public static final int[] tableCardsColumn = {0,3,6,0,1,3,4,6,7,0,1,2,3,4,5,6,7,8,0,1,2,3,4,5,6,7,8,9};

    private Card[] pile;

    private int pilePosition;

    public Game(String tableStr, String pileStr) {
        this.table = getTableFromStr(tableStr);
        this.pile = getPileFromStr(pileStr);
        this.pilePosition = 0;
    }

    public Boolean solve() {
        return solve(0, pile[0].getPoint());
    }

    private Boolean solve(int pilePosition, Card.Point lastPoint) {
        List<TableCard> removeable = getRemoveableCards();
        if (removeable != null) {
            for (TableCard nextCard: removeable) {
                remove(nextCard);
                if (remainTableCardNUm == 0) {
                    return true;
                }
                if (solve(pilePosition, nextCard.getPoint())) {
                    return true;
                }
                putback(nextCard);
            }
        }
        return false;
    }

    private void remove(TableCard nextCard) {
    }

    private void putback(TableCard nextCard) {
    }

    private List<TableCard> getRemoveableCards() {
        return null;
    }

    private TableCard[][] getTableFromStr(String tableStr) {
        TableCard[][] table = new TableCard[4][10];
        Iterator<String> points = new ArrayList<>(Arrays.asList(tableStr.split(" "))).iterator();
        for (int i = 0; i < 28; i++) {
            table[tableCardsRow[i]][tableCardsColumn[i]] = new TableCard(points.next(), tableCardsRow[i], tableCardsColumn[i]);
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

}
