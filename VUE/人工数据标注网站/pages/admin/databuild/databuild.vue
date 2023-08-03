<template>
	<view>
		<view style="line-height: 30px; padding: 10px; margin-bottom: 30px; text-align: left;">
			<el-button plain disabled style="width: 240px;" @tap="initDatas">上传数据集</el-button>
			<!-- <el-button plain disabled style="width: 240px;" @tap="delAll">清理所有数据集</el-button> -->
			<el-button plain disabled style="width: 240px;" @tap="delAll">清理所有数据集</el-button>
		</view>
		
		<view style="margin: 30px 0; line-height: 30px;">
			<el-alert
			    :title="message1"
			    type="warning">
			  </el-alert>
			  <view style="margin: 5px;"></view>
			  <el-alert
			      :title="message2"
			      type="error">
			    </el-alert>
		</view>
		
		<view style="background-color: #FFFFFF; padding: 10px;">
			<view style="text-align: left;
						font-weight: 700;
						line-height: 30px;">上传进度（已完成上传）：</view>
			<view style="">
				<el-progress :percentage="percentage" status="success"></el-progress>
			</view>
			
		</view>
		
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				count: 10,   // 对话总数
				percentage: 100,   // 上传进度
				message1: `上传数据集: 目的为将数据集的索引-单词(0-0-0 word)上传至数据库，方便后期的标注工作。
						  理论上只需一次上传即可。切勿随意操作，打乱数据库中保存的数据集格式。`,     // 提示
				message2: `清理所有数据集: 谨慎操作，会删除数据表中所有数据，包括已标注的数据。该功能在初始化数据集无误
							后，理应禁用。`,     // 提示
				timer: null,  // 定义计时器变量  // 对话延迟器
				// timer2: null,  // 定义计时器变量 // 句子延迟器
			}
		},
		methods: {
			// 清除数据
			delAll(){
				uni.request({
					method:"DELETE",
					url: this.$store.state.url + '/bz-record/delAll',
					success: (res) => {
						uni.showToast({
							title: res.data.message
						})
					}
				})
			},
			// 生成所有数据,初始到数据表
			initDatas() {
				console.log("初始化开始...")
				let count = 2540;
				let i=2540;
				let timer = setInterval(() => {
					// 调用获取文章
					this.getDuiHua(i)
					
					// 更新进度
					this.percentage = (i/count) * 100
					
					
					if(i==count){  // 每个对话都遍历结束
						clearInterval(timer)
					}
					
					i++;
				}, 40000);
				
				
			},
			// 递归遍历对话
			async diGuiInitDatas(dhidx) {
				console.log(dhidx,typeof(dhidx), "**")
				if(dhidx>=this.count) {
					// 假如对话总数够了，终止递归
					return;
				}
				
				// 递归调用
				await this.getDuiHua(dhidx);
				
				// 更新进度
				// this.percentage = (dhidx/this.count) * 100
				
				// 同步递归调用
				await this.diGuiInitDatas(dhidx+1)
			},
			
			// 获取第idx个对话的文件名
			getDuiHua(dhidx) {  
				// 获取对应文章的文件名
				new Promise((resolve, reject)=>{
					uni.request({
						method: 'GET',
						url: this.$store.state.url + '/file/getFiles',
						data: {
							"idx": dhidx,
						},
						success: (res) => {
							if(res.data.code==200) {
								// 获取成功(混合的wav，txt)
								let arr = res.data.result;
								let arrTxtList = [];  // 用于存txt
								arr.forEach((item) => {
									let wzfl = item.split(".")
									if(wzfl[1] == 'txt') {
										// 后缀为txt
										arrTxtList.push(item)
									}
								})
								
								// 调用成功回调
								resolve(arrTxtList)
							}else {
								uni.showToast({
									title: '资源获取失败'
								})
							}
						}
						
					})
				}).then((arrTxtList)=>{
					// 调用具体对话处理（arrTxtList为获取到的txt文件名）
					this.read_f(arrTxtList, dhidx)
				},(err)=>{
					console.log(err)
					this.$message({
					      message: err.msg,
					      type: 'error'
					 });
				})
			},
			ceshi1(){
				setTimeout(() => {
					console.log("一次")
				}, 1000);
			},
			// 我们当点击某个具体文章的时候再加载
			read_f(data, dhidx) {
				let arrTxtList = data;
				// 通过规则排序（因为正则化读取按某种规则排序，不合适我们的需求，按我们的规则排序）
				arrTxtList.sort(function(a,b){
					return parseInt(a.replace(/_(.*).txt/g, "")) - parseInt(b.replace(/_(.*).txt/g, ""))
				})
				
				let pathbase = "http://121.41.10.38:8822/" + dhidx + "/";
				// 开始加载txt与音频
				let jzidx=0;
				let timer2 = setInterval(()=>{
					
					// 每200毫秒请求一次，word，并入库
					this.readTxt(pathbase + arrTxtList[jzidx], dhidx, jzidx);
					
					if(jzidx==arrTxtList.length){
						// 每个句子都遍历了
						clearInterval(timer2)
					}
					
					jzidx++;
				}, 2000)
				
				
				
				
			},
			readTxt(path, dhidx, jzidx) {   // 读取txt文件，并返回单词分离格式
				
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
							
							// 传输到数据库
							uni.request({
								method:"POST",
								url: this.$store.state.url + '/bz-record/addJz',
								data: {
									"words": txtList,
									"dh": dhidx,
									"jz": jzidx
								},
								success: (res) => {
									console.log(res.data.message)
								}
							})
						}else {
							
						}
					}
				})
			}
			
			
			
		}
		
		
	}
</script>

<style>
</style>
