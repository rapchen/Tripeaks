package com.ronald.tripeaks.model;

import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Chen Runwen
 * @version 1.0 2018/9/9
 */
@Data
public class Card {

    public enum Suit{
        NONE(0, "_"), SPADE(1, "♠"), HEART(2, "❤"), DIAMOND(3, "♦"), CLUB(4, "♣");

        private int index;
        private String str;

        private static Map<String, Suit> map = new HashMap<>();

        static {
            for (Suit suit1 : Suit.values()) {
                map.put(suit1.str, suit1);
            }
        }

        Suit(int index, String str) {
            this.index = index;
            this.str = str;
        }

        public static Suit getByName(String str) {
            return map.get(str);
        }

        @Override
        public String toString() {
            return str;
        }
    }

    @Getter
    public enum Point{
        NONE(0, "None"), ACE(1, "A"), TWO(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5, "5"), SIX(6, "6"),
        SEVEN(7, "7"), EIGHT(8, "8"), NINE(9, "9"), TEN(10, "0"), JACK(11, "J"), QUEEN(12, "Q"), KING(13, "K");

        private int index;
        private String str;

        private static Map<String, Point> map = new HashMap<>();

        static {
            for (Point point1 : Point.values()) {
                map.put(point1.str, point1);
            }
        }

        Point(int index, String str) {
            this.index = index;
            this.str = str;
        }

        public static Point getByName(String str) {
            return map.get(str);
        }

        public int[] getSiblingPoints() {
            int[] points = new int[2];
            points[0] = (index - 2) % 13 + 1;
            points[1] = index % 13 + 1;
            return points;
        }

        @Override
        public String toString() {
            return str;
        }
    }

    private Suit suit;
    private Point point;

    public Card(String suitStr, String pointStr) {
        this.suit = Suit.getByName(suitStr);
        this.point = Point.getByName(pointStr);
    }

    public Card(String pointStr) {
        this.suit = Suit.NONE;
        this.point = Point.getByName(pointStr);
    }

    @Override
    public String toString() {
        return suit.str + point.str;
    }
}
