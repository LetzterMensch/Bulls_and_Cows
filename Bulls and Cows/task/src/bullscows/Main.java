package bullscows;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }
    public static void main(String[] args) {
        setTimeout(() -> System.out.println("test"), 15000);
        Integer bulls = 0;
        Integer cows = 0;
        Scanner scanner = new Scanner(System.in);
        String strSymbolNum;
        String strNumLen;
        Integer numLen = null;
        Integer symbolNum = null;
        /*Generate Code*/
        System.out.println("Please, enter the secret code's length:");
        strNumLen = scanner.next();
        try{
            numLen = Integer.parseInt(strNumLen);
        }catch (NumberFormatException e){
            System.out.println("Error: \""+strNumLen+"\""+" isn't a valid number.");
            return;
        }
        if (numLen < 1) {
            System.out.println("Error: minimum number of possible symbols in the code is 1.");
            return;
        }
        if (numLen > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        strSymbolNum = scanner.next();
        try{
            symbolNum = Integer.parseInt(strSymbolNum);
        }catch (NumberFormatException e){
            System.out.println("\""+strSymbolNum+"\""+" isn't a valid number.");
            return;
        }
        if (symbolNum > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }
        if (symbolNum < 1) {
            System.out.println("Error: minimum number of possible symbols in the code is 1.");
            return;
        }
        if(symbolNum < numLen){
            System.out.println("Error: it's not possible to generate a code with a length of "+numLen+" with "+symbolNum+" unique symbols.");
            return;
        }
//        System.out.println("\"" + numLen + "\"" + "isn't a valid number.");
//        System.out.println("\"" + symbolNum + "\"" + "isn't a valid number.");
        String characters = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder strCode = new StringBuilder();
        StringBuilder strPseudoRandomNumber = new StringBuilder("0123456789");
        for (int i = 0; i < symbolNum - 10; i++) {
            strPseudoRandomNumber.append(characters.charAt(i));
        }
        do {
            Random rnd = new Random();
            int index = rnd.nextInt(strPseudoRandomNumber.length());
            if (strPseudoRandomNumber.charAt(index) != '#') {
                strCode.append(strPseudoRandomNumber.charAt(index));
                strPseudoRandomNumber.setCharAt(index, '#');
            }
        }
        while (strCode.length() < numLen);
        System.out.println(strCode.toString());
        System.out.println(strCode.length());
//        System.out.println("The random secret number is " + strCode);
        System.out.print("The secret is prepared: ");
        for (int i = 0; i < strCode.length(); i++) {
            System.out.print("*");
        }
        if (symbolNum == 11) {
            System.out.printf(" (%d-%d, %c).", 0, 0, 'a');
        } else if (symbolNum > 11) {
            System.out.printf(" (%d-%d, %c-%c).", 0, 9, 'a', strPseudoRandomNumber.charAt(strPseudoRandomNumber.length() - 1));
        } else if (symbolNum <= 10) {
            System.out.printf(" (%d-%d).", 0, symbolNum - 1);
        }
        System.out.println("Okay, let's start a game!");
        /*Gameplay*/
        int count = 0;
        HashSet<Integer> bull = new HashSet<>();
        do {
            count++;
            System.out.println("Turn " + count + ":");
            String strInput = scanner.next();
            bull.clear();
            bulls = 0;
            cows = 0;
            System.out.println(strInput);
            for (int i = 0; i < strCode.length(); i++) {
                for (int j = 0; j < strInput.length(); j++) {
                    if (strInput.charAt(j) == strCode.charAt(i) && i == j) {
                        bulls++;
                        bull.add(j);
                        continue;
                    }
                    if (strInput.charAt(j) == strCode.charAt(i) && i != j && !bull.contains(j)) {
                        cows++;
                    }
                }
            }
            String output = "";
            if (bulls == 0 && cows == 0) {
                output = "None";
            } else {
                if (bulls == 1) {
                    output += bulls.toString() + " bull";
                } else if (bulls > 1) {
                    output += bulls.toString() + " bulls";
                }
                if (bulls != 0 && cows == 1) {
                    output += " and " + cows.toString() + " cow";
                } else if (bulls != 0 && cows > 1) {
                    output += " and " + cows.toString() + " cows";
                } else if (bulls == 0 && cows == 1) {
                    output += cows.toString() + " cow";
                } else if (bulls == 0 && cows > 1) {
                    output += cows.toString() + " cows";
                }
            }
            System.out.println("Grade: " + output);
        } while (bulls != numLen);
        System.out.println("Congratulations! You guessed the secret code.");
    }
}
