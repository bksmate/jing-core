package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-06-29 <br>
 */
public class Decode4Html {
    public Decode4Html() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        StringBuilder stbr = new StringBuilder();
        while (null != (input = bufferedReader.readLine())) {
            stbr.append(input);
            if (input.equals("</jing>")) {
                break;
            }
            else {
                stbr.append("\n");
            }
        }
        String html = stbr.toString();
        html = html.replaceAll("<", "&lt;");
        html = html.replaceAll(">", "&gt;");
        html = html.replaceAll("\t", "    ");
        System.out.println(html);
    }


    public static void main(String[] args) throws IOException {
        new Decode4Html();
    }
}
