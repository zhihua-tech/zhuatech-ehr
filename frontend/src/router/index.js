/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import MainLayout from '../components/MainLayout.vue'
const routes=[
 {path:'/login',component:LoginView,meta:{public:true,title:'登录'}},
 {path:'/',component:MainLayout,children:[
  {path:'',component:()=>import('../views/HomeView.vue'),meta:{title:'人事首页'}},
  {path:'workbench',component:()=>import('../views/WorkbenchView.vue'),meta:{title:'人事工作台'}},
  {path:'employees',component:()=>import('../views/EmployeesView.vue'),meta:{title:'员工花名册'}},
  {path:'profile',component:()=>import('../views/ProfileView.vue'),meta:{title:'我的'}},
  {path:'attendance',component:()=>import('../views/AttendanceView.vue'),meta:{title:'考勤打卡'}},
  {path:'leave',component:()=>import('../views/LeaveView.vue'),meta:{title:'请假审批'}},
  {path:'payroll',component:()=>import('../views/PayrollView.vue'),meta:{title:'薪资记录'}},
  {path:'recruitment',component:()=>import('../views/RecruitmentView.vue'),meta:{title:'招聘管理'}}
 ]}
]
const router=createRouter({history:createWebHistory(),routes,scrollBehavior:()=>({top:0})})
router.beforeEach(to=>{ document.title=`${to.meta.title || '人力资源'}｜知华科技 EHR`; if(!to.meta.public&&!localStorage.getItem('zhuatech_token')) return '/login'; if(to.path==='/login'&&localStorage.getItem('zhuatech_token')) return '/' })
export default router
