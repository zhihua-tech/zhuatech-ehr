/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.service;

import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeExitClearanceGovernanceService {
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!request.lastWorkingDayConfirmed()) blockers.add("最后工作日尚未确认");
        if (!request.accessRevoked()) blockers.add("账号与系统访问权限尚未回收");
        if (request.privilegedAccount() && !request.privilegedAccessRevoked()) blockers.add("特权账号权限尚未回收");
        if (!request.assetsReturned()) blockers.add("企业资产尚未全部归还");
        if (!request.payrollSettled()) actions.add("完成薪资、补偿和个税结算");
        if (!request.expensesSettled()) actions.add("完成借款与报销清算");
        if (!request.knowledgeTransferred()) actions.add("完成岗位知识与在办事项交接");
        if (!request.managerApproved() || !request.hrApproved()) actions.add("取得业务负责人和人力资源双重确认");

        Decision decision = !blockers.isEmpty() ? Decision.BLOCK
                : !actions.isEmpty() ? Decision.REVIEW : Decision.CLOSE;
        return new Assessment(request.employeeNo(), decision, List.copyOf(blockers), List.copyOf(actions));
    }

    public record Request(@NotBlank String employeeNo, boolean lastWorkingDayConfirmed,
                          boolean accessRevoked, boolean privilegedAccount,
                          boolean privilegedAccessRevoked, boolean assetsReturned,
                          boolean payrollSettled, boolean expensesSettled,
                          boolean knowledgeTransferred, boolean managerApproved,
                          boolean hrApproved) {}
    public record Assessment(String employeeNo, Decision decision, List<String> blockers,
                             List<String> actions) {}
    public enum Decision { CLOSE, REVIEW, BLOCK }
}
