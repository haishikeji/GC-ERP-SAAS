<template>
    <div class="page">
      <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
            <!-- 搜索框-->
         <el-form-item prop="inventory.id">
            <GridSelect
                    title="选择存货id"
                    placeholder="请选择存货id"
                    labelName = 'inventoryName'
                    labelValue = 'id'
                    :value = "searchForm.inventory.id"
                    :limit="1"
                    size="small"
                    @getValue='(value) => {searchForm.inventory.id=value}'
                    :columns="[
                    {
                      prop: 'id',
                      label: 'id'
                    },
                    {
                      prop: 'inventoryName',
                      label: '存货名称'
                    },
                    {
                      prop: 'inventoryCode',
                      label: '存货编码'
                    },
                    {
                      prop: 'model',
                      label: '规格型号'
                    },
                    {
                      prop: 'nventoryAttributeValue',
                      label: '存货属性值'
                    },
                    {
                      prop: 'singalUnit',
                      label: '计量单位'
                    },
                    {
                      prop: 'notaxPrice',
                      label: '未税单价'
                    },
                    {
                      prop: 'taxincludedPrice',
                      label: '含税单价'
                    },
                    {
                      prop: 'unitOfMeasurement',
                      label: '在库库存'
                    },
                    {
                      prop: 'inventoryAttribute',
                      label: '可用库存'
                    },
                    {
                      prop: 'lockStock',
                      label: '锁定库存'
                    }
                    ]"
                    :searchs="[
                    {
                      prop: 'inventoryName',
                      label: '存货名称'
                    },
                    {
                      prop: 'inventoryCode',
                      label: '存货编码'
                    }
                    ]"
                    dataListUrl="/basic/inventorytype/inventory/list"
                    queryEntityUrl="/basic/inventorytype/inventory/queryById">
                  </GridSelect>
         </el-form-item>
         <el-form-item prop="warehouse.id">
         </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
            <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
          </el-form-item>
      </el-form>

     <div class="bg-white top">
        <vxe-toolbar :refresh="{query: refreshList}" export print custom>
          <template #buttons>
            <el-button v-if="hasPermission('basic:inventorywarehouse:inventoryWarehouse:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
            <el-button v-if="hasPermission('basic:inventorywarehouse:inventoryWarehouse:edit')" type="warning" size="small" icon="el-icon-edit-outline" @click="edit()" :disabled="$refs.inventoryWarehouseTable && $refs.inventoryWarehouseTable.getCheckboxRecords().length !== 1" plain>修改</el-button>
            <el-button v-if="hasPermission('basic:inventorywarehouse:inventoryWarehouse:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()" :disabled="$refs.inventoryWarehouseTable && $refs.inventoryWarehouseTable.getCheckboxRecords().length === 0" plain>删除</el-button>
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
            ref="inventoryWarehouseTable"
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
        field="inventory.inventoryName"
        sortable
        title="存货id">
            <template slot-scope="scope">
              <el-link  type="primary" :underline="false" v-if="hasPermission('basic:inventorywarehouse:inventoryWarehouse:edit')" @click="edit(scope.row.id)">{{scope.row.inventory && scope.row.inventory.inventoryName}}</el-link>
              <el-link  type="primary" :underline="false" v-else-if="hasPermission('basic:inventorywarehouse:inventoryWarehouse:view')"  @click="view(scope.row.id)">{{scope.row.inventory &&  scope.row.inventory.inventoryName}}</el-link>
              <span v-else>{{scope.row.inventory && scope.row.inventory.inventoryName}}</span>
            </template>
      </vxe-column>
    <vxe-column
        field="warehouse.id"
        sortable
        title="仓库id">
      </vxe-column>
    <vxe-column
        field="minimumInventory"
        sortable
        title="最小库存">
      </vxe-column>
    <vxe-column
        field="maximumInventory"
        sortable
        title="最大库存">
      </vxe-column>
    <vxe-column
        field="inventoryInStock"
        sortable
        title="在库库存">
      </vxe-column>
    <vxe-column
        field="availableStock"
        sortable
        title="可用库存">
      </vxe-column>
    <vxe-column
        field="lockStock"
        sortable
        title="锁定库存">
      </vxe-column>
      <vxe-column
        fixed="right"
        align="center"
        width="200"
        title="操作">
        <template  slot-scope="scope">
          <el-button v-if="hasPermission('basic:inventorywarehouse:inventoryWarehouse:view')" type="text" icon="el-icon-view" size="small" @click="view(scope.row.id)">查看</el-button>
          <el-button v-if="hasPermission('basic:inventorywarehouse:inventoryWarehouse:edit')" type="text" icon="el-icon-edit" size="small" @click="edit(scope.row.id)">修改</el-button>
          <el-button v-if="hasPermission('basic:inventorywarehouse:inventoryWarehouse:del')" type="text"  icon="el-icon-delete" size="small" @click="del(scope.row.id)">删除</el-button>
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
    <InventoryWarehouseForm  ref="inventoryWarehouseForm" @refreshDataList="refreshList"></InventoryWarehouseForm>
  </div>
</template>

<script>
  import InventoryWarehouseForm from './InventoryWarehouseForm'
  import InventoryWarehouseService from '@/api/basic/inventorywarehouse/InventoryWarehouseService'
  import GridSelect from '@/components/gridSelect'
  import InventorySelect from '@/components/inventorySelect'
  export default {
    data () {
      return {
        searchForm: {
          inventory: {
            id: ''
          },
          warehouse: {
            id: ''
          }
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
      GridSelect,
      InventorySelect ,
      InventoryWarehouseForm
    },
    inventoryWarehouseService: null,
    created () {
      this.inventoryWarehouseService = new InventoryWarehouseService()
    },
    activated () {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.inventoryWarehouseService.list({
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
        this.$refs.inventoryWarehouseForm.init('add', '')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.inventoryWarehouseTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.inventoryWarehouseForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.inventoryWarehouseForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.inventoryWarehouseTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.inventoryWarehouseService.delete(ids).then(({data}) => {
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

