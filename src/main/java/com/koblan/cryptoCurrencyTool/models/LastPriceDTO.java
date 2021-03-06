package main.java.com.koblan.cryptoCurrencyTool.models;

public class LastPriceDTO {

    private float lprice;
    private String curr1;
    private String curr2;

    LastPriceDTO(){}

    public LastPriceDTO(float lprice, String curr1, String curr2) {
        this.lprice = lprice;
        this.curr1 = curr1;
        this.curr2 = curr2;
    }

    public float getLprice() {
        return lprice;
    }

    public void setLprice(float lprice) {
        this.lprice = lprice;
    }

    public String getCurr1() {
        return curr1;
    }

    public void setCurr1(String curr1) {
        this.curr1 = curr1;
    }

    public String getCurr2() {
        return curr2;
    }

    public void setCurr2(String curr2) {
        this.curr2 = curr2;
    }

    @Override
    public String toString() {
        return "LastPriceDTO{" +
                "lprice=" + lprice +
                ", curr1='" + curr1 + '\'' +
                ", curr2='" + curr2 + '\'' +
                '}';
    }
}
