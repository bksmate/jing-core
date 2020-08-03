package org.jing.core.json;

import org.jing.core.lang.Carrier;
import org.jing.core.lang.JingException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by code4wt on 17/5/19.
 */
public class Parser {
    private static final int BEGIN_OBJECT_TOKEN = 1;

    private static final int END_OBJECT_TOKEN = 2;

    private static final int BEGIN_ARRAY_TOKEN = 4;

    private static final int END_ARRAY_TOKEN = 8;

    private static final int NULL_TOKEN = 16;

    private static final int NUMBER_TOKEN = 32;

    private static final int STRING_TOKEN = 64;

    private static final int BOOLEAN_TOKEN = 128;

    private static final int SEP_COLON_TOKEN = 256;

    private static final int SEP_COMMA_TOKEN = 512;

    private TokenList tokens;

    private String rootNodeName;

    public Carrier parse(TokenList tokens, String rootNodeName) throws JingException {
        this.tokens = tokens;
        this.rootNodeName = rootNodeName;
        return parse();
    }

    private Carrier parse() throws JingException {
        Token token = tokens.next();
        if (token == null) {
            return new Carrier(rootNodeName);
        } else if (token.getTokenType() == TokenType.BEGIN_OBJECT) {
            return parserCarrier();
        } else {
            throw new JingException("Parse error, invalid Token.");
        }
    }

    private Carrier parserCarrier() throws JingException {
        Carrier retCarrier = new Carrier(rootNodeName);
        int expectToken = STRING_TOKEN | END_OBJECT_TOKEN;
        String key = null;
        Object value;
        while (tokens.hasMore()) {
            Token token = tokens.next();
            TokenType tokenType = token.getTokenType();
            String tokenValue = token.getValue();
            switch (tokenType) {
                case BEGIN_OBJECT:
                    checkExpectToken(tokenType, expectToken);
                    retCarrier.setValueByKey(key, parserCarrier());
                    expectToken = SEP_COMMA_TOKEN | END_OBJECT_TOKEN;
                    break;
                case END_OBJECT:
                    checkExpectToken(tokenType, expectToken);
                    return retCarrier;
                case BEGIN_ARRAY:
                    checkExpectToken(tokenType, expectToken);
                    retCarrier.setValueByKey(key, parserJsonArray2CarrierList());
                    expectToken = SEP_COMMA_TOKEN | END_OBJECT_TOKEN;
                    break;
                case NULL:
                    checkExpectToken(tokenType, expectToken);
                    retCarrier.setValueByKey(key, null);
                    expectToken = SEP_COMMA_TOKEN | END_OBJECT_TOKEN;
                    break;
                case NUMBER:
                    checkExpectToken(tokenType, expectToken);
                    if (tokenValue.contains(".") || tokenValue.contains("e") || tokenValue.contains("E")) {
                        retCarrier.setValueByKey(key, Double.valueOf(tokenValue));
                    } else {
                        Long num = Long.valueOf(tokenValue);
                        if (num > Integer.MAX_VALUE || num < Integer.MIN_VALUE) {
                            retCarrier.setValueByKey(key, num);
                        } else {
                            retCarrier.setValueByKey(key, num.intValue());
                        }
                    }
                    expectToken = SEP_COMMA_TOKEN | END_OBJECT_TOKEN;
                    break;
                case BOOLEAN:
                    checkExpectToken(tokenType, expectToken);
                    retCarrier.setValueByKey(key, Boolean.valueOf(token.getValue()));
                    expectToken = SEP_COMMA_TOKEN | END_OBJECT_TOKEN;
                    break;
                case STRING:
                    checkExpectToken(tokenType, expectToken);
                    Token preToken = tokens.peekPrevious();
                    if (preToken.getTokenType() == TokenType.SEP_COLON) {
                        value = token.getValue();
                        retCarrier.setValueByKey(key, value);
                        // jsonObject.put(key, value);
                        expectToken = SEP_COMMA_TOKEN | END_OBJECT_TOKEN;
                    } else {
                        key = token.getValue();
                        expectToken = SEP_COLON_TOKEN;
                    }
                    break;
                case SEP_COLON:
                    checkExpectToken(tokenType, expectToken);
                    expectToken = NULL_TOKEN | NUMBER_TOKEN | BOOLEAN_TOKEN | STRING_TOKEN
                        | BEGIN_OBJECT_TOKEN | BEGIN_ARRAY_TOKEN;
                    break;
                case SEP_COMMA:
                    checkExpectToken(tokenType, expectToken);
                    expectToken = STRING_TOKEN;
                    break;
                case END_DOCUMENT:
                    checkExpectToken(tokenType, expectToken);
                    return retCarrier;
                default:
                    throw new JingException("Unexpected Token.");
            }
        }
        throw new JingException("Parse error, invalid Token.");
    }

    private List<Object> parserJsonArray2CarrierList() throws JingException {
        int expectToken = BEGIN_ARRAY_TOKEN | END_ARRAY_TOKEN | BEGIN_OBJECT_TOKEN | NULL_TOKEN
            | NUMBER_TOKEN | BOOLEAN_TOKEN | STRING_TOKEN;
        List<Object> retList = new ArrayList<Object>();
        while (tokens.hasMore()) {
            Token token = tokens.next();
            TokenType tokenType = token.getTokenType();
            String tokenValue = token.getValue();
            switch (tokenType) {
                case BEGIN_OBJECT:
                    checkExpectToken(tokenType, expectToken);
                    retList.add(parserCarrier());
                    expectToken = SEP_COMMA_TOKEN | END_ARRAY_TOKEN;
                    break;
                case BEGIN_ARRAY:
                    checkExpectToken(tokenType, expectToken);
                    retList.add(parserJsonArray2CarrierList());
                    expectToken = SEP_COMMA_TOKEN | END_ARRAY_TOKEN;
                    break;
                case END_ARRAY:
                    checkExpectToken(tokenType, expectToken);
                    return retList;
                case NULL:
                    checkExpectToken(tokenType, expectToken);
                    retList.add(null);
                    expectToken = SEP_COMMA_TOKEN | END_ARRAY_TOKEN;
                    break;
                case NUMBER:
                    checkExpectToken(tokenType, expectToken);
                    if (tokenValue.contains(".") || tokenValue.contains("e") || tokenValue.contains("E")) {
                        retList.add(Double.valueOf(tokenValue));
                    } else {
                        Long num = Long.valueOf(tokenValue);
                        if (num > Integer.MAX_VALUE || num < Integer.MIN_VALUE) {
                            retList.add(num);
                        } else {
                            retList.add(num.intValue());
                        }
                    }
                    expectToken = SEP_COMMA_TOKEN | END_ARRAY_TOKEN;
                    break;
                case BOOLEAN:
                    checkExpectToken(tokenType, expectToken);
                    retList.add(Boolean.valueOf(tokenValue));
                    expectToken = SEP_COMMA_TOKEN | END_ARRAY_TOKEN;
                    break;
                case STRING:
                    checkExpectToken(tokenType, expectToken);
                    retList.add(tokenValue);
                    expectToken = SEP_COMMA_TOKEN | END_ARRAY_TOKEN;
                    break;
                case SEP_COMMA:
                    checkExpectToken(tokenType, expectToken);
                    expectToken = STRING_TOKEN | NULL_TOKEN | NUMBER_TOKEN | BOOLEAN_TOKEN
                        | BEGIN_ARRAY_TOKEN | BEGIN_OBJECT_TOKEN;
                    break;
                case END_DOCUMENT:
                    checkExpectToken(tokenType, expectToken);
                    return retList;
                default:
                    throw new JingException("Unexpected Token.");
            }
        }
        throw new JingException("Parse error, invalid Token.");
    }

    private void checkExpectToken(TokenType tokenType, int expectToken) throws JingException {
        if ((tokenType.getTokenCode() & expectToken) == 0) {
            throw new JingException("Parse error, invalid Token.");
        }
    }
}