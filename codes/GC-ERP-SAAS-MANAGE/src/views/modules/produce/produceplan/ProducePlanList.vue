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
      <el-form-item prop="produceplanCode">
        <el-input
          size="small"
          v-model="searchForm.produceplanCode"
          placeholder="任务单号"
          clearable
        ></el-input>
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
      <vxe-toolbar :refresh="{ query: refreshList }" export print custom>
        <template #buttons>
          <el-button
            v-if="hasPermission('produce:produceplan:producePlan:add')"
            type="primary"
            size="small"
            icon="el-icon-plus"
            @click="add()"
            >新建</el-button
          >
          <el-button
            v-if="hasPermission('produce:produceplan:producePlan:edit')"
            type="warning"
            size="small"
            icon="el-icon-edit-outline"
            @click="edit()"
            :disabled="
              $refs.producePlanTable &&
              $refs.producePlanTable.getCheckboxRecords().length !== 1
            "
            plain
            >修改
          </el-button>
          <!-- <el-button v-if="hasPermission('produce:produceplan:producePlan:del')" type="danger" size="small"
            icon="el-icon-delete" @click="del()"
            :disabled="$refs.producePlanTable && $refs.producePlanTable.getCheckboxRecords().length === 0" plain>删除
          </el-button> -->
          <el-button
            v-if="
              hasPermission(
                'produce:produceplan:producePlan:createMaterialPlan'
              )
            "
            type="primary"
            size="small"
            icon="el-icon-s-unfold"
            @click="createMpByProducePlan()"
            :disabled="
              $refs.producePlanTable &&
              $refs.producePlanTable.getCheckboxRecords().length !== 1
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
          ref="producePlanTable"
          show-header-overflow
          show-overflow
          highlight-hover-row
          :menu-config="{}"
          :print-config="{}"
          :import-config="{}"
          :export-config="{}"
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
                <el-tab-pane label="生产计划单详情">
                  <el-table
                    size="small"
                    :data="row.producePlanDetailDTOList"
                    style="width: 100%"
                  >
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
                      prop="planProduceNum"
                      show-overflow-tooltip
                      label="计划生产数量"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="orderDate"
                      show-overflow-tooltip
                      label="订货日期"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="deliveryDate"
                      show-overflow-tooltip
                      label="交货日期"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="planFinishDate"
                      show-overflow-tooltip
                      label="计划完成日期"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="trueFinishDate"
                      :formatter="formatDate"
                      show-overflow-tooltip
                      label="实际完成日期"
                    >
                    </el-table-column>
                  </el-table>
                </el-tab-pane>
              </el-tabs>
            </template>
          </vxe-column>
          <vxe-column field="produceplanCode" sortable title="任务单号">
            <template slot-scope="scope">
              <el-link
                type="primary"
                :underline="false"
                v-if="hasPermission('produce:produceplan:producePlan:edit')"
                @click="edit(scope.row.id)"
                >{{ scope.row.produceplanCode }}</el-link
              >
              <el-link
                type="primary"
                :underline="false"
                v-else-if="
                  hasPermission('produce:produceplan:producePlan:view')
                "
                @click="view(scope.row.id)"
              >
                {{ scope.row.produceplanCode }}
              </el-link>
              <span v-else>{{ scope.row.produceplanCode }}</span>
            </template>
          </vxe-column>
          <vxe-column
            field="salesorder.salesorderCode"
            sortable
            title="销售订单"
          >
            <template slot-scope="scope">
              <el-link
                type="primary"
                :underline="false"
                v-if="hasPermission('sales:salesorder:salesorder:view')"
                @click="viewSale(scope.row.salesorder.id)"
                >{{ scope.row.salesorder.salesorderCode }}</el-link
              >
              <span v-else>{{ scope.row.salesorder.salesorderCode }}</span>
            </template>
          </vxe-column>
          <vxe-column
            field="salesorder.customer.customerName"
            sortable
            title="客户名称"
          >
          </vxe-column>
          <vxe-column field="createBy.name" sortable title="创建人">
            <template slot-scope="scope">
              {{ scope.row.createBy.name }}
            </template>
          </vxe-column>
          <vxe-column field="createDate" sortable title="创建时间">
            <template slot-scope="scope">
              {{ scope.row.createDate }}
            </template>
          </vxe-column>
          <vxe-column field="producePlanState" sortable title="任务单状态">
            <template slot-scope="scope">
              {{
                $dictUtils.getDictLabel(
                  "produce_plan_state",
                  scope.row.producePlanState,
                  "-"
                )
              }}
            </template>
          </vxe-column>
          <vxe-column fixed="right" align="center" width="300" title="操作">
            <template slot-scope="scope">
              <el-button
                v-if="
                  hasPermission(
                    'produce:produceplan:producePlan:sureProducePlan'
                  ) && scope.row.producePlanState == 0
                "
                type="text"
                size="small"
                @click="sureProducePlan(scope.row.id)"
                >确认计划单</el-button
              >
              <el-button
                v-if="hasPermission('produce:produceplan:producePlan:view')"
                type="text"
                icon="el-icon-view"
                size="small"
                @click="view(scope.row.id)"
                >查看</el-button
              >
              <el-button
                v-if="
                  hasPermission('produce:produceplan:producePlan:edit') &&
                  scope.row.producePlanState == 0
                "
                type="text"
                icon="el-icon-edit"
                size="small"
                @click="edit(scope.row.id)"
                >修改</el-button
              >
              <el-button
                v-if="
                  hasPermission('produce:produceplan:producePlan:del') &&
                  scope.row.producePlanState == 0
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
    <ProducePlanForm
      ref="producePlanForm"
      @refreshDataList="refreshList"
    ></ProducePlanForm>
  </div>
</template>

<script>
import ProducePlanForm from "./ProducePlanForm";
import SalesorderForm from "../../sales/salesorder/SalesorderForm.vue";
import ProducePlanService from "@/api/produce/produceplan/ProducePlanService";
import GridSelect from "@/components/gridSelect";
export default {
  data() {
    return {
      searchForm: {
        produceplanCode: "",
        salesorder: {
          id: "",
        },
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
    // GridSelect,
    ProducePlanForm,
    SalesorderForm,
  },
  producePlanService: null,
  created() {
    this.producePlanService = new ProducePlanService();
  },
  activated() {
    this.refreshList();
  },
  methods: {
    // 获取数据列表
    refreshList() {
      //倒序条件
      this.tablePage.orders = [];
      let pa1 = {};
      pa1.column = "a.produce_plan_state";
      pa1.asc = true;

      let pa = {};
      pa.column = "a.create_date";
      pa.asc = false;

      this.tablePage.orders.push(pa1);
      this.tablePage.orders.push(pa);

      this.loading = true;
      this.producePlanService
        .list({
          current: this.tablePage.currentPage,
          size: this.tablePage.pageSize,
          // 'orders': this.tablePage.orders,
          "orders[0].column": "a.produce_plan_state",
          "orders[0].asc": true,
          "orders[1].column": "a.create_date",
          "orders[1].asc": false,
          // ...this.searchForm
          "salesorder.id": this.searchForm.salesorder.id,
          produceplanCode: this.searchForm.produceplanCode,
        })
        .then(({ data }) => {
          this.dataList = data.records;
          this.tablePage.total = data.total;
          this.loading = false;
        });
    },
    //替换表格时间格式
    formatDate(row, column) {
      // 获取单元格数据
      let date = row[column.property];
      if (date) {
        return this.transformDate(date);
      } else {
        return "";
      }
    },
    //标准时间格式转yyyy-MM-dd HH:mm:ss
    transformDate(date) {
      if (date) {
        let dt = new Date(date);
        return (
          dt.getFullYear() +
          "-" +
          (dt.getMonth() + 1 < 10
            ? "0" + (dt.getMonth() + 1)
            : dt.getMonth() + 1) +
          "-" +
          (dt.getDate() < 10 ? "0" + dt.getDate() : dt.getDate()) +
          " "
        );
      } else {
        return "";
      }
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
      this.$refs.producePlanForm.init("add", "");
    },
    // 修改
    edit(id) {
      id =
        id ||
        this.$refs.producePlanTable.getCheckboxRecords().map((item) => {
          return item.id;
        })[0];
      this.producePlanService.queryById(id).then(({ data }) => {
        if (data.producePlanState > 0) {
          this.$message.error("该生产计划单非开立状态，不可编辑修改！");
          return;
        } else {
          this.$refs.producePlanForm.init("edit", id);
        }
      });
    },
    // 查看
    view(id) {
      this.$refs.producePlanForm.init("view", id);
    },
    viewSale(id) {
      this.$refs.salesorderForm.init("view", id);
    },
    // 删除
    del(id) {
      let ids =
        id ||
        this.$refs.producePlanTable
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
        this.producePlanService.delete(ids).then(({ data }) => {
          this.$message.success(data);
          this.refreshList();
          this.loading = false;
        });
      });
    },
    // 查看详情
    detail({ row }) {
      return new Promise((resolve) => {
        this.producePlanService.queryById(row.id).then(({ data }) => {
          this.dataList.forEach((item, index) => {
            if (item.id === row.id) {
              item.producePlanDetailDTOList = data.producePlanDetailDTOList;
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
    //确认计划单
    sureProducePlan(id) {
      this.loading = true;
      this.producePlanService.sureProducePlan(id).then(({ data }) => {
        this.$message.success(data);
        this.refreshList();
        this.loading = false;
      });
    },
    //生成物料计划单
    createMpByProducePlan(id) {
      id =
        id ||
        this.$refs.producePlanTable.getCheckboxRecords().map((item) => {
          return item.id;
        })[0];
      this.producePlanService.createMpByProducePlan(id).then(({ data }) => {
        this.$message.success(data);
        this.refreshList();
      });
    },
  },
};
</script>
