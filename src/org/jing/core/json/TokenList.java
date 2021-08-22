package org.jing.core.json;

import java.util.LinkedList;

/**
 * Created by code4wt on 17/5/19.
 */
@SuppressWarnings("unused") public class TokenList {

    private LinkedList<Token> tokens = new LinkedList<Token>();

    private int pos = 0;

    public void add(Token token) {
        tokens.addLast(token);
    }

    public void addFirst(Token token) {
        tokens.addFirst(token);
    }

    public Token peek() {
        return pos < tokens.size() ? tokens.get(pos) : null;
    }

    public Token peekPrevious() {
        return pos - 1 < 0 ? null : tokens.get(pos - 2);
    }

    public Token next() {
        return tokens.get(pos++);
    }

    public boolean hasMore() {
        return pos < tokens.size();
    }

    @Override
    public String toString() {
        return "TokenList{" +
                "tokens=" + tokens +
                '}';
    }
}
