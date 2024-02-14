package pl.edu.agh.hangman;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.ArrayList;



public class Main {
    public static void main(String[] args)throws FileNotFoundException{
        Scanner input = new Scanner(System.in);
        int graphic =0;
        int numberOfLetters =2;
        int numberOfTry=6;

        do {
            System.out.println("Grafika 1 czy 2:");
            graphic = input.nextInt();
        }while(graphic!=1&graphic!=2);

        /*System.out.println("Ile prób do zgadniecia? Optymalnie to 6. Wybierz z zakresu 1-12");
        do {
            numberOfTry = input.nextInt();
        }while(numberOfTry<1||numberOfTry>12);
         */

        System.out.println("Podaj długość słowa o dlugosci 2-21 liter:");
        do {
           numberOfLetters = input.nextInt();
        }while(numberOfLetters<2||numberOfLetters>21);
        String WordToPlay = chooseWord(numberOfLetters); //wybiera slowo z listy o okreslonej liczbie znakow

        char[] invisibleWord = new char[WordToPlay.length()]; // slowo do odgadniecia
        invisibleWord = generateInvisibleWord(WordToPlay,invisibleWord);

        int attempt = 0; //licznik prób
        while (attempt < 7) {


            if (graphic==1)System.out.print("\n" + Hangman.HANGMANPICS[attempt].substring(0,31)+"\t\t\t\t"); // grafika
            else if (graphic==2)System.out.print("\n" + Hangman.HANGMANPICS2[attempt].substring(0,31)+"\t\t\t\t"); // grafika
            printWord(invisibleWord);// wykreskowane szukane słowo
            if (graphic==1)System.out.print(Hangman.HANGMANPICS[attempt].substring(31)); //grafika
            if (graphic==2)System.out.print(Hangman.HANGMANPICS2[attempt].substring(31)); //grafika
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t(podpowiedz:" + WordToPlay + ")");
            System.out.print("\n" + "GUESS>>"); //znak zachęty
            if(attempt>=6){
                System.out.println("___________ Gra skończona - - - PRZEGRALES ____________");
                break;
            }
            if(areAllLettersUncovered(invisibleWord)){
                System.out.println(">>>>>     WYGRAŁES - GRATULACJE     <<<<<"); // sprwdzam czy odgadnięte wszystkie litery
                break;
            }
            char charAttempt = input.next().charAt(0); // odczyt znaku

            if (isCharCorrect(WordToPlay, charAttempt)) { //obsługa małej i dużej litery
                    invisibleWord = fillTheInvisibleWord(WordToPlay,invisibleWord,charAttempt);
            }
            else attempt++ ; // zwieksz licznik prób
        }
    }

    static char[] generateInvisibleWord(String WordToPlay, char[] invisibleWord){
        for (int i = 0; i < invisibleWord.length; i++) {
            if(WordToPlay.charAt(i)==' ')invisibleWord[i] = ' '; // SPACJA w slowie
            else invisibleWord[i] = '_'; // INICJALIZACJA PUSTEGO SLOWA
        }
        return invisibleWord;
    }
    static String chooseWord(int numberOfLetters)throws FileNotFoundException {
        ArrayList<String> listOfWords = new ArrayList<>();
        Random random = new Random();
        File file = new File("src/main/resources/slowa.txt");
        Scanner in = new Scanner(file);
        String WordToPlay = "";

        while(in.hasNextLine()) { //dopoki istnieje koeljne slowo
            String line = in.nextLine(); //odczytaj slowo
            if(line.length()==numberOfLetters)listOfWords.add(line); //jesli ma odpowiednia ilosc znakow, dodaj do listy
        }
       // for(String name : listOfWords) { // wyswietla wszystkie odnalezione slowa o zadanej dlugosci znakow
       //     System.out.println(name);
       // }
        WordToPlay = listOfWords.get(random.nextInt(listOfWords.size())); // losuje slowo z listy
        return WordToPlay;
    }

    static void printWord(char[] secretWord) {
        for (int k = 0; k < secretWord.length; k++) {
            System.out.print(secretWord[k]+" ");
        }
    }

    static boolean isCharCorrect(String zdanie, char a) {
        if (zdanie.toLowerCase().indexOf(a) >= 0 | zdanie.toUpperCase().indexOf(a) >= 0) //obsluga malych i duzych liter
            return true;
            return false;
    }
    static char[] fillTheInvisibleWord(String WordToPlay,char[] invisibleWord, char charAttempt){
        for (int i = 0; i < WordToPlay.length(); i++) { //jesli znalazl taka litere w slowie, to wpisz dla kazdej wystepujacej litery
            if (WordToPlay.toUpperCase().charAt(i) == charAttempt | WordToPlay.toLowerCase().charAt(i) == charAttempt)
                invisibleWord[i] = WordToPlay.charAt(i); //dodaje do pustego slowa odnalezione literki w oryginalej wielkosci
        }
        return invisibleWord;
    }
    static boolean areAllLettersUncovered(char[] invisibleWord){ //sprawdza czy wszystkie literki sa odslonięte
        for(int i=0;i<invisibleWord.length;i++) {
            if (invisibleWord[i]=='_') {
                return false;
            }
        }
        return true; // wygrales, brak pustych liter
    }
}


