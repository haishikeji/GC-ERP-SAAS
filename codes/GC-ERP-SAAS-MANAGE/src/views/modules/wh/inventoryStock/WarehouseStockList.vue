<template>
  <div class="page">
    <!-- <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm"
      @keyup.enter.native="refreshList()" @submit.native.prevent>
      <el-form-item label="存货类别" prop="inventoryTypeId">
        <SelectTree ref="inventoryTypeId" :props="{
                    value: 'id',             // ID字段名
                    label: 'name',         // 显示名称
                    children: 'children'    // 子级字段名
                  }" url="/basic/inventorytype/inventoryType/treeData" :value="searchForm.inventoryTypeId"
          :clearable="true" :accordion="true" @getValue="(value) => {searchForm.inventoryTypeId=value}" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
        <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
      </el-form-item>
    </el-form> -->
    <div class="bg-white top">
      <vxe-toolbar :refresh="{query: refreshList}" export print custom>
        <template #buttons></template>
      </vxe-toolbar>
      <div style="height: calc(100% - 80px);">
        <vxe-table border="inner" auto-resize resizable height="auto" :loading="loading" size="small"
          ref="materialIntoTable" show-header-overflow show-overflow highlight-hover-row :menu-config="{}"
          :print-config="{}" :import-config="{}" :export-config="{}" @sort-change="sortChangeHandle"
          :sort-config="{remote:true}" :expand-config="{accordion: true, lazy: true,loadMethod:detail}" :data="dataList"
          :checkbox-config="{}">
          <vxe-column type="seq" width="40"></vxe-column>
          <vxe-column type="expand" width="80">
            <template #content="{ row }">
              <el-tabs>
                <el-tab-pane label="仓库库存详情表">
                  <el-table size="small" :data="row.WarehouseStockDetailDTO" style="width: 100%" show-summary>
                    <el-table-column prop="inventory.inventoryName" label="存货">
                    </el-table-column>
                    <el-table-column prop="inventoryType" label="存货类型">
                    </el-table-column>
                    <el-table-column prop="inventory.inventoryCode" label="存货编码">
                    </el-table-column>
                    <el-table-column prop="inventory.model" label="规格型号">
                    </el-table-column>
                    <el-table-column prop="inventory.nventoryAttributeValue" label="存货属性值">
                    </el-table-column>
                    <el-table-column prop="inventory.singalUnit" label="计量单位">
                      <template slot-scope="scope">
                        {{ $dictUtils.getDictLabel("sx_metering_unit", scope.row.inventory.singalUnit, '-') }}
                      </template>
                    </el-table-column>
                    <el-table-column prop="inventory.avgPrice" label="存货均价(元)">
                    </el-table-column>
                    <el-table-column prop="inventoryInStock" label="在库库存">
                    </el-table-column>
                    <el-table-column prop="availableStock" label="可用库存">
                    </el-table-column>
                    <el-table-column prop="lockStock" label="锁定库存">
                    </el-table-column>
                    <el-table-column prop="totalValue" label="库存总价(万元)">
                    </el-table-column>
                  </el-table>
                </el-tab-pane>
              </el-tabs>
            </template>
          </vxe-column>
          <vxe-column field="warehouse" sortable title="仓库">
          </vxe-column>
          <vxe-column field="inventoryInStock" sortable title="在库库存">
          </vxe-column>
          <vxe-column field="availableStock" sortable title="可用库存">
          </vxe-column>
          <vxe-column field="lockStock" sortable title="锁定库存">
          </vxe-column>
          <vxe-column field="totalValue" sortable title="库存总价(万元)">
          </vxe-column>
        </vxe-table>
        <vxe-pager background size="small" :current-page="tablePage.currentPage" :page-size="tablePage.pageSize"
          :total="tablePage.total" :page-sizes="[10, 20, 100, 1000, {label: '全量数据', value: 1000000}]"
          :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'FullJump', 'Sizes', 'Total']"
          @page-change="currentChangeHandle">
        </vxe-pager>
      </div>
    </div>
  </div>
</template>

<script>
  import InventoryStockService from '@/api/wh/inventoryStock/InventoryStockService'
  import SelectTree from '@/components/treeSelect/treeSelect.vue'
  export default {
    data() {
      return {
        searchForm: {
          inventoryTypeId: ''
        },
        dataList: [],
        tablePage: {
          total: 0,
          currentPage: 1,
          pageSize: 1000000,
          orders: []
        },
        loading: false
      }
    },
    components: {
      SelectTree
    },
    inventoryStockService: null,
    created() {
      this.inventoryStockService = new InventoryStockService()
    },
    activated() {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList() {
        this.loading = true
        this.inventoryStockService.list1({
          'current': this.tablePage.currentPage,
          'size': this.tablePage.pageSize,
          'orders': this.tablePage.orders,
          ...this.searchForm
        }).then(({
          data
        }) => {
          this.dataList = data.records
          this.tablePage.total = data.total
          this.loading = false
        })
      },
      // 当前页
      currentChangeHandle({
        currentPage,
        pageSize
      }) {
        this.tablePage.currentPage = currentPage
        this.tablePage.pageSize = pageSize
        this.refreshList()
      },
      // 排序
      sortChangeHandle(column) {
        this.tablePage.orders = []
        if (column.order != null) {
          this.tablePage.orders.push({
            column: this.$utils.toLine(column.property),
            asc: column.order === 'asc'
          })
        }
        this.refreshList()
      },
      // 查看详情
      detail({
        row
      }) {
        return new Promise(resolve => {
          this.inventoryStockService.queryById1(row.warehouseId).then(({
            data
          }) => {
            this.dataList.forEach((item, index) => {
              if (item.warehouseId === row.warehouseId) {
                item.WarehouseStockDetailDTO = data
              }
            })
            resolve()
          })
        })
      },
      resetSearch() {
        this.$refs.searchForm.resetFields()
        this.refreshList()
      }
    }
  }
</script>
