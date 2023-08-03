<template>
	<view>
		<el-container style='position: absolute; left: 0; top: 0; bottom: 0; right: 0; overflow: hidden; '>
		  <popup 
		    @closepopup = 'closepopup'
			 v-if="popupshow"
			 :option= 'option'></popup>
		  <el-header height='80px' class='flex align-center justify-between'>
				<view style="font-size: 65rpx;font-weight: 500;">
					<text style="color: #FFFFFF;font-family: Hiragino Sans GB;letter-spacing:10rpx;">
						人工数据标注管理员管理中心
					</text>
				</view>
				<view>
					<el-menu
					  :default-active="activeIndex2"
					  class="el-menu-demo"
					  mode="horizontal"
					  @select="handleSelect"
					  background-color="#545c64"
					  text-color="#fff"
					  active-text-color="#ffd04b">
					  
					  <el-menu-item @tap="loout">退出登录</el-menu-item>
					</el-menu>
				</view>
		  </el-header>
		  <el-container>
		    <el-aside :style='WindowsHeight' width="200px">
				<el-menu
				      default-active="1"
				      class="el-menu-vertical-demo"
					  unique-opened=true
				      @open="handleOpen"
				      @close="handleClose"
					  @select="handSelect"
				      background-color="#545c64"
				      text-color="#fff"
				      active-text-color="#ffd04b"
					  >
					  <el-menu-item index="1">
					    <i class="el-icon-menu"></i>
					    <span slot="title">首页中心</span>
					  </el-menu-item>
					  
					  
				      <el-submenu index="2">
				        <template slot="title" >
				          <i class="el-icon-location"></i>
				          <span>数据生成</span>
				        </template>
				          <el-menu-item index="2-1" class='el-menu-item-action'>生成数据集</el-menu-item>
				      </el-submenu>
					  
					  <el-submenu index="3">
					    <template slot="title">
					      <i class="el-icon-location"></i>
					      <span>数据查看</span>
					    </template>
						<el-menu-item index="3-1" class='el-menu-item-action'>标注记录</el-menu-item>
						<!-- <el-menu-item index="3-2" class='el-menu-item-action'>标注人记录</el-menu-item> -->
					  </el-submenu>
					  
					  
					  
					 
					  
				    </el-menu>
			</el-aside>
		    <el-main :style='WindowsHeight'>
				<view>
					<admin-index v-if="index == '1'"></admin-index>
					<databuild v-if="index == '2-1'"></databuild>
					<datashow v-if="index == '3-1'"></datashow>
					<!-- <dataCount v-if="index == '3-2'"></dataCount> -->
					
				</view>
			</el-main>
		  </el-container>
		</el-container>
	</view>
</template>

<script>
	import databuild from '@/pages/admin/databuild/databuild.vue';
	import datashow from '@/pages/admin/datashow/datashow.vue';
	import dataCount from  '@/pages/admin/datashow/datasCount.vue';
	export default {
		data() {
			return {
				activeIndex: '1',
				activeIndex2: '1',
				index:'1',
				Gid: -1,
				popupshow: false,
				option: null,
			}
		},
		computed:{
			WindowsHeight(){
				let height
				let width
				uni.getSystemInfo({
					success:function(res){
						height = res.windowHeight - 20
					}
				});
				return `height:${height}px`
			}
		},
		onLoad() {
			// 检测是不是管理员用户
			this.isAdmin()
		},
		methods: {
			// 退出登录
			loout(){
				// 清理缓存即可
				// 状态存缓存，确定是用户还是管理
				uni.setStorageSync('status', '')
				// 用户label存缓存，方便后期入库
				uni.setStorageSync('user_label', '')
				// 存一下用户名，区别标注
				uni.setStorageSync("username", '')
				uni.redirectTo({
					url: '../login/login'
				})
			},
			// 检查管理员
			isAdmin() {
				if(uni.getStorageSync('status') != 'admin') {
					uni.showToast({
						title: '不是管理员'
					})
					
					uni.redirectTo({
						url: '../login/login'
					})
				}
			},
			handleOpen(key, keyPath) {
			     console.log(key, keyPath);
			},
			handleClose(key, keyPath) {
			    console.log(key, keyPath);
			},
			handSelect(index,indexPath){
				this.index = index
				console.log(index)
			},
			
			// 接收子组件传输的index值
			getIndex(msg) {
				//  子组件传输过来的 第一个数据是显示群成员的flag， 第二个数据是群id
				
				this.index = msg[0]
				this.Gid = msg[1]
			},
			
			// 关闭popup
			closepopup() {
				this.popupshow = false
			},
			// 打开popup
			openpopup(option) {
				console.log(option)
				this.option = option
				this.popupshow = true
			}
		},
		components:{
			databuild,
			datashow,
			dataCount
		}
	}
</script>

<style>
	  .el-header{
		  font-size: 58rpx;
		  background-color:#545c64;
	  }
	  .el-aside {
		background-color:#545c64;
		
	    text-align: center;
	    line-height: 200px;
	  }
	  
	  .el-main {
	    background-color: #E9EEF3;
	    color: #333;
	    text-align: center;
	    line-height: 160px;
	  }
	  
	  .el-menu-item {
		  padding-top: 10px;
		  font-size: 18px;
		  text-align: center;
		  height: 80px;
	  }
	  
	  .el-menu-item-action {
		  padding: 0;
		  width: 190px;
		  height: 55px;
		  font-size: 15px;
		  color: #204D74;
	  }
	  
	  
	  /deep/ .el-submenu__title{
		  padding-top: 10px;
		  height: 80px;
		  padding-bottom: 10px;
		  font-size: 18px;
		  text-align: center;
	  }
</style>
