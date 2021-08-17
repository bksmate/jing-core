package org.jing.core.json;

/**
 * Created by code4wt on 17/5/10.
 */
@SuppressWarnings("unused") public class Token {

    private TokenType tokenType;

    private String value;

    Token(TokenType tokenType, String value) {
        this.tokenType = tokenType;
        this.value = value;
    }

    TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenType=" + tokenType +
                ", value='" + value + '\'' +
                '}';
    }
}
