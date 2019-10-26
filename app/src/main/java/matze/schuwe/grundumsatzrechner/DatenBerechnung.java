package matze.schuwe.grundumsatzrechner;

import android.widget.TextView;
import android.widget.Toast;

public class DatenBerechnung {
    private boolean maennlich;
    private  int groesse, alter;
    private double gewicht, grundUmsatz, kalorienVerbrauch, palSchlaf, palSitzend, palStehend, palKaumAktiv, palSport, palDurchschnitt;
    private final double palFaktorSchlaf= 0.95;
    private final double palFaktorKaumAktiv= 1.45;
    private final double palFaktorSitzend= 1.2;
    private final double palFaktorStehend= 1.85;
    private final double palFaktorSport= 2.2;

    public boolean getMaennlich(){
        return maennlich;
    }
    public int getGroesse(){
        return groesse;
    }
    public int getAlter(){
        return alter;
    }
    public double getGewicht(){
        return gewicht;
    }
    public double getGrundUmsatz(){
        return grundUmsatz;
    }
    public double getKalorienVerbrauch(){
        return kalorienVerbrauch;
    }
    public double getPalSchlaf() {
        return palSchlaf;
    }
    public double getPalSitzend(){
        return palSitzend;
    }
    public double getPalStehend(){
        return palStehend;
    }
    public double getPalKaumAktiv(){
        return palKaumAktiv;
    }
    public double getPalSport(){
        return palSport;
    }
    public void setGroesse(int gr) throws WertebereichException{
        if(gr<100 || gr>300){
            throw new WertebereichException();
        }
      groesse = gr;
    }
    public void setAlter(int alt) throws WertebereichException{
        if(alt<15 || alt>130){
            throw new WertebereichException();
        }
        alter = alt;
    }
    public void setGewicht(double gew) throws WertebereichException{
        if(gew<30 || gew>300){
            throw new WertebereichException();
        }
        gewicht =gew;
    }
    public void setMaennlich(boolean maen){
        maennlich = maen;
    }
    public void berechneGrundumsatz(){
        if(maennlich){
            grundUmsatz= 66.47+((13.7*gewicht)+(5*groesse))-(6.8*alter);
        }else{
            grundUmsatz= 655+(9.6*gewicht)+(1.8*groesse)-(4.7*alter);
        }
    }
    public void setPalWerte(double schlaf, double sitzend, double kaumAktiv, double stehend, double sport) throws WertebereichException{
        if(schlaf + sitzend+ stehend + sport + kaumAktiv!= 24)
            throw new WertebereichException();
        palSchlaf = schlaf;
        palSitzend = sitzend;
        palStehend = stehend;
        palSport = sport;
        palKaumAktiv=kaumAktiv;
    }
    public void berechneKalorienverbrauch() {
        palDurchschnitt = (palSchlaf*palFaktorSchlaf+palSitzend*palFaktorSitzend+ palKaumAktiv*palFaktorKaumAktiv+palStehend*palFaktorStehend+palSport*palFaktorSport)/24;
        kalorienVerbrauch= palDurchschnitt*grundUmsatz;

    }
}
