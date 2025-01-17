<template>
    <div class="page">
      <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
            <!-- 搜索框-->
            <el-form-item prop="corporateName">
                   <el-input size="small" v-model="searchForm.corporateName" placeholder="公司名称" clearable></el-input>
            </el-form-item>
            <el-form-item prop="dbName">
                   <el-input size="small" v-model="searchForm.dbName" placeholder="数据库名" clearable></el-input>
            </el-form-item>
            <el-form-item prop="adminAccount">
                   <el-input size="small" v-model="searchForm.adminAccount" placeholder="管理员账号" clearable></el-input>
            </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
            <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
          </el-form-item>
      </el-form>

     <div class="bg-white top">
        <vxe-toolbar :refresh="{query: refreshList}" export print custom>
          <template #buttons>
            <el-button v-if="hasPermission('wh:basicinformationbf:basicInformationbf:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
            <el-button v-if="hasPermission('wh:basicinformationbf:basicInformationbf:edit')" type="warning" size="small" icon="el-icon-edit-outline" @click="edit()" :disabled="$refs.basicInformationbfTable && $refs.basicInformationbfTable.getCheckboxRecords().length !== 1" plain>修改</el-button>
            <el-button v-if="hasPermission('wh:basicinformationbf:basicInformationbf:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()" :disabled="$refs.basicInformationbfTable && $refs.basicInformationbfTable.getCheckboxRecords().length === 0" plain>删除</el-button>
            <el-button v-if="hasPermission('wh:basicinformationbf:basicInformationbf:del')" type="danger"   size="small" icon="el-icon-delete" @click="sc()" :disabled="$refs.basicInformationbfTable && $refs.basicInformationbfTable.getCheckboxRecords().length === 0" plain>生成数据</el-button>
            <!-- <el-button v-if="hasPermission('wh:basicinformationbf:basicInformationbf:del')" type="danger"   size="small" icon="el-icon-delete" @click="tongbu()" :disabled="$refs.basicInformationbfTable && $refs.basicInformationbfTable.getCheckboxRecords().length === 0" plain>同步</el-button> -->
            <!-- <el-button v-if="hasPermission('wh:basicinformationbf:basicInformationbf:add')" type="primary"   size="small" icon="el-icon-plus" @click="cj()" >创建数据库</el-button> -->
          </template>
        </vxe-toolbar>
        <div style="height: calc(100% - 80px);">
        <vxe-table
            border="inner"
            auto-resize
            resizable
            height="auto"
            :loading="loading"
            size="small"
            ref="basicInformationbfTable"
            show-header-overflow
            show-overflow
            highlight-hover-row
            :menu-config="{}"
            :print-config="{}"
            :import-config="{}"
            :export-config="{}"
            @sort-change="sortChangeHandle"
            :sort-config="{remote:true}"
            :data="dataList"
            :checkbox-config="{}">
            <vxe-column type="seq" width="40"></vxe-column>
            <vxe-column type="checkbox"  width="40px"></vxe-column>
    <vxe-column
        field="corporateName"
        sortable
        title="公司名称">
            <template slot-scope="scope">
              <el-link  type="primary" :underline="false" v-if="hasPermission('wh:basicinformationbf:basicInformationbf:edit')" @click="edit(scope.row.id)">{{scope.row.corporateName}}</el-link>
              <el-link  type="primary" :underline="false" v-else-if="hasPermission('wh:basicinformationbf:basicInformationbf:view')"  @click="view(scope.row.id)">{{scope.row.corporateName}}</el-link>
              <span v-else>{{scope.row.corporateName}}</span>
            </template>
      </vxe-column>
    <!-- <vxe-column
        field="socialCreditCode"
        sortable
        title="社会信用代码">
      </vxe-column>
    <vxe-column
        field="industry"
        sortable
        title="所属行业">
        <template slot-scope="scope">
              {{ $dictUtils.getDictLabel("select_industry", scope.row.industry, '-') }}
        </template>
      </vxe-column>
    <vxe-column
        field="contacts"
        sortable
        title="联系人">
      </vxe-column>
    <vxe-column
        field="contactsTel"
        sortable
        title="联系方式">
      </vxe-column>
    <vxe-column
        field="businessLicense"

        sortable
        title="营业执照">
        <template slot-scope="scope" v-if="scope.row.businessLicense">
          <el-image
            style="height: 50px;width:50px;margin-right:10px;"
            :src="src" v-for="(src, index) in scope.row.businessLicense.split('|')" :key="index"
            :preview-src-list="scope.row.businessLicense.split('|')">
          </el-image>
        </template>
      </vxe-column>
    <vxe-column
        field="logo"

        sortable
        title="logo">
        <template slot-scope="scope" v-if="scope.row.logo">
          <el-image
            style="height: 50px;width:50px;margin-right:10px;"
            :src="src" v-for="(src, index) in scope.row.logo.split('|')" :key="index"
            :preview-src-list="scope.row.logo.split('|')">
          </el-image>
        </template>
      </vxe-column> -->
    <vxe-column
        field="platformName"
        sortable
        title="平台名称">
      </vxe-column>
    <vxe-column
        field="selectServe"
        sortable
        title="服务器">
      </vxe-column>
    <vxe-column
        field="dbName"
        sortable
        title="数据库名">
      </vxe-column>
    <vxe-column
        field="databaseAccount"
        sortable
        title="数据库帐号">
      </vxe-column>
    <vxe-column
        field="databasePwd"
        sortable
        title="数据库密码">
      </vxe-column>
    <vxe-column
        field="redisNum"
        sortable
        title="redis库号">
      </vxe-column>
    <vxe-column
        field="adminAccount"
        sortable
        title="管理员账号">
      </vxe-column>
    <vxe-column
        field="adminPas"
        sortable
        title="管理员密码">
      </vxe-column>
    <vxe-column
        field="monthlySales"
        sortable
        title="月销售额">
      </vxe-column>
    <vxe-column
        field="monthlyNum"
        sortable
        title="月销售单数">
      </vxe-column>
    <vxe-column
        field="stratTime"
        sortable
        title="开始时间">
      </vxe-column>
    <vxe-column
        field="endTime"
        sortable
        title="结束时间">
      </vxe-column>
    <vxe-column
        field="deadline"
        sortable
        title="截止时间">
      </vxe-column>
    <vxe-column
        field="codingRules"
        sortable
        title="编码规则">
      </vxe-column>
      <vxe-column
        fixed="right"
        align="center"
        width="200"
        title="操作">
        <template  slot-scope="scope">
          <el-button v-if="hasPermission('wh:basicinformationbf:basicInformationbf:view')" type="text" icon="el-icon-view" size="small" @click="view(scope.row.id)">查看</el-button>
          <el-button v-if="hasPermission('wh:basicinformationbf:basicInformationbf:edit')" type="text" icon="el-icon-edit" size="small" @click="edit(scope.row.id)">修改</el-button>
          <el-button v-if="hasPermission('wh:basicinformationbf:basicInformationbf:del')" type="text"  icon="el-icon-delete" size="small" @click="del(scope.row.id)">删除</el-button>
        </template>
      </vxe-column>
    </vxe-table>
    <vxe-pager
      background
      size="small"
      :current-page="tablePage.currentPage"
      :page-size="tablePage.pageSize"
      :total="tablePage.total"
      :page-sizes="[10, 20, 100, 1000, {label: '全量数据', value: 1000000}]"
      :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'FullJump', 'Sizes', 'Total']"
      @page-change="currentChangeHandle">
    </vxe-pager>
    </div>
    </div>
        <!-- 弹窗, 新增 / 修改 -->
    <BasicInformationbfForm  ref="basicInformationbfForm" @refreshDataList="refreshList"></BasicInformationbfForm>
  </div>
</template>

<script>
  import BasicInformationbfForm from './BasicInformationbfForm'
  import BasicInformationbfService from '@/api/wh/basicinformationbf/BasicInformationbfService'
  export default {
    data () {
      return {
        searchForm: {
        corporateName: '',
        dbName: '',
        adminAccount: ''
        },
        dataList: [],
        tablePage: {
          total: 0,
          currentPage: 1,
          pageSize: 10,
          orders: []
        },
        loading: false
      }
    },
    components: {
      BasicInformationbfForm
    },
    basicInformationbfService: null,
    created () {
      this.basicInformationbfService = new BasicInformationbfService()
    },
    activated () {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.basicInformationbfService.list({
          'current': this.tablePage.currentPage,
          'size': this.tablePage.pageSize,
          'orders': this.tablePage.orders,
          ...this.searchForm
        }).then(({data}) => {
          this.dataList = data.records
          this.tablePage.total = data.total
          this.loading = false
        })
      },
      // 当前页
      currentChangeHandle ({ currentPage, pageSize }) {
        this.tablePage.currentPage = currentPage
        this.tablePage.pageSize = pageSize
        this.refreshList()
      },
      // 排序
      sortChangeHandle (column) {
        this.tablePage.orders = []
        if (column.order != null) {
          this.tablePage.orders.push({column: this.$utils.toLine(column.property), asc: column.order === 'asc'})
        }
        this.refreshList()
      },
      // 新增
      add () {
        this.$refs.basicInformationbfForm.init('add', '')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.basicInformationbfTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.basicInformationbfForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.basicInformationbfForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.basicInformationbfTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.basicInformationbfService.delete(ids).then(({data}) => {
            this.$message.success(data)
            this.refreshList()
            this.loading = false
          })
        })
      },
      // 生成数据
      sc (id) {
        let ids = id || this.$refs.basicInformationbfTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定要生产数据吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.basicInformationbfService.sc(ids).then(({data}) => {
            this.$message.success(data)
            this.refreshList()
            this.loading = false
          })
        })
      },
      // 同步
      tongbu (id) {
        let ids = id || this.$refs.basicInformationbfTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.basicInformationbfService.tongbu(ids).then(({data}) => {
            this.$message.success(data)
            this.refreshList()
            this.loading = false
          })
        })
      },
      // 创建
      cj () {
        this.basicInformationbfService.cj().then(({data}) => {
            this.$message.success(data)
            this.refreshList()
            this.loading = false
          })
        },

      resetSearch () {
        this.$refs.searchForm.resetFields()
        this.refreshList()
      }
    }
  }
</script>
