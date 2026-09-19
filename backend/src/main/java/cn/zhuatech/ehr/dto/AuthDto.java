/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.dto;
import cn.zhuatech.ehr.model.UserAccount;
import jakarta.validation.constraints.NotBlank;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public final class AuthDto {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private AuthDto() {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record LoginRequest(@NotBlank(message="请输入用户名") String username, @NotBlank(message="请输入密码") String password) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record UserView(Long id, String employeeNo, String username, String fullName, String email, String phone, String position, String role, String employmentStatus, Long departmentId, String departmentName) {
        /**
         * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
         */
        public static UserView from(UserAccount u) { return new UserView(u.getId(), u.getEmployeeNo(), u.getUsername(), u.getFullName(), u.getEmail(), u.getPhone(), u.getPosition(), u.getRole().name(), u.getEmploymentStatus().name(), u.getDepartment() == null ? null : u.getDepartment().getId(), u.getDepartment() == null ? null : u.getDepartment().getName()); }
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record LoginResponse(String token, UserView user) {}
}
