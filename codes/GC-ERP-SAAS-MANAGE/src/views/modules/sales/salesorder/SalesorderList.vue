<template>
  <div class="page">
    <el-form
      size="small"
      :inline="true"
      class="query-form"
      ref="searchForm"
      :model="searchForm"
      @keyup.enter.native="refreshList()"
      @submit.native.prevent
    >
      <!-- 搜索框-->
      <el-form-item prop="salesorderCode">
        <el-input
          size="small"
          v-model="searchForm.salesorderCode"
          placeholder="销售订单编号"
          clearable
        ></el-input>
      </el-form-item>
      <!-- <el-form-item prop="salesorderBatchnumber">
        <el-input size="small" v-model="searchForm.salesorderBatchnumber" placeholder="销售订单批号" clearable></el-input>
      </el-form-item> -->
      <el-form-item prop="salesorderState">
        <el-select
          v-model="searchForm.salesorderState"
          placeholder="请选择订单状态"
          size="small"
          style="width: 100%"
        >
          <el-option
            v-for="item in $dictUtils.getDictList('salesorder_state')"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          @click="refreshList()"
          size="small"
          icon="el-icon-search"
          >查询</el-button
        >
        <el-button
          @click="resetSearch()"
          size="small"
          icon="el-icon-refresh-right"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <div class="bg-white top">
      <vxe-toolbar :refresh="{ query: refreshList }" import export print custom>
        <template #buttons>
          <el-button
            v-if="hasPermission('sales:salesorder:salesorder:add')"
            type="primary"
            size="small"
            icon="el-icon-plus"
            @click="add()"
            >新建</el-button
          >
          <el-button
            v-if="hasPermission('sales:salesorder:salesorder:edit')"
            type="warning"
            size="small"
            icon="el-icon-edit-outline"
            @click="edit()"
            :disabled="
              $refs.salesorderTable &&
              $refs.salesorderTable.getCheckboxRecords().length !== 1
            "
            plain
            >修改
          </el-button>
          <!-- <el-button v-if="hasPermission('sales:salesorder:salesorder:del')" type="danger" size="small"
            icon="el-icon-delete" @click="del()"
            :disabled="$refs.salesorderTable && $refs.salesorderTable.getCheckboxRecords().length === 0" plain>删除
          </el-button> -->
          <el-button
            v-if="hasPermission('sales:salesorder:salesorder:import:template')"
            type="default"
            @click="downloadTpl()"
            size="small"
            >下载模板</el-button
          >
          <el-button
            v-if="
              hasPermission('sales:salesorder:salesorder:createSalesOutDoc')
            "
            type="primary"
            size="small"
            icon="el-icon-s-unfold"
            @click="createSalesOutDoc()"
            :disabled="
              $refs.salesorderTable &&
              $refs.salesorderTable.getCheckboxRecords().length !== 1
            "
            plain
            >生成销售出库单
          </el-button>
          <el-button
            v-if="
              hasPermission('sales:salesorder:salesorder:createProducePlan')
            "
            type="primary"
            size="small"
            icon="el-icon-s-unfold"
            @click="createProducePlan()"
            :disabled="
              $refs.salesorderTable &&
              $refs.salesorderTable.getCheckboxRecords().length !== 1
            "
            plain
            >生成生产计划单
          </el-button>
          <el-button
            v-if="
              hasPermission('sales:salesorder:salesorder:createMaterialPlan')
            "
            type="primary"
            size="small"
            icon="el-icon-s-unfold"
            @click="createMaterialPlan()"
            :disabled="
              $refs.salesorderTable &&
              $refs.salesorderTable.getCheckboxRecords().length !== 1
            "
            plain
            >生成物料计划单
          </el-button>
        </template>
      </vxe-toolbar>
      <div style="height: calc(100% - 80px)">
        <vxe-table
          border="inner"
          auto-resize
          resizable
          height="auto"
          :loading="loading"
          size="small"
          ref="salesorderTable"
          show-header-overflow
          show-overflow
          highlight-hover-row
          :menu-config="{}"
          :print-config="{}"
          :import-config="{
            importMethod: importMethod,
            types: ['csv', 'xls', 'xlsx'],
            remote: true,
          }"
          :export-config="{
            remote: true,
            filename: `用户数据${moment(new Date()).format('YYYY-MM-DD')}`,
            sheetName: '用户数据',
            exportMethod: exportMethod,
            types: ['xlsx'],
            modes: ['current', 'selected', 'all'],
          }"
          @sort-change="sortChangeHandle"
          :sort-config="{ remote: true, showIcon: false }"
          :expand-config="{ accordion: true, lazy: true, loadMethod: detail }"
          :data="dataList"
          :checkbox-config="{}"
        >
          <vxe-column type="seq" width="40"></vxe-column>
          <vxe-column type="checkbox" width="40px"></vxe-column>
          <vxe-column type="expand" width="80">
            <template #content="{ row }">
              <el-tabs>
                <el-tab-pane label="销售订单详情">
                  <el-table
                    size="small"
                    :data="row.salesorderdetailsDTOList"
                    style="width: 100%"
                  >
                    <el-table-column prop="salesorderBatchnumber" label="批号">
                    </el-table-column>
                    <el-table-column
                      prop="inventory.inventoryName"
                      label="存货名称"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="inventory.inventoryCode"
                      label="存货编码"
                    >
                    </el-table-column>
                    <el-table-column prop="inventory.model" label="规格型号">
                    </el-table-column>
                    <el-table-column
                      prop="inventory.singalUnit"
                      label="计量单位"
                    >
                      <template slot-scope="scope">
                        {{
                          $dictUtils.getDictLabel(
                            "sx_metering_unit",
                            scope.row.inventory.singalUnit,
                            "-"
                          )
                        }}
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="inventory.inventoryWarehouse.inventoryInStock"
                      label="在库库存"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="inventory.inventoryWarehouse.availableStock"
                      label="可用库存"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="inventory.nventoryAttributeValue"
                      label="存货属性"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="defaultWareHouse.name"
                      label="所属仓库"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="salesorderNum"
                      show-overflow-tooltip
                      label="订单数量"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="planProduceNum"
                      show-overflow-tooltip
                      label="计划生产数量"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="lockInventory"
                      show-overflow-tooltip
                      label="锁定库存数量"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="issuedNum"
                      show-overflow-tooltip
                      label="已出库数量"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="notaxiUnitPrice"
                      show-overflow-tooltip
                      label="未税单价(元)"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="notaxiTotalPrice"
                      show-overflow-tooltip
                      label="未税总价(元)"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="unitPrice"
                      show-overflow-tooltip
                      label="含税单价(元)"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="totalPrice"
                      show-overflow-tooltip
                      label="含税总价(元)"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="deliveryDate"
                      show-overflow-tooltip
                      label="交期"
                    >
                    </el-table-column>
                  </el-table>
                </el-tab-pane>
              </el-tabs>
            </template>
          </vxe-column>
          <vxe-column field="salesorderCode" sortable title="订单编号">
            <template slot-scope="scope">
              <el-link
                type="primary"
                :underline="false"
                v-if="hasPermission('sales:salesorder:salesorder:edit')"
                @click="edit(scope.row.id)"
                >{{ scope.row.salesorderCode }}</el-link
              >
              <el-link
                type="primary"
                :underline="false"
                v-else-if="hasPermission('sales:salesorder:salesorder:view')"
                @click="view(scope.row.id)"
                >{{ scope.row.salesorderCode }}</el-link
              >
              <span v-else>{{ scope.row.salesorderCode }}</span>
            </template>
          </vxe-column>
          <!-- <vxe-column field="salesorderBatchnumber" sortable title="订单批号">
          </vxe-column> -->
          <!-- <vxe-column field="hasSalescontract" sortable title="是否有合同">
            <template slot-scope="scope">
              {{ $dictUtils.getDictLabel("has_salescontract", scope.row.hasSalescontract, '-') }}
            </template>
          </vxe-column> -->
          <vxe-column
            field="salescontract.contractCode"
            sortable
            title="合同编号"
          >
            <template slot-scope="scope">
              {{
                scope.row.salescontract && scope.row.salescontract.contractCode
              }}
            </template>
          </vxe-column>
          <vxe-column field="customer.customerName" sortable title="客户名称">
          </vxe-column>
          <!-- <vxe-column field="totalUntaxedprice" sortable title="未税总价(元)">
          </vxe-column> -->
          <vxe-column
            field="totalpriceIncludingtax"
            sortable
            title="含税总价(元)"
          >
          </vxe-column>
          <vxe-column field="totalNumber" sortable title="总数量"> </vxe-column>
          <vxe-column field="salesorderState" sortable title="状态">
            <template slot-scope="scope">
              {{
                $dictUtils.getDictLabel(
                  "salesorder_state",
                  scope.row.salesorderState,
                  "-"
                )
              }}
            </template>
          </vxe-column>
          <vxe-column field="createBy.name" sortable title="创建人">
          </vxe-column>
          <vxe-column field="createDate" sortable title="创建时间">
          </vxe-column>
          <vxe-column fixed="right" align="center" width="250" title="操作">
            <template slot-scope="scope">
              <el-button
                v-if="
                  hasPermission('sales:salesorder:salesorder:sureSaleOrder') &&
                  scope.row.salesorderState == 0
                "
                type="text"
                size="small"
                @click="sureSaleOrder(scope.row.id)"
                >确认订单</el-button
              >
              <el-button
                v-if="hasPermission('sales:salesorder:salesorder:view')"
                type="text"
                icon="el-icon-view"
                size="small"
                @click="view(scope.row.id)"
                >查看</el-button
              >
              <el-button
                v-if="
                  hasPermission('sales:salesorder:salesorder:edit') &&
                  scope.row.salesorderState == 0
                "
                type="text"
                icon="el-icon-edit"
                size="small"
                @click="edit(scope.row.id)"
                >修改</el-button
              >
              <el-button
                v-if="
                  hasPermission('sales:salesorder:salesorder:del') &&
                  scope.row.salesorderState == 0
                "
                type="text"
                icon="el-icon-delete"
                size="small"
                @click="del(scope.row.id)"
                >删除</el-button
              >
            </template>
          </vxe-column>
        </vxe-table>
        <vxe-pager
          background
          size="small"
          :current-page="tablePage.currentPage"
          :page-size="tablePage.pageSize"
          :total="tablePage.total"
          :page-sizes="[
            10,
            20,
            100,
            1000,
            { label: '全量数据', value: 1000000 },
          ]"
          :layouts="[
            'PrevPage',
            'JumpNumber',
            'NextPage',
            'FullJump',
            'Sizes',
            'Total',
          ]"
          @page-change="currentChangeHandle"
        >
        </vxe-pager>
      </div>
    </div>
    <!-- 弹窗, 新增 / 修改 -->
    <SalesorderForm
      ref="salesorderForm"
      @refreshDataList="refreshList"
    ></SalesorderForm>
  </div>
</template>

<script>
import SalesorderForm from "./SalesorderForm";
import SalesorderService from "@/api/sales/salesorder/SalesorderService";
export default {
  data() {
    return {
      searchForm: {
        salesorderCode: "",
        salesorderBatchnumber: "",
        salesorderState: "",
      },
      dataList: [],
      tablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        orders: [],
      },
      loading: false,
    };
  },
  components: {
    SalesorderForm,
  },
  salesorderService: null,
  created() {
    this.salesorderService = new SalesorderService();
  },
  activated() {
    this.refreshList();
  },
  methods: {
    // 获取数据列表
    refreshList() {
      this.tablePage.orders = [];
      //倒序条件
      let pa1 = {};
      pa1.column = "a.salesorder_state";
      pa1.asc = true;
      this.tablePage.orders.push(pa1);

      let pa = {};
      pa.column = "a.create_date";
      pa.asc = false;
      this.tablePage.orders.push(pa);

      this.loading = true;
      this.salesorderService
        .list({
          current: this.tablePage.currentPage,
          size: this.tablePage.pageSize,
          // 'orders': this.tablePage.orders,
          "orders[0].column": "a.salesorder_state",
          "orders[0].asc": true,
          "orders[1].column": "a.create_date",
          "orders[1].asc": false,
          ...this.searchForm,
        })
        .then(({ data }) => {
          this.dataList = data.records;
          this.tablePage.total = data.total;
          this.loading = false;
        });
    },
    // 当前页
    currentChangeHandle({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.refreshList();
    },
    // 排序
    sortChangeHandle(column) {
      this.tablePage.orders = [];
      if (column.order != null) {
        this.tablePage.orders.push({
          column: this.$utils.toLine(column.property),
          asc: column.order === "asc",
        });
      }
      this.refreshList();
    },
    // 新增
    add() {
      this.$refs.salesorderForm.init("add", "");
    },
    // 修改
    edit(id) {
      id =
        id ||
        this.$refs.salesorderTable.getCheckboxRecords().map((item) => {
          return item.id;
        })[0];
      this.salesorderService.queryById(id).then(({ data }) => {
        if (data.salesorderState > 0) {
          this.$message.error("该销售订单非开立状态，不可编辑修改！");
          return;
        } else {
          this.$refs.salesorderForm.init("edit", id);
        }
      });
    },
    // 查看
    view(id) {
      this.$refs.salesorderForm.init("view", id);
    },
    // 删除
    del(id) {
      let ids =
        id ||
        this.$refs.salesorderTable
          .getCheckboxRecords()
          .map((item) => {
            return item.id;
          })
          .join(",");
      this.$confirm(`确定删除所选项吗?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.loading = true;
        this.salesorderService.delete(ids).then(({ data }) => {
          this.$message.success(data);
          this.refreshList();
          this.loading = false;
        });
      });
    },
    // 查看详情
    detail({ row }) {
      return new Promise((resolve) => {
        this.salesorderService.queryById(row.id).then(({ data }) => {
          this.dataList.forEach((item, index) => {
            if (item.id === row.id) {
              item.salesorderdetailsDTOList = data.salesorderdetailsDTOList;
            }
          });
          resolve();
        });
      });
    },
    resetSearch() {
      this.$refs.searchForm.resetFields();
      this.refreshList();
    },
    //生成销售出库单
    createSalesOutDoc(id) {
      id =
        id ||
        this.$refs.salesorderTable.getCheckboxRecords().map((item) => {
          return item.id;
        })[0];
      this.loading = true;
      this.salesorderService.createSalesOutDoc(id).then(({ data }) => {
        this.$message.success(data);
        this.refreshList();
        this.loading = false;
      });
    },
    //生成生产计划单
    createProducePlan(id) {
      id =
        id ||
        this.$refs.salesorderTable.getCheckboxRecords().map((item) => {
          return item.id;
        })[0];
      this.loading = true;
      this.salesorderService.createProducePlan(id).then(({ data }) => {
        this.$message.success(data);
        this.refreshList();
        this.loading = false;
      });
    },
    //生成物料计划单
    createMaterialPlan(id) {
      id =
        id ||
        this.$refs.salesorderTable.getCheckboxRecords().map((item) => {
          return item.id;
        })[0];
      this.loading = true;
      this.salesorderService.createMaterialPlan(id).then(({ data }) => {
        this.$message.success(data);
        this.refreshList();
        this.loading = false;
      });
    },
    sureSaleOrder(id) {
      id =
        id ||
        this.$refs.salesorderTable.getCheckboxRecords().map((item) => {
          return item.id;
        })[0];
      this.loading = true;
      this.salesorderService.sureSaleOrder(id).then(({ data }) => {
        this.$message.success(data);
        this.refreshList();
        this.loading = false;
      });
    },
    // 下载模板
    downloadTpl() {
      // this.$utils.downloadExcel('/sys/user/import/template')
      this.salesorderService
        .exportTemplate()
        .then((res) => {
          // 将二进制流文件写入excel表，以下为重要步骤
          this.$utils.downloadExcel(res.data, "销售订单模板");
        })
        .catch(function (err) {
          if (err.response) {
            console.log(err.response);
          }
        });
    }, // 自定义服务端导出
    exportMethod({ options }) {
      // 传给服务端的参数
      const params = {
        current: this.tablePage.currentPage,
        size: this.tablePage.pageSize,
        orders: this.tablePage.orders,
        ...this.searchForm,
        filename: options.filename,
        sheetName: options.sheetName,
        isHeader: options.isHeader,
        original: options.original,
        mode: options.mode,
        selectIds:
          options.mode === "selected"
            ? options.data.map((item) => item.id)
            : [],
        exportFields: options.columns.map((column) => column.property),
      };
      return this.userService
        .exportExcel(params)
        .then((res) => {
          // 将二进制流文件写入excel表，以下为重要步骤
          this.$utils.downloadExcel(res.data, options.filename);
        })
        .catch(function (err) {
          if (err.response) {
            console.log(err.response);
          }
        });
    },
    // 自定义服务端导入
    importMethod({ file }) {
      // 处理表单
      const formBody = new FormData();
      formBody.append("file", file);
      this.salesorderService.importExcel(formBody).then((result) => {
        console.info(result);
        this.$message.success(result.data);
      });
    },
  },
};
</script>
