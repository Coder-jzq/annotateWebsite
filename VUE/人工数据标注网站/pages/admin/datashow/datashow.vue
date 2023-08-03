<template>
	<view>
		 <el-divider content-position="center"></el-divider>
		<view style="line-height: 30px; padding: 10px; margin-bottom: 30px; text-align: left;">
			<el-button plain disabled style="width: 240px; margin-bottom: 15px;" @tap="download()">下载标注数据</el-button>
			
			<el-alert
			    :title="message1"
			    type="success">
			  </el-alert>
			<div style="padding: 20px 20px 0 20px;">
				<text selectable="true">
					情感数据：
					{{this.$store.state.url + "/export-excel-emotion"}}
				</text>
			</div>
			
			<div style="padding: 20px 20px 0 20px;">
				<text selectable="true">
					重音数据：
					{{this.$store.state.url + "/export-excel-accent"}}
				</text>
			</div>
			 
		</view>
	
		
		<el-divider content-position="center"></el-divider>
		
		<el-form style="line-height: 30px; text-align: left; margin-top: 70px;" ref="queryForm" size="small" :inline="true" label-width="68px">
		   <el-form-item label="标注人" prop="recordIndex">
		     <el-input
		       v-model="username"
		       placeholder="请输入标注人账号"
		       clearable
		       @keyup.enter.native="handleQuery"
		     />
		   </el-form-item>
			  <el-button
			    type="success"
			    plain
			    icon="el-icon-search"
			    size="mini"
			    @click="search_user()"
			  >搜索</el-button>
			  
			  <text style="padding-left: 50px;">标注记录条数：</text>
			  <el-tag
			    type="success">
			   {{count}}
			  </el-tag>
			  
			  
		
			<el-alert
			    :title="message2"
			    type="success">
			  </el-alert>	
			
			</br>
			<el-form-item label="标注人" prop="recordIndex">
			  <el-input
			    v-model="username2"
			    placeholder="请输入标注人账号"
			    clearable
			    @keyup.enter.native="handleQuery"
			  />
			</el-form-item>
			
			<el-button
			  type="success"
			  plain
			  icon="el-icon-search"
			  size="mini"
			  @click="getOmission()()"
			>搜索</el-button>
			
			<text style="padding-left: 50px;">{{username}}遗漏对话索引：</text>
			<div>
				{{lostArr}}
			</div>
			
			<el-alert
			    :title="message3"
			    type="success">
			  </el-alert>
			
				
			<div style="padding-top: 20px;">
				<el-button plain style="width: 240px;" @tap="getCounts()">查询所有标注记录</el-button>
				<text style="padding-left: 50px;">标注记录总条数：</text>
				<el-tag
				  type="success">
				 {{tableCountSum}}
				</el-tag>
				
			</div>
			<el-table
			    :data="tableData"
			    style="width: 100%;margin: 24px 0;">
			    <el-table-column
			      prop="username"
			      label="账号"
			      width="180">
			    </el-table-column>
			    <el-table-column
			      prop="count"
			      label="目前标记数量"
			      width="180">
			    </el-table-column>
			    </el-table>
		 </el-form>
		
		
	</view>
</template>

<script>
	import {downloadRequest} from '@/api/download/download'
	export default {
		data() {
			return {
				activeNames: ["1"], 
				username: '', // 用户账号值
				username2: '', // 用户账号值
				count: 0,    // 显示标记个数
				message1: `点击"下载标注数据"，可将标注记录导出到excel文件，但是由于数据集较大有约等于34M，
							下载处理的速度取决于服务器的硬件设置，如若点击无反应，请稍等片刻。(注意):如果
							下载无效请点击下面外链下载：`,     // 提示
				message2: `输入标注人账号，可查询出该标注人的漏标对话。`,     // 提示
				message3: `输入标注人账号，可查询出该标注人标注的信息总数。`,     // 提示
				tableData: [],    // 所有人的标记记录
				tableCountSum: 0,
				lostArr: "-",  // 漏标索引
			}
		},
		methods: {
			getOmission() {  // 查看遗漏标记的对话索引
						
				let userarr1 = ["tts001", "tts003", "tts005", "tts007", "tts009", "tts011", "tts013", "tts015", "tts017", "tts019"];
				let start=1270;
				let end = 2540;
				userarr1.forEach(item => {
					if(this.username2 == item){
						start = 0
						end = 1269
					}
				})
				uni.request({
					method:"POST",
					// url: this.$store.state.url + '/bz-ann-record/getAnnRecordCountS',
					url: 'http://127.0.0.1:8811/api/file/getlost',
					data: {
						"ann_record_user": this.username2,
						"start": start,
						"end": end
					},
					success: (res) => {
						// 加载结束
						if(res.data.code == 200) {
							uni.showToast({
								title: "获取成功:"
							})
							this.lostArr = res.data.result
						}else {
							uni.showToast({
								title: "获取失败"
							})
						}
					},
				})
			},
			getCounts(){ // 查询所有人标注记录
				const loading = this.$loading({
				          lock: true,
				          text: '获取标注记录中',
				          spinner: 'el-icon-loading',
				          background: 'rgba(0, 0, 0, 0.7)'
				        });
						
						
				uni.request({
					method:"GET",
					url: this.$store.state.url + '/bz-ann-record/getAnnRecordCountS',
					// url: 'http://127.0.0.1:8811/api/bz-ann-record/getAnnRecordCountS',
					success: (res) => {
						// 加载结束
						loading.close();
						if(res.data.code == 200) {
							uni.showToast({
								title: "获取成功:"
							})
							this.tableData = res.data.result
							
							let sum=0;
							res.data.result.forEach(item => {
								sum+=item.count
							})
							
							this.tableCountSum = sum;
						}else {
							uni.showToast({
								title: "获取失败"
							})
						}
					},
				})
			},
			// 查询该记录人标注的记录条数
			search_user(){
				// 加载ing
				const loading = this.$loading({
				          lock: true,
				          text: '获取标注人标注记录中',
				          spinner: 'el-icon-loading',
				          background: 'rgba(0, 0, 0, 0.7)'
				        });
						
				uni.request({
					method:"GET",
					url: this.$store.state.url + '/bz-ann-record/getAnnRecordCountByDhAndUser',
					data: {
						"username": this.username
					},
					success: (res) => {
						// 加载结束
						loading.close();
						if(res.data.code == 200) {
							uni.showToast({
								title: "获取成功:" + res.data.result + "条"
							})
							this.count = res.data.result
						}else {
							uni.showToast({
								title: "获取失败"
							})
							this.count = 0
						}
					},
				})
			},
			// 下载数据集
			download(){
				uni.showToast({
					title:'开始下载...'
				})
				// downloadRequest('/export-excel');
				uni.request({
					method: "GET",
					url: this.$store.state.url + '/export-excel',
					timeout: 300000,
					success: (res) => {
						console.log(res)
					}
				})
				
			},
			
		}
		
		
	}
</script>

<style>
</style>
