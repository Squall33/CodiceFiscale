package codicefiscaleyuhu;

import java.util.Arrays;
import java.util.Scanner;
import java.io.File; // Se si usa un file

public class CFGeneratore {

    // private String nome, cognome, comune, m, sesso;
    private String nome, cognome, comune, sesso;
    private int anno, giorno, m;

    private char[] elencoPari = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B',
        'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private int[] elencoDispari = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13,
        15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16,
        10, 22, 25, 24, 23
    };

    /* private String[][] mese = { {"Gennaio","A"}, // per l'acquisizione del mese come stringa
                                    {"Febbraio","B"},
                                    {"Marzo","C"},
                                    {"Aprile","D"},
                                    {"Maggio","E"},
                                    {"Giugno","H"},
                                    {"Luglio","L"},
                                    {"Agosto","M"},
                                    {"Settembre","P"},
                                    {"Ottobre","R"},
                                    {"Novembre","S"},
                                    {"Dicembre","T"}
                                  }; */
    private String letteramese = "ABCDEHLMPRST";

    // --------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Inizializza le variabili di istanza della classe
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------
    public CFGeneratore(String nome, String cognome, String comune, int giorno, int m, int anno, String sesso) {
        // public CFGeneratore(String nome, String cognome, String comune, int giorno, int mese, int a,String sesso)
        this.nome = nome;
        this.cognome = cognome;
        this.comune = comune;
        this.m = m;
        this.anno = anno;
        this.giorno = giorno;
        this.sesso = sesso;

    } // Fine costruttore
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Metodi getter per ottenere gli elementi della classe
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------
    String getNome() {
        return modificaNC(nome, true);
    }

    String getCognome() {
        return modificaNC(cognome, false);
    }

    String getNomeInserito() {
        return nome;
    }

    String getCognomeInserito() {
        return cognome;
    }

    char getMese() {
        return modificaMese();
    }

    int getMeseInserito() {
        return m;
    }

    int getAnno() {
        return (anno % 100);
    }

    int getAnnoInserito() {
        return anno;
    }

    int getGiorno() {
        return (sesso.equals("M")) ? giorno : (giorno + 40);
    }

    int getGiornoInserito() {
        return giorno;
    }

    String getComune() {
        return elaboraCodiceComune();
    }

    String getCodice() {
        return calcolaCodice();
    }

    String getCodiceFiscale() {
        return toString();
    }
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------

    private String modificaNC(String stringa, boolean cod) { // se cod = true l'utente ha inserito un cognome, altrimenti nome
        String nuovastringa = "";
        stringa = stringa.replaceAll(" ", "");  // Rimuovo eventuali spazi         
        stringa = stringa.replaceAll("[1234567890]",""); // Rimuovo eventuali numeri inseriti per sbaglio o caratteri speciali
        stringa = stringa.toLowerCase(); // Tutti i caratteri sono minuscoli

        String consonanti = getConsonanti(stringa);      // Ottengo tutte le consonanti e tutte le vocali della stringa
        String vocali = getVocali(stringa);

        // Controlla i possibili casi
        if (consonanti.length() == 3) {                   // La stringa contiene solo 3 consonanti, quindi ho la modifica
            nuovastringa = consonanti;
        } // Le consonanti non sono sufficienti, e la stringa e' più lunga o
        // uguale a 3 caratteri [aggiungo le vocali mancanti]
        else if ((consonanti.length() < 3) && (stringa.length() >= 3)) {
            nuovastringa = consonanti;
            nuovastringa = aggiungiVocali(nuovastringa, vocali);
        } // Le consonanti non sono sufficienti, e la stringa 
        //contiene meno di 3 caratteri [aggiungo consonanti e vocali, e le x]
        else if ((consonanti.length() < 3) && (stringa.length() < 3)) {
            nuovastringa = consonanti;
            nuovastringa += vocali;
            nuovastringa = aggiungiX(nuovastringa);
        } // Le consonanti sono in eccesso, prendo solo le 
        //prime 3 nel caso del cognome; nel caso del nome la 0, 2, 3.
        else if (consonanti.length() > 3) {               // Controllo per il nome
            // true indica il nome e false il cognome
            if (!cod) {
                nuovastringa = consonanti.substring(0, 3);
            } else {
                nuovastringa = consonanti.charAt(0) + "" + consonanti.charAt(2) + "" + consonanti.charAt(3);
            }
        }

        return nuovastringa;
    }
    // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Aggiunge le X fino a raggiungere una lunghezza complessiva di 3 caratteri
    // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private String aggiungiX(String stringa) {
        while (stringa.length() < 3) {
            stringa += "x";
        }
        return stringa;
    }
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Aggiunge le vocali alla stringa passata per parametro
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private String aggiungiVocali(String stringa, String vocali) {
        int index = 0;
        while (stringa.length() < 3) {
            stringa += vocali.charAt(index);
            index++;
        }
        return stringa;
    }
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Toglie dalla stringa tutte le consonanti
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private String getVocali(String stringa) { // L'operatore ^ di negazione impone l'acquisizione di tutto ciò che non è aeiou.
        stringa = stringa.replaceAll("[^aeiou]", ""); // stringa = stringa.replaceAll("[B-DF-HJ-NP-TV-Z]", ""); da provare
        // stringa = stringa.replaceAll("[!aeiou]", ""); da provare
        return stringa;
    }
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Toglie dalla stringa tutte le vocali
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private String getConsonanti(String stringa) {
        stringa = stringa.replaceAll("[aeiou]", "");
        return stringa;
    }
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Restituisce il codice del mese
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private char modificaMese() {
        return (letteramese.charAt(m - 1));

    }

    /* private String modificaMese() {
    for(int i=0; i<mese.length; i++) {
      if(mese[i][0].equalsIgnoreCase(m)) return mese[i][1];
    }
    return null;
  } */
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Elabora codice del comune
    // ------ --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private String elaboraCodiceComune() {            // Si potrebbe utilizzare un array o uno switch avendo pochi comuni/codici.
        String cc = "";
        try {
            Scanner scanner = new Scanner(new File("C:\\Users\\admin\\Desktop\\EnricoJavaE\\Codicefiscale\\Comuni.txt"));
            int j;

            while (scanner.hasNext()) {
                String s1 = scanner.nextLine();
                String s2 = s1.substring(0, j = s1.indexOf('-'));
                if (s2.equalsIgnoreCase(comune)) {
                    cc = s1.substring(j + 1, s1.indexOf(';'));
                }

            }

            scanner.close();
        } catch (Exception e) {
            System.out.println("ERRORE LETTURA");
        }
        return cc;
    }
    // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Calcolo del Codice di Controllo
    // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private String calcolaCodice() {
        String str = getCognome().toUpperCase() + getNome().toUpperCase() + getAnno() + getMese() + getGiorno() + getComune();
        // String str = getCognome().toUpperCase()+getNome().toUpperCase()+getAnno()+getMese()+getGiorno()+"C741";
        int pari = 0, dispari = 0;

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);              // i-esimo carattere della stringa

            // Il primo carattere e' il numero 1 non 0
            if ((i + 1) % 2 == 0) {
                int index = Arrays.binarySearch(elencoPari, ch); // ricerco il carattere nell'array statico elencoPari dichiarato sopra.
                pari += ((index >= 10) ? index - 10 : index);   // Nella tabella di conversione i numeri da 0 a 9 come le lettere da A a J hanno un numero come valore 					                    // corrispondente
            } else {
                int index = Arrays.binarySearch(elencoPari, ch); // l'indice del carattere trovato in elencoPari corrisponde all'indice ->
                dispari += elencoDispari[index];                // -> del codice cercato in elencoDispari
            }
        }

        int controllo = (pari + dispari) % 26;
        controllo += 10;  // Utilizzo la tabella elencoPari escludendo i numeri

        return elencoPari[controllo] + "";
    }
    // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Viene richiamato per una stampa
    public String stampa() {
        return getCognome().toUpperCase() + getNome().toUpperCase() + getAnno() + getMese() + getGiorno() + getComune() + getCodice();
        // return getCognome().toUpperCase()+getNome().toUpperCase()+getAnno()+getMese()+getGiorno()+"C741"+getCodice();
    }

}
