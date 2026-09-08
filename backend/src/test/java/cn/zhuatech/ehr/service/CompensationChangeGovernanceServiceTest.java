/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.service;
import org.junit.jupiter.api.Test;import java.math.BigDecimal;import java.time.LocalDate;import static org.assertj.core.api.Assertions.assertThat;
class CompensationChangeGovernanceServiceTest{
 private final CompensationChangeGovernanceService service=new CompensationChangeGovernanceService();
 private CompensationChangeGovernanceService.Request request(boolean notify,boolean evidence){return new CompensationChangeGovernanceService.Request("SAL-1","hr","payroll",new BigDecimal("200000"),new BigDecimal("220000"),LocalDate.now().plusDays(30),true,true,true,false,true,true,true,true,true,true,true,true,notify,evidence);}
 @Test void appliesControlledCompensationChange(){var a=service.assess(request(true,true));assertThat(a.decision()).isEqualTo(CompensationChangeGovernanceService.Decision.APPLY);assertThat(a.changeRate()).isEqualByComparingTo("0.1000");}
 @Test void reviewsCommunicationAndEvidence(){var a=service.assess(request(false,false));assertThat(a.decision()).isEqualTo(CompensationChangeGovernanceService.Decision.REVIEW);assertThat(a.actions()).hasSize(2);}
 @Test void blocksUnapprovedRetroactiveIncrease(){var r=new CompensationChangeGovernanceService.Request("SAL-2","same","same",new BigDecimal("100000"),new BigDecimal("150000"),LocalDate.now().minusDays(10),true,false,false,true,false,false,false,false,false,false,false,false,false,false);var a=service.assess(r);assertThat(a.decision()).isEqualTo(CompensationChangeGovernanceService.Decision.BLOCKED);assertThat(a.riskLevel()).isEqualTo(CompensationChangeGovernanceService.RiskLevel.HIGH);assertThat(a.blockers()).hasSizeGreaterThanOrEqualTo(8);}
}
