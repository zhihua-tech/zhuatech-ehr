/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
import http from './http'
export const api = {
  login: data => http.post('/auth/login', data), me: () => http.get('/auth/me'), dashboard: () => http.get('/dashboard'),
  employees: () => http.get('/employees'), employee: id => http.get(`/employees/${id}`), createEmployee: data => http.post('/employees', data), setEmployeeStatus: (id,status) => http.patch(`/employees/${id}/status`, { status }), departments: () => http.get('/organization/departments'),
  attendanceToday: () => http.get('/attendance/today'), attendanceHistory: () => http.get('/attendance'), checkIn: () => http.post('/attendance/check-in'), checkOut: () => http.post('/attendance/check-out'),
  leaves: () => http.get('/leaves'), pendingLeaves: () => http.get('/leaves/pending'), createLeave: data => http.post('/leaves', data), approveLeave: (id,data) => http.post(`/leaves/${id}/approve`, data),
  payrollMine: () => http.get('/payroll/mine'), payroll: () => http.get('/payroll'), createPayroll: data => http.post('/payroll', data), setPayrollStatus: (id,status) => http.patch(`/payroll/${id}/status`, { status }),
  jobs: () => http.get('/recruitment/jobs'), createJob: data => http.post('/recruitment/jobs', data), closeJob: id => http.post(`/recruitment/jobs/${id}/close`),
  candidates: () => http.get('/recruitment/candidates'), createCandidate: data => http.post('/recruitment/candidates', data), setCandidateStage: (id,data) => http.patch(`/recruitment/candidates/${id}/stage`, data)
}
