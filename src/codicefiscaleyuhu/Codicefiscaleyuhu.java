
package codicefiscaleyuhu;

public class Codicefiscaleyuhu {

    public static void main(String[] args) {
        String nome, cognome, comune,sesso;
        
        String codicefiscale;
        int anno, giorno; 
        
        // Si può usare un metodo per l'acquisizione dato per dato con l'oggetto scanner;
        // 1) Acquisizione di nome,cognome,città e sesso tramite nextLine().
        // 2 split della stringa.
        // 3) for:each dello split e salvataggio del nome,cognome,città e sesso in stringhe differenti.
        // 4) Ripeti da 1 a 4 utilizzando una stringa per la data
        // 5a) Conrollo sulla stringa data con replaceAll per eliminare eventuali \ o caratteri doppi
        // 5b) Substring per estrarre giorno,mese e anno singolarmente da ritornare e passare a GeneratoreCodiceFiscale.
        
        // Inserire come NOME,COGNOME,CITTA DI NASCITA,GIORNO,MESE,ANNO,SESSO
        GeneratoreCodiceFiscale acquisizione = new GeneratoreCodiceFiscale("ENRICO","argentieri","cisternino",23,02,1990,"M");
        codicefiscale =acquisizione.stampa();
        System.out.println(codicefiscale);

        
 
        
    }
    
}
