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
      <el-form-item label="物流订单" prop="searchForm.id">
        <GridSelect
          title="物流订单名称"
          labelName="logNumber"
          labelValue="id"
          :value="searchForm.logNumber"
          :limit="1"
          @getValue="
            (value) => {
              searchForm.id = value;
            }
          "
          :columns="[
            {
              prop: 'logNumber',
              label: '物流订单',
            },
            {
              prop: 'contactsName',
              label: '联系人',
            },
            {
              prop: 'contactsPhone',
              label: '联系人电话',
            },
            {
              prop: 'license',
              label: '车牌',
            },
          ]"
          :searchs="[
            {
              prop: 'id',
              label: '物流订单',
            },
          ]"
          dataListUrl="/logistics/order/logistics/list"
          queryEntityUrl="/logistics/order/logistics/queryById"
        >
        </GridSelect>
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
            v-if="hasPermission('logistics:order:logistics:add')"
            type="primary"
            size="small"
            icon="el-icon-plus"
            @click="add()"
            >新建</el-button
          >
          <el-button
            v-if="hasPermission('logistics:orderMoney:logistics:add')"
            type="warning"
            size="small"
            icon="el-icon-edit-outline"
            @click="edit()"
            :disabled="
              $refs.logisticsTable &&
              $refs.logisticsTable.getCheckboxRecords().length !== 1
            "
            plain
            >修改</el-button
          >
          <el-button
            v-if="hasPermission('logistics:order:logistics:del')"
            type="danger"
            size="small"
            icon="el-icon-delete"
            @click="del()"
            :disabled="
              $refs.logisticsTable &&
              $refs.logisticsTable.getCheckboxRecords().length === 0
            "
            plain
            >删除</el-button
          >
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
          ref="logisticsTable"
          show-header-overflow
          show-overflow
          highlight-hover-row
          :menu-config="{}"
          :print-config="{}"
          :import-config="{}"
          :export-config="{}"
          @sort-change="sortChangeHandle"
          :sort-config="{ remote: true }"
          :expand-config="{ accordion: true, lazy: true, loadMethod: detail }"
          :data="dataList"
          :checkbox-config="{}"
        >
          <vxe-column type="seq" width="40"></vxe-column>
          <vxe-column type="checkbox" width="40px"></vxe-column>

          <vxe-column field="logNumber" sortable title="物流订单"> </vxe-column>
          <vxe-column field="license" sortable title="车牌"> </vxe-column>
          <vxe-column field="logType" sortable title="收款类型">
            <template slot-scope="scope">
              {{
                $dictUtils.getDictLabel(
                  "log_money_type",
                  scope.row.logType,
                  "-"
                )
              }}
            </template>
          </vxe-column>
          <vxe-column field="logMoney" sortable title="金额"> </vxe-column>
          <vxe-column field="state" sortable title="订单状态">
            <template slot-scope="scope">
              {{ $dictUtils.getDictLabel("Logi_state", scope.row.state, "-") }}
            </template>
          </vxe-column>
          <vxe-column fixed="right" align="center" width="300" title="操作">
            <template slot-scope="scope">
              <el-button
                v-if="hasPermission('logistics:order:logistics:view')"
                type="text"
                icon="el-icon-view"
                size="small"
                @click="view(scope.row.id)"
                >查看</el-button
              >
              <!-- <el-button v-if="hasPermission('logistics:order:logistics:edit')" type="text" icon="el-icon-edit"
                size="small" @click="edit(scope.row.id)">修改</el-button> -->
              <el-button
                v-if="hasPermission('logistics:order:logistics:del')"
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
    <LogisticsForm
      ref="logisticsForm"
      @refreshDataList="refreshList"
    ></LogisticsForm>
  </div>
</template>

<script>
import LogisticsForm from "./LogisticsMoneyForm";
import LogisticsService from "@/api/logistics/order/LogisticsService";
import GridSelect from "@/components/gridSelect";
export default {
  data() {
    return {
      searchForm: {
        id: "",
        logNumber: "",
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
    LogisticsForm,
    GridSelect,
  },
  logisticsService: null,
  created() {
    this.logisticsService = new LogisticsService();
  },
  activated() {
    this.refreshList();
  },
  methods: {
    // 获取数据列表
    refreshList() {
      this.loading = true;
      let pa = {};
      pa.column = "a.create_date";
      pa.asc = false;
      this.tablePage.orders.push(pa);
      this.loading = true;
      this.searchForm.materialOutBegin = null;
      this.searchForm.materialOutEnd = null;
      if (this.searchForm.sjfw != null && this.searchForm.sjfw.length > 0) {
        this.searchForm.sjfw.forEach((item, index) => {
          if (index == 0) {
            this.searchForm.materialOutBegin = item + " 00:00:00";
          } else if (index == 1) {
            this.searchForm.materialOutEnd = item + " 23:59:59";
          }
        });
      }

      this.logisticsService
        .listMoney({
          current: this.tablePage.currentPage,
          size: this.tablePage.pageSize,
          // 'orders': this.tablePage.orders,
          "orders[0].column": "a.create_date",
          "orders[0].asc": false,
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
      this.$refs.logisticsForm.init("add", "");
    },
    // 修改
    edit(id) {
      id =
        id ||
        this.$refs.logisticsTable.getCheckboxRecords().map((item) => {
          return item.id;
        })[0];
      this.$refs.logisticsForm.init("edit", id);
    },
    // 查看
    view(id) {
      this.$refs.logisticsForm.init("view", id);
    },
    // 删除
    del(id) {
      let ids =
        id ||
        this.$refs.logisticsTable
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
        this.logisticsService.delete(ids).then(({ data }) => {
          this.$message.success(data);
          this.refreshList();
          this.loading = false;
        });
      });
    },
    // 查看详情
    detail({ row }) {
      return new Promise((resolve) => {
        this.logisticsService.queryById(row.id).then(({ data }) => {
          this.dataList.forEach((item, index) => {
            if (item.id === row.id) {
              item.productdeliverydetailsDTOS = data.productdeliverydetailsDTOS;
            }
          });
          resolve();
        });
      });
    },
    resetSearch() {
      (this.searchForm.id = ""), this.refreshList();
    },
    accomplish(id) {
      this.logisticsService
        .logfa(id)
        .then(({ data }) => {
          this.$message.success(data);
          this.$emit("refreshDataList");
          this.refreshList();
        })
        .catch(() => {
          this.loading = false;
        });
    },
    accomplish1(id) {
      this.logisticsService
        .logYi(id)
        .then(({ data }) => {
          this.$message.success(data);
          this.$emit("refreshDataList");
          this.refreshList();
        })
        .catch(() => {
          this.loading = false;
        });
    },
  },
};
</script>
