package com.example.alen.glumcifinalverzion.model;

import android.opengl.GLU;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Alen on 15-Mar-17.
 */

@DatabaseTable(tableName = Glumci.TABLE_NAME_USERS)
public class Glumci {

    public static final String TABLE_NAME_USERS  = "glumci";
    public static final String FIELD_NAME_ID     = "id";
    public static final String FIELD_NAME_NAME   = "ime i prezime";
    public static final String FIELD_NAME_BIO    = "biografija";
    public static final String FIELD_NAME_MARK   = "ocena";
    public static final String FIELD_NAME_DATE   = "datum rodjenja";
    public static final String FIELD_NAME_MOVIE  = "filmovi";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int idGlumci;

    @DatabaseField(columnName = FIELD_NAME_NAME)
    private String imeGlumci;

    @DatabaseField(columnName = FIELD_NAME_BIO)
    private String bioGlumcmi;

    @DatabaseField(columnName = FIELD_NAME_MARK)
    private String ocenaGlumci;

    @DatabaseField(columnName = FIELD_NAME_DATE)
    private String datum;

    @ForeignCollectionField(columnName = Glumci.FIELD_NAME_MOVIE, eager = true)
    private ForeignCollection<Filmovi> filmovi;

    public Glumci() {

    }

    public int getIdGlumci() {
        return idGlumci;
    }

    public void setIdGlumci(int idGlumci) {
        this.idGlumci = idGlumci;
    }

    public String getImeGlumci() {
        return imeGlumci;
    }

    public void setImeGlumci(String imeGlumci) {
        this.imeGlumci = imeGlumci;
    }

    public String getBioGlumcmi() {
        return bioGlumcmi;
    }

    public void setBioGlumcmi(String bioGlumcmi) {
        this.bioGlumcmi = bioGlumcmi;
    }

    public String getOcenaGlumci() {
        return ocenaGlumci;
    }

    public void setOcenaGlumci(String ocenaGlumci) {
        this.ocenaGlumci = ocenaGlumci;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public ForeignCollection<Filmovi> getFilmovi() {
        return filmovi;
    }

    public void setFilmovi(ForeignCollection<Filmovi> filmovi) {
        this.filmovi = filmovi;
    }

    public String toString() {
        return imeGlumci;
    }
}
