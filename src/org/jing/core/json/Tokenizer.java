package org.jing.core.json;

import org.jing.core.lang.JingException;

/**
 * Created by code4wt on 17/5/10.
 */
@SuppressWarnings("Duplicates") public class Tokenizer {

    private CharReader charReader;

    private TokenList tokens;

    public TokenList tokenize(CharReader charReader) throws JingException {
        this.charReader = charReader;
        tokens = new TokenList();
        tokenize();

        return tokens;
    }

    private void tokenize() throws JingException {
        // 使用do-while处理空文件
        Token token;
        do {
            token = start();
            tokens.add(token);
        } while (token.getTokenType() != TokenType.END_DOCUMENT);
    }

    private Token start() throws JingException {
        char ch;
        do {
            if (!charReader.hasMore()) {
                return new Token(TokenType.END_DOCUMENT, null);
            }

            ch = charReader.next();
        }
        while (isWhiteSpace(ch));

        switch (ch) {
            case '{':
                return new Token(TokenType.BEGIN_OBJECT, String.valueOf(ch));
            case '}':
                return new Token(TokenType.END_OBJECT, String.valueOf(ch));
            case '[':
                return new Token(TokenType.BEGIN_ARRAY, String.valueOf(ch));
            case ']':
                return new Token(TokenType.END_ARRAY, String.valueOf(ch));
            case ',':
                return new Token(TokenType.SEP_COMMA, String.valueOf(ch));
            case ':':
                return new Token(TokenType.SEP_COLON, String.valueOf(ch));
            case 'n':
                return readNull();
            case 't':
            case 'f':
                return readBoolean();
            case '"':
                return readString();
            case '-':
                return readNumber();
        }

        if (isDigit(ch)) {
            return readNumber();
        }

        throw new JingException("Illegal character");
    }

    private boolean isWhiteSpace(char ch) {
        return (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n');
    }

    private Token readString() throws JingException {
        StringBuilder sb = new StringBuilder();
        for (;;) {
            char ch = charReader.next();
            if (ch == '\\') {
                ch = charReader.next();
                switch (ch) {
                    case '"':
                    case '\\':
                    case '/':
                        sb.append(ch);
                        break;
                    case 'u':
                        for (int i = 0; i < 4; i++) {
                            ch = charReader.next();
                            if (isHex(ch)) {
                                sb.append(ch);
                            } else {
                                throw new JingException("Invalid character");
                            }
                        }
                        break;
                    case 'r':
                        sb.append('\r');
                        break;
                    case 'n':
                        sb.append('\n');
                        break;
                    case 'b':
                        sb.append('\b');
                        break;
                    case 't':
                        sb.append('\t');
                        break;
                    case 'f':
                        sb.append('\f');
                        break;
                    default:
                        throw new JingException("Invalid escape character");
                }
            } else if (ch == '"') {
                return new Token(TokenType.STRING, sb.toString());
            } else if (ch == '\r' || ch == '\n') {
                throw new JingException("Invalid character");
            } else {
                sb.append(ch);
            }
        }
    }

    /*@Deprecated
    private boolean isEscape() throws JingException {
        char ch = charReader.next();
        return (ch == '"' || ch == '\\' || ch == 'u' || ch == 'r'
            || ch == 'n' || ch == 'b' || ch == 't' || ch == 'f' || ch == '/');

    }
    */
    private boolean isHex(char ch) {
        return ((ch >= '0' && ch <= '9') || ('a' <= ch && ch <= 'f')
            || ('A' <= ch && ch <= 'F'));
    }

    private Token readNumber() throws JingException {
        char ch = charReader.peek();
        StringBuilder sb = new StringBuilder();
        if (ch == '-') {    // 处理负数
            sb.append(ch);
            ch = charReader.next();
            if (ch == '0') {    // 处理 -0
                sb.append(ch);
                sb.append(readFracAndExp());
            } else if (isDigitOne2Nine(ch)) {
                do {
                    sb.append(ch);
                    ch = charReader.next();
                } while (isDigit(ch));
                if (ch != (char) -1) {
                    charReader.back();
                    sb.append(readFracAndExp());
                }
            } else {
                throw new JingException("Invalid minus number");
            }
        } else if (ch == '0') {    // 处理小数
            sb.append(ch);
            sb.append(readFracAndExp());
        } else {
            do {
                sb.append(ch);
                ch = charReader.next();
            } while (isDigit(ch));
            if (ch != (char) -1) {
                charReader.back();
                sb.append(readFracAndExp());
            }
        }

        return new Token(TokenType.NUMBER, sb.toString());
    }

    private boolean isExp(char ch) {
        return ch == 'e' || ch == 'E';
    }

    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private boolean isDigitOne2Nine(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private String readFracAndExp() throws JingException {
        StringBuilder sb = new StringBuilder();
        char ch = charReader.next();
        if (ch ==  '.') {
            sb.append(ch);
            ch = charReader.next();
            if (!isDigit(ch)) {
                throw new JingException("Invalid frac");
            }
            do {
                sb.append(ch);
                ch = charReader.next();
            } while (isDigit(ch));

            if (isExp(ch)) {    // 处理科学计数法
                sb.append(ch);
                sb.append(readExp());
            } else {
                if (ch != (char) -1) {
                    charReader.back();
                }
            }
        } else if (isExp(ch)) {
            sb.append(ch);
            sb.append(readExp());
        } else {
            charReader.back();
        }

        return sb.toString();
    }

    private String readExp() throws JingException {
        StringBuilder sb = new StringBuilder();
        char ch = charReader.next();
        if (ch == '+' || ch =='-') {
            sb.append(ch);
            ch = charReader.next();
            if (isDigit(ch)) {
                do {
                    sb.append(ch);
                    ch = charReader.next();
                } while (isDigit(ch));

                if (ch != (char) -1) {    // 读取结束，不用回退
                    charReader.back();
                }
            } else {
                throw new JingException("e or E");
            }
        } else {
            throw new JingException("e or E");
        }

        return sb.toString();
    }

    private Token readBoolean() throws JingException {
        if (charReader.peek() == 't') {
            if (!(charReader.next() == 'r' && charReader.next() == 'u' && charReader.next() == 'e')) {
                throw new JingException("Invalid json string");
            }

            return new Token(TokenType.BOOLEAN, "true");
        } else {
            if (!(charReader.next() == 'a' && charReader.next() == 'l'
                && charReader.next() == 's' && charReader.next() == 'e')) {
                throw new JingException("Invalid json string");
            }

            return new Token(TokenType.BOOLEAN, "false");
        }
    }

    private Token readNull() throws JingException {
        if (!(charReader.next() == 'u' && charReader.next() == 'l' && charReader.next() == 'l')) {
            throw new JingException("Invalid json string");
        }

        return new Token(TokenType.NULL, "null");
    }
}
