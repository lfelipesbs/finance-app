package com.example.finance.model;

public class DashboardSummary {
    private double totalReceitas;
    private double totalDespesas;

    public DashboardSummary(double totalReceitas, double totalDespesas) {
        this.totalReceitas = totalReceitas;
        this.totalDespesas = totalDespesas;
    }

    public double getTotalReceitas() {
        return totalReceitas;
    }

    public void setTotalReceitas(double totalReceitas) {
        this.totalReceitas = totalReceitas;
    }

    public double getTotalDespesas() {
        return totalDespesas;
    }

    public void setTotalDespesas(double totalDespesas) {
        this.totalDespesas = totalDespesas;
    }

    public double getSaldo() {
        return totalReceitas - totalDespesas;
    }
}