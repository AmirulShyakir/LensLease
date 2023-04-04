/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author Amirul
 */
@Entity
public class PortfolioSkill implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portfolioSkillId;
    private String skillName;
    
    @ManyToOne
    private Portfolio portfolio;

    public PortfolioSkill() {
    }

    public PortfolioSkill(String skillName) {
        this.skillName = skillName;
    }
    
    /**
     * @return the skillName
     */
    public String getSkillName() {
        return skillName;
    }

    /**
     * @param skillName the skillName to set
     */
    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
    
    public Long getPortfolioSkillId() {
        return portfolioSkillId;
    }

    public void setPortfolioSkillId(Long portfolioSkillId) {
        this.portfolioSkillId = portfolioSkillId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getPortfolioSkillId() != null ? getPortfolioSkillId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the portfolioSkillId fields are not set
        if (!(object instanceof PortfolioSkill)) {
            return false;
        }
        PortfolioSkill other = (PortfolioSkill) object;
        if ((this.getPortfolioSkillId() == null && other.getPortfolioSkillId() != null) || (this.getPortfolioSkillId() != null && !this.portfolioSkillId.equals(other.portfolioSkillId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PortfolioSkill[ id=" + getPortfolioSkillId() + " ]";
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

}
