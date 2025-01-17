<template>
    <div class="page">
      <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
          <!-- 搜索框-->
         <el-form-item prop="inventoryName">
                <el-input size="small" v-model="searchForm.inventoryName" placeholder="存货名称" clearable></el-input>
         </el-form-item>
         <el-form-item prop="model">
                <el-input size="small" v-model="searchForm.model" placeholder="规格型号" clearable></el-input>
         </el-form-item>
         <el-form-item prop="sjfw">
               <el-date-picker v-model="searchForm.sjfw" type="daterange" size="small" align="right" value-format="yyyy-MM-dd"
                  unlink-panels range-separator="~" start-placeholder="出库开始日期" end-placeholder="出库结束日期">
                </el-date-picker>
         </el-form-item>
          <el-form-item prop="salesorderCode">
            <el-input size="small" v-model="searchForm.salesorderCode" placeholder="销售订单" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
            <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
          </el-form-item>
      </el-form>

     <div class="bg-white top">
        <vxe-toolbar :refresh="{query: refreshList}" export custom>
          <template #buttons></template>
          <template #tools>
            <vxe-button type="button" circle icon="vxe-icon--print" class="tool-btn" @click="customPrint"></vxe-button>
          </template>
        </vxe-toolbar>
        <div style="height: calc(100% - 80px);">
        <vxe-table border="inner" auto-resize resizable height="auto" :loading="loading" size="small" ref="productDeliveryTable"
            show-header-overflow show-overflow highlight-hover-row :menu-config="{}" :print-config="{}"
            :import-config="{}" :export-config="{}"
            :expand-config="{accordion: true, lazy: true,loadMethod:detail}" :data="dataList"
            :checkbox-config="{}" show-footer :footer-method="getFooterData" header-align="center">
            <vxe-column type="seq" width="50" title="序号" align="center"></vxe-column>
            <vxe-column type="checkbox"  width="40px"></vxe-column>
            <vxe-column type="expand" width="50" title="详情" align="center">
              <template #content="{ row }">
                <el-tabs>
                  <el-tab-pane label="出库单记录">
                      <el-table size="small" :data="row.deliveryDetailsDTOList" style="width: 100%">
                        <el-table-column prop="deliveryOrderNo" label="出库单编号"> </el-table-column>
                        <el-table-column label="出库仓库">
                          <template slot-scope="scope">
                            {{scope.row.warehouseName || scope.row.warehouseNameDefault}}
                          </template>
                        </el-table-column>
                        <el-table-column prop="outboundQuantity" label="出库数量"> </el-table-column>
                        <el-table-column prop="singalUnit" header-align="center" align="center" show-overflow-tooltip label="计量单位">
                          <template slot-scope="scope">
                            {{ $dictUtils.getDictLabel("sx_metering_unit", scope.row.singalUnit, '-') }}
                          </template>
                        </el-table-column>
                        <el-table-column prop="issueType" label="出库类型">
                          <template slot-scope="scope">
                                {{ $dictUtils.getDictLabel("product_issue_type", scope.row.issueType, '-') }}
                          </template>
                        </el-table-column>
                        <el-table-column prop="remark" label="备注"> </el-table-column>
                        <el-table-column prop="salesorderCode" label="销售订单"> </el-table-column>
                        <el-table-column prop="createDate" label="出库时间"> </el-table-column>
                        <el-table-column prop="createName" label="出库人"> </el-table-column>
                      </el-table>
                  </el-tab-pane>
                </el-tabs>
              </template>
            </vxe-column>
      <vxe-column field="inventoryCode" sortable title="存货编号"></vxe-column>
      <vxe-column field="inventoryName" sortable title="存货名称"></vxe-column>
      <vxe-column field="ckhj" sortable title="出库合计">
        <template slot-scope="scope">
          <b>{{scope.row.ckhj}}</b>
        </template>
      </vxe-column>
      <vxe-column field="model" sortable title="规格型号"></vxe-column>
      <vxe-column field="singalUnit" sortable title="计量单位">
        <template slot-scope="scope">
          {{ $dictUtils.getDictLabel("sx_metering_unit", scope.row.singalUnit, '-') }}
        </template>
      </vxe-column>
      <vxe-column field="nventoryAttributeValue" sortable title="存货属性"></vxe-column>
      <vxe-column field="inventoryInStock" sortable title="在库库存"></vxe-column>
      <vxe-column field="availableStock" sortable title="可用库存"></vxe-column>

      <vxe-column field="createBy.name" sortable title="创建人"> </vxe-column>
      <!-- <vxe-column field="createDate" sortable align="center" title="创建时间"> </vxe-column> -->
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
  </div>
</template>

<script>
  import DeliveryReportService from '@/api/report/delivery/DeliveryReportService'
  export default {
    data () {
      // 打印样式
      const printStyle = `
        .title {
          text-align: center;
        }
        .my-list-row {
          display: inline-block;
          width: 100%;
        }
        .my-list-row div {
          font-size: 16px,
          float: left;
          width: 33.33%;
          height: 28px;
          line-height: 28px;
        }
        .div-float-left {
          float: left !important;
        }
        .div-float-right {
          float: right !important;
          text-align: right;
        }
        .my-top {
          margin-bottom: 5px;
        }
      `;

      var date = new Date();
      var month = date.getMonth() + 1;
      var strDate = date.getDate();
      var currentDate = date.getFullYear() + '-' + month + '-' + strDate;

      // 打印顶部内容模板
      const topHtml = `
        <h1 class="title">产品出库明细</h1>
        <div class="my-top">
          <div class="my-list-row">
            <div class="div-float-left">打印人：` + this.$store.state.user.name + `</div>
            <div class="div-float-right">打印日期：` + currentDate + `</div>
          </div>
        </div>
      `;

      return {
        searchForm: {
          inventoryName: '',
          model: '',
          salesorderCode: '',
          sjfw: [],
        },
        dataList: [],
        tablePage: {
          total: 0,
          currentPage: 1,
          pageSize: 10,
          orders: []
        },
        loading: false,
        printStyle,
        topHtml
      }
    },
    components: {

    },
    deliveryReportService: null,
    created () {
      this.deliveryReportService = new DeliveryReportService();
      this.defaultDate();
    },
    activated () {
      this.refreshList()
    },
    methods: {
      getFooterData ({ columns, data }) {
        let deliveryTotal = 0;
        data.map((item, index) => {
          deliveryTotal += parseInt(item.ckhj);
        })
        return [['合计', '', '', '', '', deliveryTotal]]
      },
      // 获取数据列表
      refreshList () {
        if (this.searchForm.sjfw.length > 0) {
          this.searchForm.sjfw.forEach((item, index) => {
            if (index == 0) {
              this.searchForm.delivery_begin = item + ' 00:00:00';
            } else if (index == 1) {
              this.searchForm.delivery_end = item + ' 23:59:59';
            }
          });
        }

        this.loading = true
        this.deliveryReportService.list({
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
      //设置当前月
      defaultDate(){
        let date = new Date()
        let year = date.getFullYear().toString();
        let month = date.getMonth()+1 < 10 ? '0' + (date.getMonth()+1).toString():(date.getMonth()+1).toString();
        let da = date.getDate() < 10 ? '0' + date.getDate().toString():date.getDate().toString();
        let end = year + '-' + month + '-' + da;
        let beg = year + '-' + month + '-01';
        this.searchForm.sjfw = [beg, end] //将值设置给插件绑定的数据
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
      // 查看详情
      detail ({row}) {
        return new Promise(resolve => {
          this.deliveryReportService.queryById({'inventoryId' : row.inventoryId, 'delivery_begin' : this.searchForm.delivery_begin, 'delivery_end' : this.searchForm.delivery_end}).then(({data}) => {
            data.forEach((item, index) => {
              item["warehouseNameDefault"] = row.warehouseNameDefault;
            })

            this.dataList.forEach((item, index) => {
              if (item.inventoryId === row.inventoryId) {
                item.deliveryDetailsDTOList = data;
              }
            })
            resolve()
          })
        })
      },
      resetSearch () {
        this.$refs.searchForm.resetFields()
        this.refreshList()
      },
      customPrint () {
        this.$refs.productDeliveryTable.print({
          sheetName: '产品出库明细打印',
          style: this.printStyle,
          columns: [
            {type: 'seq'},
            {field: 'inventoryCode'},
            {field: 'inventoryName'},
            {field: 'ckhj'},
            {field: 'model'},
            {field: 'singalUnit'},
            {field: 'nventoryAttributeValue'},
            {field: 'inventoryInStock'},
            {field: 'availableStock'},
            {field: 'createBy.name'},
          ],
          beforePrintMethod: ({content}) => {
            return this.topHtml + content;
          }
        })
      }
    }
  }
</script>

<style scoped lang = "less">
  .tool-btn {
    margin-right: 12px;
  }
</style>