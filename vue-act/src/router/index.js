import Vue from "vue";
import VueRouter from "vue-router";
import Act from '@/views/activiti/Index'

Vue.use(VueRouter);

const routes = [
  {
    path: '/Act',
    name: 'Act',
    component: Act,
    children: [
      {
        path: 'Bus',
        name: 'Bus',
        component: () => import(`@/views/activiti/bus/Bus`)
      },
      {
        path: 'Approve',
        name: 'Approve',
        component: () => import(`@/views/activiti/approve/Approve`)
      },
      {
        path: 'ApproveDetail',
        name: 'ApproveDetail',
        component: () => import(`@/views/activiti/approve/detail/Detail`)
      },
      {
        path: 'Query',
        name: 'Query',
        component: () => import(`@/views/activiti/query/Query`)
      },
      {
        path: 'QueryDetail',
        name: 'QueryDetail',
        component: () => import(`@/views/activiti/query/detail/Detail`)
      },
    ]
  }
];

const router = new VueRouter({
  routes,
});

export default router;
