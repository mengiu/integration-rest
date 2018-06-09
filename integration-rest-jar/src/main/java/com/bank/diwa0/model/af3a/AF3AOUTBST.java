bank com.intesasanpaolo.diwa0.model.af3a;

import com.intesasanpaolo.diwa0.annotation.Azienda;
import com.intesasanpaolo.diwa0.annotation.Ndg;
import com.intesasanpaolo.diwa0.annotation.Trascodifica;

@Trascodifica
public class AF3AOUTBST {

    private String gruppo;

    @Azienda
    private String azienda;

    private String ruolo;

    private String lingua;

    @Ndg
    private String ndg;

    private String cdn;

    private AF3AOUTLEI outLei;

    public AF3AOUTLEI getOutLei() {
        return outLei;
    }

    public void setOutLei(AF3AOUTLEI outLei) {
        this.outLei = outLei;
    }

    public String getGruppo() {
        return gruppo;
    }

    public void setGruppo(String gruppo) {
        this.gruppo = gruppo;
    }

    public String getAzienda() {
        return azienda;
    }

    public void setAzienda(String azienda) {
        this.azienda = azienda;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    public String getNdg() {
        return ndg;
    }

    public void setNdg(String ndg) {
        this.ndg = ndg;
    }

    public String getCdn() {
        return cdn;
    }

    public void setCdn(String cdn) {
        this.cdn = cdn;
    }
}
