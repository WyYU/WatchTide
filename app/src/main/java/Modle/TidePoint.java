package Modle;

public class TidePoint {
    private long Date;
    private int Heigth;

    public TidePoint(long Date, int heigth){
        this.Heigth= heigth;
        this.Date =Date;
    }
    public long getTime() {
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
