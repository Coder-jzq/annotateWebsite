import Vue from 'vue'
import App from './App'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import store from 'store/index.js'
Vue.config.productionTip = false
Vue.use(ElementUI);

// main.js
import uView from "uview-ui";
Vue.use(uView);

import './common/css/global.less'

// 将全局的echarts对象 挂载到Vue的原型对象上
// import echarts from './common/js/echarts.min.js'
// Vue.prototype.$echarts = echarts

// 将echarts挂载到vue
// import * as echarts from 'echarts';
// Vue.prototype.$echarts = echarts

Vue.prototype.$store = store

App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
