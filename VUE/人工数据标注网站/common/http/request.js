// var baseUrl = 'http://1x:8888/'

// const request = (options)=>{
// 	options.url = baseUrl + options.url;
// 	try {
// 		// 获取缓存的字段
// 		const token = uni.getStorageSync('token')
// 		const AmdId = uni.getStorageSync('AmdId')
		
// 		if(token && AmdId) {
// 			options.header = {
// 				'Content-Type' : 'application/json',
// 				'Authorization': 'JWT ' + this.token,
// 				'AdmId': this.AdmId
// 			}
// 		}else {
// 			this.$message({
// 			      message: '身份验证失败',
// 			      type: 'error'
// 			 });
// 		}
// 	} catch(err) {
// 		console.log(err)
// 	}
// }