package ru.home.miniplanner.domain;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by privod on 19.10.2015.
 */
public class Party extends Domain {
    private Long id;
    private String name;
    private BigDecimal deposit;
    private List<Bay> bays;
    private List<Contribution> contributions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public List<Bay> getBays() {
        return bays;
    }

    public void setBays(List<Bay> bays) {
        this.bays = bays;
    }

    public List<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(List<Contribution> contributions) {
        this.contributions = contributions;
    }
}
