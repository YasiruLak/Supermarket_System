package view.tm;

import java.sql.Date;

public class IncomeReportTm {
    private Date date;
    private int numberOfOrders;
    private int numberOfSoldItem;
    private double finalCost;
    private String monthOrYear;

    public IncomeReportTm(int numberOfOrders, int numberOfSoldItem, double finalCost, String monthOrYear) {
        this.numberOfOrders = numberOfOrders;
        this.numberOfSoldItem = numberOfSoldItem;
        this.finalCost = finalCost;
        this.setMonthOrYear(monthOrYear);
    }

    public IncomeReportTm(Date date, int numberOfOrders, int numberOfSoldItem, double finalCost) {
        this.date = date;
        this.numberOfOrders = numberOfOrders;
        this.numberOfSoldItem = numberOfSoldItem;
        this.finalCost = finalCost;
    }

    public IncomeReportTm() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public int getNumberOfSoldItem() {
        return numberOfSoldItem;
    }

    public void setNumberOfSoldItem(int numberOfSoldItem) {
        this.numberOfSoldItem = numberOfSoldItem;
    }

    public double getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(double finalCost) {
        this.finalCost = finalCost;
    }

    public String getMonthOrYear() {
        return monthOrYear;
    }

    public void setMonthOrYear(String monthOrYear) {
        this.monthOrYear = monthOrYear;
    }

    @Override
    public String toString() {
        return "IncomeReportsTm{" +
                "date=" + date +
                ", numberOfOrders=" + numberOfOrders +
                ", numberOfSoldItem=" + numberOfSoldItem +
                ", finalCost=" + finalCost +
                ", monthOrYear='" + monthOrYear + '\'' +
                '}';
    }
}
