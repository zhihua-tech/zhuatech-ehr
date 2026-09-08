/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.service;
import jakarta.validation.constraints.*;import org.springframework.stereotype.Service;import java.math.BigDecimal;import java.time.LocalDate;import java.util.*;
@Service
public class CompensationChangeGovernanceService{
 public Assessment assess(Request r){
  List<String> blockers=new ArrayList<>();List<String> actions=new ArrayList<>();
  if(!r.employmentRecordActive())blockers.add("员工任职记录无效");
  if(!r.gradeBandValidated())blockers.add("调整后薪酬未通过职级带宽校验");
  if(!r.budgetAvailable())blockers.add("部门人力预算不足");
  if(r.contractChangeRequired()&&!r.contractChangeSigned())blockers.add("涉及合同变更但员工尚未签署");
  if(!r.managerApproved()||!r.hrApproved()||!r.payrollApproved())blockers.add("经理、人力或薪资审批链不完整");
  if(r.requesterId().equals(r.payrollApproverId()))blockers.add("申请人与薪资审批人必须职责分离");
  if(!r.taxAndSocialInsuranceAssessed())blockers.add("个税与社保影响尚未评估");
  if(!r.sensitiveAccessRestricted())blockers.add("薪酬敏感数据未实施最小权限访问");
  if(!r.idempotencyKeyRegistered())blockers.add("变更缺少幂等键，存在重复调薪风险");
  if(r.effectiveDate().isBefore(LocalDate.now())&&!r.retroAdjustmentCalculated())blockers.add("追溯调薪未计算补发或冲销金额");
  if(!r.employeeNotificationScheduled())actions.add("安排生效前的员工通知");
  if(!r.auditEvidenceAttached())actions.add("归档预算、带宽、审批和合同证据");
  BigDecimal increase=r.newAnnualSalary().subtract(r.currentAnnualSalary());
  BigDecimal rate=r.currentAnnualSalary().signum()==0?BigDecimal.ONE:increase.divide(r.currentAnnualSalary(),4,java.math.RoundingMode.HALF_UP);
  RiskLevel risk=rate.compareTo(new BigDecimal("0.20"))>0||r.effectiveDate().isBefore(LocalDate.now())?RiskLevel.HIGH:RiskLevel.NORMAL;
  Decision decision=!blockers.isEmpty()?Decision.BLOCKED:!actions.isEmpty()?Decision.REVIEW:Decision.APPLY;
  String route=risk==RiskLevel.HIGH?"直属经理→HRBP→薪酬负责人→财务":"直属经理→HRBP→薪资专员";
  return new Assessment(r.changeNo(),decision,risk,route,rate,List.copyOf(blockers),List.copyOf(actions));
 }
 public record Request(@NotBlank String changeNo,@NotBlank String requesterId,@NotBlank String payrollApproverId,
  @NotNull @DecimalMin("0.00")BigDecimal currentAnnualSalary,@NotNull @DecimalMin("0.00")BigDecimal newAnnualSalary,
  @NotNull LocalDate effectiveDate,boolean employmentRecordActive,boolean gradeBandValidated,boolean budgetAvailable,
  boolean contractChangeRequired,boolean contractChangeSigned,boolean managerApproved,boolean hrApproved,
  boolean payrollApproved,boolean taxAndSocialInsuranceAssessed,boolean sensitiveAccessRestricted,
  boolean idempotencyKeyRegistered,boolean retroAdjustmentCalculated,boolean employeeNotificationScheduled,
  boolean auditEvidenceAttached){}
 public record Assessment(String changeNo,Decision decision,RiskLevel riskLevel,String approvalRoute,BigDecimal changeRate,List<String> blockers,List<String> actions){}
 public enum Decision{APPLY,REVIEW,BLOCKED}public enum RiskLevel{NORMAL,HIGH}
}
