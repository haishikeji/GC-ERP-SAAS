<template>
  <div class="page">
    <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm"
      @keyup.enter.native="refreshList()" @submit.native.prevent>
      <!-- 搜索框-->
      <el-form-item prop="customerId">
        <el-input size="small" v-model="searchForm.customerId" placeholder="客户名称" clearable></el-input>
      </el-form-item>
      <el-form-item prop="serverType">
        <el-select v-model="searchForm.serverType" placeholder="请选择类型" size="small" style="width: 100%;">
          <el-option v-for="item in $dictUtils.getDictList('cus_server')" :key="item.value" :label="item.label"
            :value="item.value"> </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
        <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
      </el-form-item>
    </el-form>

    <div class="bg-white top">
      <vxe-toolbar :refresh="{ query: refreshList }" export print custom>
        <template #buttons>
          <el-button v-if="hasPermission('customer:customerServer:add')" type="primary" size="small" icon="el-icon-plus"
            @click="add()">新建</el-button>
          <el-button v-if="hasPermission('customer:customerServer:edit')" type="warning" size="small"
            icon="el-icon-edit-outline" @click="edit()"
            :disabled="$refs.customerServerTable && $refs.customerServerTable.getCheckboxRecords().length !== 1"
            plain>修改</el-button>
          <el-button v-if="hasPermission('customer:customerServer:del')" type="danger" size="small" icon="el-icon-delete"
            @click="del()"
            :disabled="$refs.customerServerTable && $refs.customerServerTable.getCheckboxRecords().length === 0"
            plain>删除</el-button>
        </template>
      </vxe-toolbar>
      <div style="height: calc(100% - 80px);">
        <vxe-table border="inner" auto-resize resizable height="auto" :loading="loading" size="small"
          ref="customerServerTable" show-header-overflow show-overflow highlight-hover-row :menu-config="{}"
          :print-config="{}" :import-config="{}" :export-config="{}" @sort-change="sortChangeHandle"
          :sort-config="{ remote: true }" :expand-config="{ accordion: true, lazy: true, loadMethod: detail }"
          :data="dataList" :checkbox-config="{}">
          <vxe-column type="seq" width="40"></vxe-column>
          <vxe-column type="checkbox" width="40px"></vxe-column>
          <vxe-column field="salesorderId" sortable title="销售订单编号">
          </vxe-column>
          <vxe-column field="customerId" sortable title="客户">
          </vxe-column>
          <vxe-column field="serverType" sortable title="类型">
            <template slot-scope="scope">
              {{ $dictUtils.getDictLabel("cus_server", scope.row.serverType, '-') }}
            </template>
          </vxe-column>
          <vxe-column field="serverNotes" sortable title="信息概要">
          </vxe-column>
          <vxe-column field="serverState" sortable title="状态">
            <template slot-scope="scope">
              {{ $dictUtils.getDictLabel("cus_server_state", scope.row.serverState, '-') }}
            </template>
          </vxe-column>
          <vxe-column fixed="right" align="center" width="250" title="操作">
            <template slot-scope="scope">
              <el-button v-if="hasPermission('customer:customerServer:view')" type="text" icon="el-icon-view" size="small"
                @click="view(scope.row.id)">查看</el-button>
              <el-button v-if="hasPermission('customer:customerServer:edit')&& (scope.row.serverState == '1')" type="text" icon="el-icon-edit" size="small"
                @click="edit(scope.row.id)">修改</el-button>
              <el-button v-if="hasPermission('customer:customerServer:del')&& (scope.row.serverState == '1')" type="text" icon="el-icon-delete"
                size="small" @click="del(scope.row.id)">删除</el-button>
              <el-button v-if="hasPermission('customer:customerServer:stat') && (scope.row.serverState == '1')"
                type="text" icon="el-icon-user" size="small" @click="accomplish(scope.row.id)">处理</el-button>
            </template>
          </vxe-column>
        </vxe-table>
        <vxe-pager background size="small" :current-page="tablePage.currentPage" :page-size="tablePage.pageSize"
          :total="tablePage.total" :page-sizes="[10, 20, 100, 1000, { label: '全量数据', value: 1000000 }]"
          :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'FullJump', 'Sizes', 'Total']"
          @page-change="currentChangeHandle">
        </vxe-pager>
      </div>
    </div>
    <!-- 弹窗, 新增 / 修改 -->
    <CustomerServerForm ref="customerServerForm" @refreshDataList="refreshList"></CustomerServerForm>
  </div>
</template>

<script>
import CustomerServerForm from './CustomerServerForm'
import CustomerServerService from '@/api/customer/CustomerServerService'
export default {
  data() {
    return {
      searchForm: {
        serverType: '',
        customerId: ''
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
    CustomerServerForm
  },
  customerServerService: null,
  created() {
    this.customerServerService = new CustomerServerService()
  },
  activated() {
    this.refreshList()
  },
  methods: {
    // 获取数据列表
    refreshList() {
      this.loading = true
      this.customerServerService.list({
        'current': this.tablePage.currentPage,
        'size': this.tablePage.pageSize,
        'orders': this.tablePage.orders,
        ...this.searchForm
      }).then(({ data }) => {
        this.dataList = data.records
        this.tablePage.total = data.total
        this.loading = false
      })
    },
    // 当前页
    currentChangeHandle({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      this.refreshList()
    },
    // 排序
    sortChangeHandle(column) {
      this.tablePage.orders = []
      if (column.order != null) {
        this.tablePage.orders.push({ column: this.$utils.toLine(column.property), asc: column.order === 'asc' })
      }
      this.refreshList()
    },
    // 新增
    add() {
      this.$refs.customerServerForm.init('add', '')
    },
    // 修改
    edit(id) {
      id = id || this.$refs.customerServerTable.getCheckboxRecords().map(item => {
        return item.id
      })[0]
      this.$refs.customerServerForm.init('edit', id)
    },
    // 查看
    view(id) {
      this.$refs.customerServerForm.init('view', id)
    },
    // 删除
    del(id) {
      let ids = id || this.$refs.customerServerTable.getCheckboxRecords().map(item => {
        return item.id
      }).join(',')
      this.$confirm(`确定删除所选项吗?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true
        this.customerServerService.delete(ids).then(({ data }) => {
          this.$message.success(data)
          this.refreshList()
          this.loading = false
        })
      })
    },
    // 查看详情
    detail({ row }) {
      return new Promise(resolve => {
        this.customerServerService.queryById(row.id).then(({ data }) => {
          this.dataList.forEach((item, index) => {
            if (item.id === row.id) {
            }
          })
          resolve()
        })
      })
    },
    accomplish(id) {
      console.info(id)
      this.customerServerService.stat(id).then(({
        data
      }) => {
        this.$message.success(data)
        this.$emit('refreshDataList')
        this.refreshList()
      }).catch(() => {
        this.loading = false
      })
    },
    resetSearch() {
      this.$refs.searchForm.resetFields()
      this.refreshList()
    }
  }
}
</script>

