package Modle;

public class TidePoint {
    private String Date;
    private int Heigth;

    public TidePoint(String Date, int heigth){
        this.Heigth= heigth;
        this.Date =Date;
    }
    public String getTime() {
        return Date;
    }

    public void setTime(int time) {
        Date = Date;
    }

    public int getHeigth() {
        return Heigth;
    }

    public void setHeigth(int heigth) {
        Heigth = heigth;
    }

    public String toString(){
        return "[ "+this.getTime()+":"+this.getHeigth()+" ]";
    }
}
