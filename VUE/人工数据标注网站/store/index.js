import  Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)



const store = new Vuex.Store({
	state: {
		
		// 'url': 'http://127.0.0.1:8811/api',
		'arrTxtList': [],  // 存txt
		'arrAuList': []  // 存wav
	}
})

export default store