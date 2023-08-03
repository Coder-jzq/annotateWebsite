<template>
  <view class="main-iframe" v-infinite-scroll="load">
  		<h1 style="text-align: center; color: #fefdf5;padding-top: 35px;">对话列表</h1>
  		<view style="width: 100%; display: flex; flex-wrap: wrap; justify-content: space-around;">
			<view v-for="(item, index) in count" :key="index"
				 class="box"
				 style="min-width: 10%;"
				 @tap="toWzDetail(index + differ)">第{{index + differ}}个对话</view>
		</view>
  		
  </view> 
</template>

<script>
  export default {
    data () {
      return {
        count: 1270 ,  // 记录当前文章数
		differ: 0,     // 默认为第一部分
		// wzTxtListInfo: [],       // 存所有文件名
		// wzAuListInfo: [],        // 存所有音频
      }
    },
	onLoad() {
		// 读取文件名
		// this.readFile()
		// 检测登录状态
		this.isUser()
		
		// 判断属于上半还是下半(因为正好奇数个，假如5个，上半下标0-1，下标下标2-4)
		this.differ = uni.getStorageSync('differ')=='1'? 0:1270;
		this.count = uni.getStorageSync('differ')=='1'?1270:1271
	},
    methods: {
		// 检查登录
		isUser() {
			if(uni.getStorageSync('status') != 'user') {
				uni.showToast({
					title: '不是用户'
				})
				
				uni.redirectTo({
					url: '../login/login'
				})
			}
		},
		// 生成序号
		getCountList() {
			for(let i=0; i<this.count; i++) {
				
			}
		},
		
    	load () {
    	    // 调用接口
			// console.log("77777")
    	},
		
		toWzDetail(idx) {  // 跳转对应详情界面
			// 获取对应文章的文件名
			new Promise((resolve, reject)=>{
				uni.request({
					method: 'GET',
					url: this.$store.state.url + '/file/getFiles',
					data: {
						"idx": idx,
					},
					success: (res) => {
						if(res.data.code==200) {
							// 获取成功(混合的wav，txt)
							let arr = res.data.result;
							
							let arrTxtList = [];  // 用于存txt
							let arrAuList = [];   // 用于存wav
							arr.forEach((item) => {
								let wzfl = item.split(".")
								if(wzfl[1] == 'txt') {
									// 后缀为txt
									arrTxtList.push(item)
								}else if(wzfl[1] == 'wav') {
									// 后缀为wav
									arrAuList.push(item)
								}
							})
							// 放全局
							this.$store.state.arrTxtList = arrTxtList;
							this.$store.state.arrAuList = arrAuList;
							// console.log(arrTxtList, arrAuList)
							
							// 调用回调
							resolve()
						}else {
							uni.showToast({
								title: '资源获取失败'
							})
						}
					}
					
				})
			}).then((data)=>{
				// 执行接口请求成功
				// console.log("成功")
				uni.navigateTo({
					url: './dataDetail?id='+idx
				})
			},(err)=>{
				// console.log(err)
				this.$message({
				      message: err.msg,
				      type: 'error'
				 });
			})
			
		
			
		},
		
		// 我们当点击某个具体文章的时候再加载
		read_f(idx) {
			// console.log(this.wzTxtInfo)
			// 通过规则排序（因为正则化读取按某种规则排序，不合适我们的需求，按我们的规则排序）
			this.wzTxtInfo.sort(function(a,b){
				return parseInt(a.replace(/_(.*).txt/g, "")) - parseInt(b.replace(/_(.*).txt/g, ""))
			})
			this.wzAuInfo.sort(function(a,b){
				return parseInt(a.replace(/_(.*).wav/g, "")) - parseInt(b.replace(/_(.*).wav/g, ""))
			})
			// console.log(this.wzTxtInfo,this.wzAuInfo)
			
			let pathbase = "http://121.41.10.38:8822/" + idx + "/";
			// 开始加载txt与音频
			for(let i=0; i<this.wzTxtInfo.length; i++) {
				const wavPath = pathbase + this.wzAuInfo[i];
				let obj={
					"words": ["加","载", "ing"],
					"music": {
						"title": '音频文件' + i,
						"url": wavPath,
						"lrc": "[00:00:00]lrc here\n[00:01:00]aplayer"
					}
				}
				this.dhList.push(obj)
				// 加载txt
				this.readTxt(pathbase + this.wzTxtInfo[i], i);
			}
			// console.log(this.dhList)
			
			// 初始化选中标签结构
			for(let i=0; i<this.dhList.length; i++) {
				let arrT = [];
				// 根据单词个数赋值0
				for(let j=0; j<this.dhList[i].words.length; j++) {
					arrT.push(0);
				}
				this.activeArr.push(arrT);
			}
			
			// console.log(this.activeArr)
			
		},
		
		readTxt(path, idx) {   // 读取对应txt文件，并返回单词分离格式
			
			uni.request({
				url: path,
				method:"GET",
				success: (res) => {
					if(res.statusCode == 200) {
						// 获取成功
						let txt = res.data
						let tttt = txt.replace(/[(?<!\.{1,})\.(?!\.{1,})]|[,?!]/g , ' $&').trim()
						let tttt2 = tttt.replace(/[-]/g , ' $& ').trim()
						let txtList = tttt2.split(" ")
						this.$set(this.dhList[idx], 'words', txtList)
					}else {
						
					}
				}
			})
		}
		
		// readFile() { // 初始化页面的时候会把所有的文本和音频的路径进行加载
		// 	const tt = require.context("../../static/data",true, /\.txt$/).keys();
		// 	let textFiles= tt.map(item => {
		// 		return item.substr(2,)
		// 	})
			
		// 	// 读取某篇文章的音频名(根据正则化读取一个文件夹里的音频名)
		// 	const au = require.context("../../static/data",true, /\.wav$/).keys();
		// 	let auFiles= au.map(item => {
		// 		return item.substr(2,)
		// 	})
		// 	console.log(tt)
		// 	console.log(textFiles)
		// 	// 初始化一个数组
		// 	let activeArray = []
		// 	let activeIndex = 0
		// 	textFiles.forEach(item => {
		// 		let wzfg = item.split('/')
		// 		let wzId = parseInt(wzfg[0]);// 拿到文章id
		// 		if(wzId === activeIndex) {
		// 			activeArray.push(wzfg[1])
		// 		}else {
		// 			// 说明一篇文章完事了
		// 			this.wzTxtListInfo.push(activeArray)
					
		// 			// 活动标志++
		// 			activeIndex++;
		// 			// 活动清空
		// 			activeArray=[]
		// 		}
				
		// 	})
		// 	// 最后一篇
		// 	if(activeArray.length!=0) {
		// 		this.wzTxtListInfo.push(activeArray)
		// 	}
			
		// 	// 初始化一个数组
		// 	let activeArray2 = []
		// 	let activeIndex2 = 0
		// 	auFiles.forEach(item => {
		// 		let wzfg = item.split('/')
		// 		let wzId = parseInt(wzfg[0]);// 拿到文章id
		// 		if(wzId === activeIndex2) {
		// 			activeArray2.push(wzfg[1])
		// 		}else {
		// 			// 说明一篇文章完事了
		// 			this.wzAuListInfo.push(activeArray2)
					
		// 			// 活动标志++
		// 			activeIndex2++;
		// 			// 活动清空
		// 			activeArray2=[]
		// 		}
				
		// 	})
		// 	// 最后一篇
		// 	if(activeArray2.length!=0) {
		// 		this.wzAuListInfo.push(activeArray)
		// 	}
			
		// 	// 存缓存方便别的界面调用
		// 	this.$store.state.wzAuListInfo = this.wzAuListInfo
		// 	this.$store.state.wzTxtListInfo = this.wzTxtListInfo
			
		// 	console.log(this.wzTxtListInfo, this.wzAuListInfo)
		// },
    }
  }
</script>

<style lang="scss">
	
.main-iframe {
   height: calc(100vh);
   background-color: #474546;
   overflow: auto;
   // 其他CSS....
   
   .box {
	    height: 30px; padding: 20px;
	    background-color: #fefdf5; 
	    margin: 0 70px;
		margin-top: 45px;
	    border-radius: 20px;
	    line-height: 30px;
	    text-align: center;
	    font-size: 30px;
	    box-shadow: 0px 5px 15px 1px #383838;
	    cursor:pointer;
   }
   
   .box:hover {
	   background-color: #d1d0ca;
   }
}
</style>
