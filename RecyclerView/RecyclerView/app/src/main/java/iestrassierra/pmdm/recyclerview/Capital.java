package iestrassierra.pmdm.recyclerview;

public class Capital {
    private String capital;
    private String habitantes;
    private Boolean favorita;
    public Capital(String cap, String hab){
        capital = cap;
        habitantes = hab;
        favorita = false;
    }
    public Capital(String cap, String hab,Boolean fav){
        capital = cap;
        habitantes = hab;
        favorita = fav;
    }
    public String getCapital(){
        return capital;
    }
    public String getHabitantes(){
        return habitantes;
    }
    public Boolean isFavorita(){
        return favorita;
    }
}
