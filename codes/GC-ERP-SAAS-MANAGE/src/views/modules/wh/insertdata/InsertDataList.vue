<template>
    <div class="page">
      <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
            <!-- 搜索框-->
          <el-form-item>
            <el-button type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
            <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
          </el-form-item>
      </el-form>

     <div class="bg-white top">
        <vxe-toolbar :refresh="{query: refreshList}" export print custom>
          <template #buttons>
            <el-button v-if="hasPermission('wh:insertdata:insertData:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
            <el-button v-if="hasPermission('wh:insertdata:insertData:edit')" type="warning" size="small" icon="el-icon-edit-outline" @click="edit()" :disabled="$refs.insertDataTable && $refs.insertDataTable.getCheckboxRecords().length !== 1" plain>修改</el-button>
            <el-button v-if="hasPermission('wh:insertdata:insertData:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()" :disabled="$refs.insertDataTable && $refs.insertDataTable.getCheckboxRecords().length === 0" plain>删除</el-button>
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
            ref="insertDataTable"
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
        field="stratTime"
        sortable
        title="开始时间">
            <template slot-scope="scope">
              <el-link  type="primary" :underline="false" v-if="hasPermission('wh:insertdata:insertData:edit')" @click="edit(scope.row.id)">{{scope.row.stratTime}}</el-link>
              <el-link  type="primary" :underline="false" v-else-if="hasPermission('wh:insertdata:insertData:view')"  @click="view(scope.row.id)">{{scope.row.stratTime}}</el-link>
              <span v-else>{{scope.row.stratTime}}</span>
            </template>
      </vxe-column>
    <vxe-column
        field="endTime"
        sortable
        title="结束时间">
      </vxe-column>
    <vxe-column
        field="productYearMax"
        sortable
        title="销售年度最大总额">
      </vxe-column>
    <vxe-column
        field="productYearMin"
        sortable
        title="销售年度最小总额">
      </vxe-column>
    <vxe-column
        field="purYearMax"
        sortable
        title="采购年度最大总额">
      </vxe-column>
    <vxe-column
        field="purYearMin"
        sortable
        title="采购年度最小总额">
      </vxe-column>
    <vxe-column
        field="productMax"
        sortable
        title="销售月最大总额">
      </vxe-column>
    <vxe-column
        field="productMin"
        sortable
        title="销售月最小总额">
      </vxe-column>
    <vxe-column
        field="purMax"
        sortable
        title="采购月最大总额">
      </vxe-column>
    <vxe-column
        field="purMin"
        sortable
        title="采购月最小总额">
      </vxe-column>
    <vxe-column
        field="producMonthCount"
        sortable
        title="月销售单数">
      </vxe-column>
    <vxe-column
        field="purMonthCount"
        sortable
        title="月采购单数">
      </vxe-column>
    <vxe-column
        field="producMonthDelCount"
        sortable
        title="月销售详情产品数">
      </vxe-column>
    <vxe-column
        field="purMonthDelCount"
        sortable
        title="月采购详情产品数">
      </vxe-column>
      <vxe-column
        fixed="right"
        align="center"
        width="200"
        title="操作">
        <template  slot-scope="scope">
          <el-button v-if="hasPermission('wh:insertdata:insertData:view')" type="text" icon="el-icon-view" size="small" @click="view(scope.row.id)">查看</el-button>
          <el-button v-if="hasPermission('wh:insertdata:insertData:edit')" type="text" icon="el-icon-edit" size="small" @click="edit(scope.row.id)">修改</el-button>
          <el-button v-if="hasPermission('wh:insertdata:insertData:del')" type="text"  icon="el-icon-delete" size="small" @click="del(scope.row.id)">删除</el-button>
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
    <InsertDataForm  ref="insertDataForm" @refreshDataList="refreshList"></InsertDataForm>
  </div>
</template>

<script>
  import InsertDataForm from './InsertDataForm'
  import InsertDataService from '@/api/wh/insertdata/InsertDataService'
  export default {
    data () {
      return {
        searchForm: {
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
      InsertDataForm
    },
    insertDataService: null,
    created () {
      this.insertDataService = new InsertDataService()
    },
    activated () {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.insertDataService.list({
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
        this.$refs.insertDataForm.init('add', '')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.insertDataTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.insertDataForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.insertDataForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.insertDataTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.insertDataService.delete(ids).then(({data}) => {
            this.$message.success(data)
            this.refreshList()
            this.loading = false
          })
        })
      },
      resetSearch () {
        this.$refs.searchForm.resetFields()
        this.refreshList()
      }
    }
  }
</script>
