package codicefiscaleyuhu;

import java.util.Arrays;
import java.util.Scanner;
import java.io.File; // Per l'acquisizione da file

public class GeneratoreCodiceFiscale {

    private String nome, cognome, comune, sesso;
    private int anno, giorno, mese;

    private char[] numeripari = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B',
        'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private int[] numeridispari = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13,
        15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16,
        10, 22, 25, 24, 23
    };

    private String letteramese = "ABCDEHLMPRST";

    public GeneratoreCodiceFiscale(String nome, String cognome, String comune, int giorno, int meseacquisito, int anno, String sesso) {
        // public GeneratoreCodiceFiscale(String nome, String cognome, String comune, int giorno, int mese, int a,String sesso)
        this.nome = nome;
        this.cognome = cognome;
        this.comune = comune;
        this.mese = meseacquisito;
        this.anno = anno;
        this.giorno = giorno;
        this.sesso = sesso;

    }

    String getNome() {
        return elaboraNomeCognome(nome, true);
    }

    String getCognome() {
        return elaboraNomeCognome(cognome, false);
    }

    String getNomeInserito() {
        return nome;
    }

    String getCognomeInserito() {
        return cognome;
    }

    char getMese() {
        return calcolaMese();
    }

    int getMeseInserito() {
        return mese;
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
        return estraiCodiceComune();
    }

    String getCodice() {
        return codiceControllo();
    }

    String getCodiceFiscale() {
        return toString();
    }

    private String elaboraNomeCognome(String stringa, boolean nomeocognome) { // se nomeocognome = true l'utente ha inserito un cognome, altrimenti nome
        String nomecognome = "";
        stringa = stringa.replaceAll(" ", "");  // Rimuovo eventuali spazi         
        stringa = stringa.replaceAll("[1234567890]", ""); // Rimuovo eventuali numeri inseriti per sbaglio
        stringa = stringa.toLowerCase(); // Tutti i caratteri sono minuscoli

        String consonanti = getConsonanti(stringa);      // Ottengo tutte le consonanti e tutte le vocali della stringa
        String vocali = getVocali(stringa);

        // Controlla i possibili casi
        if (consonanti.length() == 3) {                   // La stringa contiene solo 3 consonanti, quindi ho la modifica
            nomecognome = consonanti;
        } else if ((consonanti.length() < 3) && (stringa.length() >= 3)) {    // Le consonanti non sono sufficienti, e la stringa e' più lunga o
            // uguale a 3 caratteri [aggiungo le vocali mancanti]
            nomecognome = consonanti;
            nomecognome = inserisciVocali(nomecognome, vocali);
        } else if ((consonanti.length() < 3) && (stringa.length() < 3)) {      // Le consonanti non sono sufficienti, e la stringa 
            //contiene meno di 3 caratteri [aggiungo consonanti e vocali, e le x]
            nomecognome = consonanti;
            nomecognome += vocali;
            nomecognome = inserisciXcognome(nomecognome);
        } // Controllo per il nome
        // Le consonanti sono in eccesso, prendo solo le 
        //prime 3 nel caso del cognome; nel caso del nome la 0, 2, 3.
        else if (consonanti.length() > 3) {

            if (!nomeocognome) {                      // true indica il nome e false il cognome
                nomecognome = consonanti.substring(0, 3);
            } else {
                nomecognome = consonanti.charAt(0) + "" + consonanti.charAt(2) + "" + consonanti.charAt(3);
            }
        }

        return nomecognome;
    }

    // Inserisce le X se le consonanti non sono sufficienti e la stringa è più piccola di 3 caratteri
    private String inserisciXcognome(String stringa) {
        while (stringa.length() < 3) {
            stringa += "x";
        }
        return stringa;
    }

    // Aggiunge le vocali per il caso "Consonanti non sufficienti" nell'acquisizione del Cognome
    private String inserisciVocali(String stringa, String vocali) {
        int index = 0;
        while (stringa.length() < 3) {
            stringa += vocali.charAt(index);
            index++;
        }
        return stringa;
    }

    // Elimina le consonanti dalla stringa
    private String getVocali(String stringa) { // L'operatore ^ di negazione impone l'acquisizione di tutto ciò che non è aeiou.
        // stringa = stringa.replaceAll("[^aeiou]", ""); 
        stringa = stringa.replaceAll("[B-DF-HJ-NP-TV-Z]", "");
        // stringa = stringa.replaceAll("[!aeiou]", "");
        return stringa;
    }

    // Elimina le vocali dalla stringa
    private String getConsonanti(String stringa) {
        stringa = stringa.replaceAll("[aeiou]", "");
        return stringa;
    }

    // Calcolo del codice mese
    private char calcolaMese() {
        return (letteramese.charAt(mese - 1));

    }

    // Elabora il codice del comune
    private String estraiCodiceComune() {            // Si potrebbe utilizzare un array o uno switch avendo pochi comuni/codici.
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

    private String codiceControllo() { // Calcola il Codice di Controllo
        String codicefiscalequasicompleto = getCognome().toUpperCase() + getNome().toUpperCase() + getAnno() + getMese() + getGiorno() + getComune();
        // String codicefiscalequasicompleto = getCognome().toUpperCase()+getNome().toUpperCase()+getAnno()+getMese()+getGiorno()+"C741";
        int pari = 0;
        int dispari = 0;

        for (int i = 0; i < codicefiscalequasicompleto.length(); i++) {
            char ch = codicefiscalequasicompleto.charAt(i);              // i-esimo carattere della stringa

            if ((i + 1) % 2 == 0) {
                int posizione = Arrays.binarySearch(numeripari, ch); // ricerco il carattere nell'array statico numeripari dichiarato sopra.
                if (posizione >= 10) {
                    pari += posizione - 10;
                } else {
                    pari += posizione;
                }
            } else {
                int posizione = Arrays.binarySearch(numeripari, ch); // l'indice del carattere trovato in numeripari corrisponde all'indice ->
                dispari += numeridispari[posizione];                // -> del codice cercato in numeridispari
            }
        }

        int controllo = (pari + dispari) % 26;
        controllo += 10;  // Utilizzo la tabella numeripari escludendo i numeri

        return numeripari[controllo] + "";
    }

    public String stampa() { // Stampa tutto
        return getCognome().toUpperCase() + getNome().toUpperCase() + getAnno() + getMese() + getGiorno() + getComune() + getCodice();
        // return getCognome().toUpperCase()+getNome().toUpperCase()+getAnno()+getMese()+getGiorno()+"C741"+getCodice();
    }

}
