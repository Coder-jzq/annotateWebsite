<template>
	<view style="padding: 0 30px;" class="main-iframe">
		<div v-for="(item, index1) in dhList" :key="index1" 
			style="background-color: #FFFFFF; margin-top: 15px; padding: 20px;
				border-radius: 20px;
				box-shadow: 0px 4px 12px 0px #c3c3c3;">
			<div style="height: 100px;"><aplayer :music="item.music" id="aplayer"></aplayer></div>
			
			<!-- 重音 -->
			<el-divider content-position="left">重音标记</el-divider>
			<div style="display: flex; justify-content: flex-start; flex-wrap:wrap">
				<div v-for="(item, index2) in item.words" :key="index2" 
					style="margin: 10px; width: 70px; height: 50px;
					 line-height: 50px; text-align: center;
					 border: 2px solid #c5c5c5; font-size: 14px;
					 border-radius: 10px;
					 cursor: pointer;"
					 @tap="clickWord(index1, index2)"
					 :class="{'active-box':activeArr[index1][index2]==1}">
						{{item}}
				</div>
			</div>
			
			<!-- 停顿 -->
			<div style="padding-top: 10px;">
				<el-divider content-position="left">停顿标记</el-divider>
			</div>
			<div style="display: flex; justify-content: flex-start; flex-wrap:wrap">
				<div v-for="(item, index2) in item.words" :key="index2" 
					style="margin: 10px; width: 70px; height: 50px;
					 line-height: 50px; text-align: center;
					 border: 2px solid #c5c5c5; font-size: 14px;
					 border-radius: 10px;
					 cursor: pointer;"
					 @tap="clickWordPause(index1, index2)"
					 :class="{'active-box':activeArrPause[index1][index2]==1}">
						{{item}}
				</div>
			</div>
			
			<div style="width: 90%; text-align: left;
						margin: 30px 0; display: flex; 
						align-items: center; 
						justify-content: space-between;">
				<el-button v-if="bzActive[index1]!=1" round style="min-width: 30%;" @tap="throttlebiaozhu(wzId, index1)">提交已勾选标注</el-button>
				<el-button v-else disabled round style="min-width: 30%;">提交已勾选标注</el-button>
				<div>
					<el-tag v-show="bzActive[index1]==1" style="cursor: pointer;">已标注</el-tag>
					<!-- <div style="padding: 0 10px;"></div> -->
					<el-tag v-show="bzActive[index1]==1" style="margin-left: 10px; cursor: pointer;" @tap="throttlechehui(wzId, index1)">撤回标注</el-tag>
				</div>
			</div>
		</div>
		<div style="padding: 30px 0; text-align: center;">
			<el-button round style="min-width: 30%;" @tap="toWzDetail(wzId + 1)">跳转到下一个对话：序号({{wzId + 1}})</el-button>
		</div>
	</view>
</template>

<script>
	import aplayer from 'vue-aplayer';
	export default {
		data() {
			return {
				dhList: [],    // 一篇文章的对话
				music: {
					"title": '音频文件',
					"url": "http://43.140.212.52:8822/0/0_1_d0.wav",
					"lrc": "[00:00:00]lrc here\n[00:01:00]aplayer"
				},
				wzId: null,   // 文章id
				wzTxtInfo: [],       // 存本篇文件名
				wzAuInfo: [],        // 存本篇音频
				activeArr: [],        // 记录选中的单词
				bzActive: [],        // 记录某个句话是否已标注
				// ttsOption: ['快乐', '伤心', '难过', '愤怒'],  // 情绪 	
				activeArrPause: [],        // 记录选中停顿的单词
			}
		},
		
		onLoad(option) {
			// 检测登录状态
			this.isUser()
			
			this.wzId = parseInt(option.id) // 拿到文章id
			// 获取对应文章的对话信息
			this.wzTxtInfo = this.$store.state.arrTxtList;
			this.wzAuInfo = this.$store.state.arrAuList;
			
			// 调用 对应的 加载信息
			this.read_f(this.wzId)
			
			// 获取标注信息
			this.getBzRecord(this.wzId, this.wzTxtInfo.length);
		},
	
		methods: {
			throttlebiaozhu(dh,jz) {
				// 节流调用提交标注
				this.$u.throttle(()=>this.biaozhu(dh, jz), 5000);
			},
			
			throttlechehui(dh,jz) {  // 撤回标注节流
				// 节流调用撤回标注
				this.$u.throttle(()=>this.chehui(dh, jz), 5000);
			},
			
			// 撤回标注记录
			chehui(dh, jz){
				// 生成标注下标
				let wordsIndex = [];
				this.activeArr[jz].forEach((item, index) => {
					wordsIndex.push(index)
				})
				
				// 重置标注信息
				// 修改标注记录
				uni.request({
					method:"PUT",
					url: this.$store.state.url + '/bz-record/updataRecord_0',
					data: {
						"dh": dh,
						"jz": jz,
						"wordsIndex": wordsIndex,
						"userIndex": uni.getStorageSync('user_label')
					},
					success: (res) => {
						uni.showToast({
							title: res.data.message
						})
						
						
						// 记录标注人进行了标注
						uni.request({
							method:"DELETE",
							url: this.$store.state.url + '/bz-ann-record/deleteRecord',
							data:{
								"annRecordDh": dh,
								"annRecordJz": jz,
								"annRecordUser": uni.getStorageSync('username')
							},
							success: (res) => {
								this.$set(this.bzActive, jz, 0);
							}
						})
					}
				})
			},
			
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
			// 处理标注(记录标注， 标注修改)
			biaozhu(dh,jz){
				// 处理标注信息
				let wordsIndex = [];   // 用来保存
				this.activeArr[jz].forEach((item, index) => {
					if(item==1) {
						// 说明该单词被标记了, 把对应的下标标记
						wordsIndex.push(index);
					}
				})
				// 停顿
				let wordsPauseIndex = [];   // 用来保存
				this.activeArrPause[jz].forEach((item, index) => {
					if(item==1) {
						// 说明该单词被标记了, 把对应的下标标记
						wordsPauseIndex.push(index);
					}
				})
				
				// 修改标注记录
				uni.request({
					method:"PUT",
					url: this.$store.state.url + '/bz-record/updataRecord',
					data: {
						"dh": dh,
						"jz": jz,
						"wordsIndex": wordsIndex,
						"wordsPauseIndex":wordsPauseIndex,
						"userIndex": uni.getStorageSync('user_label')
					},
					success: (res) => {
						uni.showToast({
							title: res.data.message
						})
						
						
						// 记录标注人进行了标注
						uni.request({
							method:"POST",
							url: this.$store.state.url + '/bz-ann-record/addAnnRecord',
							data:{
								"annRecordDh": dh,
								"annRecordJz": jz,
								"annRecordUser": uni.getStorageSync('username')
							},
							success: (res) => {
								if(res.data.code == 500) {
									uni.showToast({
										title: res.data.message
									})
								}
								this.$set(this.bzActive, jz, 1);
							}
						})
					}
				})
			},
			
			//获取标注信息
			getBzRecord(dh, arrlen){
				let arrT = [];
				for (var i = 0; i < arrlen; i++) {
					arrT.push(0)
				}
				
				// 请求接口
				uni.request({
					method:"GET",
					url: this.$store.state.url + '/bz-ann-record/getAnnRecordByDhAndUser',
					data:{
						"dh": dh,
						"username": uni.getStorageSync('username')
					},
					success: (res) => {
						res.data.result.forEach((item,index)=>{
							arrT[item.annRecordJz] = 1;
						})
						
						this.bzActive = arrT
					}
				})
			},
			
			
			// 跳转到下一个对话
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
					uni.redirectTo({
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
			
			// 点击单词时，更改某个单词被选中的状态
			clickWord(idx1,idx2) {  // 分别表示第几句话的第几个单词
				
				this.$set(this.activeArr[idx1], idx2, this.activeArr[idx1][idx2] == 0 ? 1 : 0)
			},
			
			// 点击单词时，更改某个单词被选中的状态
			clickWordPause(idx1,idx2) {  // 分别表示第几句话的第几个单词
				
				this.$set(this.activeArrPause[idx1], idx2, this.activeArrPause[idx1][idx2] == 0 ? 1 : 0)
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
				
				let pathbase = "http://43.140.212.52:8822/" + idx + "/";
				// 开始加载txt与音频
				for(let i=0; i<this.wzTxtInfo.length; i++) {
					const wavPath = pathbase + this.wzAuInfo[i];
					let obj={
						"words": ["加","载", "ing"],
						"music": {
							"title": '音频文件' + i,
							"url": wavPath,
							"lrc": "[00:00:00]lrc here\n[00:01:00]aplayer"
						},
				
					}
					this.dhList.push(obj)
					// 加载txt
					this.readTxt(pathbase + this.wzTxtInfo[i], i);
				}
				
				// // 初始化选中标签结构
				// for(let i=0; i<this.dhList.length; i++) {
					
				// }
				
				
			},
			readTxt(path, idx) {   // 读取txt文件，并返回单词分离格式
			
				// let xhr = new XMLHttpRequest()
				// xhr.open("GET", path, true); // public文件夹下的绝对路径
				// xhr.overrideMimeType("text/html;charset=utf-8")
				// xhr.send(null)
				// let txt = xhr.responseText   // xhr.responseText为文本中的内容
				
				// console.log(xhr,xhr.readyState,xhr['response'], xhr.responseText, "777777777")
				
				// let tttt = txt.replace(/[(?<!\.{1,})\.(?!\.{1,})]|[,?!]/g , ' $&').trim()
				
				// let txtList = tttt.split(" ")
				
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
							
							// 在这里生成选中数组
							let arrT = [];
							let arrT2= [];
							// 根据单词个数赋值0
							for(let j=0; j<txtList.length; j++) {
								arrT.push(0);
								arrT2.push(0);
							}
							// 重音
							this.activeArr.push(arrT);
							// 停顿
							this.activeArrPause.push(arrT2);
						}else {
							
						}
					}
				})
			}
			
			
		},
		
		components: {
			aplayer
		}
	}
</script>

<style lang='scss' scoped>
 .main-iframe {
    height: calc(100vh);
    background-color: #f1ebee;
    overflow: auto;
	
	.active-box {
		background-color: #e0edff;
	}
}
 
/deep/ .aplayer{
 
  box-shadow: inherit;
 
  background-color: #F7F7F7;
 
  border-radius: 10px;
 
  width: 100%;
 
  .aplayer-body{
 
    .aplayer-pic{
 
      background-image: inherit;
 
      background:rgba(#acccff,0.3);
 
      border-radius: 10px;
	
      .aplayer-button{
		margin: -20px;
        background:#acccff;
 
        border: 0;
 
        width: 40px;
 
        height: 40px;
 
        opacity: inherit;
 
      }
 
      .aplayer-pause{
        right: 33px;
 
        bottom: 33px;
        .aplayer-icon-pause{
 
          width: 25px;
 
          height: 25px;
 
          left: 7px;
 
          top: 7.3px;
 
        }
 
      }
 
      .aplayer-play{
		
        .aplayer-icon-play{
		
          width: 30px;
 
          height: 30px;
 
          left: 6px;
 
          top: 5px;
 
        }
 
      }
 
    }
 
    .aplayer-info{
 
      padding:10px 30px;
 
      padding-left: 15px;
 
      .aplayer-music{
 
        .aplayer-title{
 
          color: #999;
 
          font-size: 20px;
 
        }
 
        .aplayer-author{
 
          display: none;
 
        }
 
      }
 
      .aplayer-controller{
 
        .aplayer-bar-wrap{
 
          .aplayer-bar{
 
            height: 8px;
 
            border-radius: 4px;
 
            background: #e0edff;
 
            .aplayer-loaded{
 
              height: 8px;
 
              border-radius: 4px;
 
              background: #e0edff;
 
            }
 
            .aplayer-played{
 
              height: 8px;
 
              border-radius: 4px;
 
              .aplayer-thumb{
 
                width: 16px;
 
                height: 16px;
 
              }
 
            }
 
          }
 
        }
 
        .aplayer-time{
 
          .aplayer-time-inner{
 
            font-size: 20px;
 
            color: #333;
 
          }
 
        }
 
        .aplayer-icon{
 
          display: none;
 
        }
 
      }
 
    }
 
  }
 
}
 

</style>
