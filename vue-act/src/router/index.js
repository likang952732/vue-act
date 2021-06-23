import Vue from "vue";
import VueRouter from "vue-router";
import Act from '@/views/activiti/Index'

Vue.use(VueRouter);

const routes = [
  {
    //path: '/Act',
    path: '/',
    name: 'Act',
    component: Act,
    children: [
      {
        path: 'Bus',
        name: 'Bus',
        component: () => import(`@/views/activiti/bus/Bus`),
        meta: {keepAlive: false}
      },
      {
        path: 'Approve',
        name: 'Approve',
        component: () => import(`@/views/activiti/approve/Approve`),
        meta: {keepAlive: false}
      },
      {
        path: 'ApproveDetail',
        name: 'ApproveDetail',
        component: () => import(`@/views/activiti/approve/detail/Detail`),
        meta: {keepAlive: false}
      },
      {
        path: 'Query',
        name: 'Query',
        component: () => import(`@/views/activiti/query/Query`),
        meta: {keepAlive: false}
      },
      {
        path: 'QueryDetail',
        name: 'QueryDetail',
        component: () => import(`@/views/activiti/query/detail/Detail`),
        meta: {keepAlive: false}
      },
      {
        path: 'Design',
        name: 'Design',
        component: () => import(`@/views/activiti/model/Design`),
        //meta: {keepAlive: true}
      },
      {
        path: 'History',
        name: 'History',
        component: () => import(`@/views/activiti/history/History`),
        //meta: {keepAlive: true}
      }
    ]
  }
];

const router = new VueRouter({
  routes,
});

export default router;
