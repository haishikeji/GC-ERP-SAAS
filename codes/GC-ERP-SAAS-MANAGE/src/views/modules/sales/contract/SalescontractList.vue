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
      <el-form-item prop="contractCode">
        <el-input
          size="small"
          v-model="searchForm.contractCode"
          placeholder="合同编号"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item prop="customer.id">
        <GridSelect
          title="选择客户"
          placeholder="请选择客户"
          labelName="customerName"
          labelValue="id"
          :value="searchForm.customer.id"
          :limit="1"
          size="small"
          @getValue="
            (value) => {
              searchForm.customer.id = value;
            }
          "
          :columns="[
            {
              prop: 'customerName',
              label: '客户名称',
            },
            {
              prop: 'contacts',
              label: '联系人',
            },
            {
              prop: 'contactsPhone',
              label: '联系人电话',
            },
            {
              prop: 'contactsAddr',
              label: '联系人地址',
            },
          ]"
          :searchs="[
            {
              prop: 'customerName',
              label: '客户名称',
            },
            {
              prop: 'contacts',
              label: '联系人',
            },
            {
              prop: 'contactsPhone',
              label: '联系人电话',
            },
          ]"
          dataListUrl="/basic/customer/customer/list"
          queryEntityUrl="/basic/customer/customer/queryById"
        >
        </GridSelect>
      </el-form-item>
      <el-form-item prop="contractTime">
        <el-date-picker
          v-model="searchForm.contractTime"
          type="datetime"
          size="small"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="请选择合同日期"
        >
        </el-date-picker>
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
            v-if="hasPermission('sales:contract:salescontract:add')"
            type="primary"
            size="small"
            icon="el-icon-plus"
            @click="add()"
            >新建</el-button
          >
          <el-button
            v-if="hasPermission('sales:contract:salescontract:edit')"
            type="warning"
            size="small"
            icon="el-icon-edit-outline"
            @click="edit()"
            :disabled="
              $refs.salescontractTable &&
              $refs.salescontractTable.getCheckboxRecords().length !== 1
            "
            plain
            >修改
          </el-button>
          <el-button
            v-if="hasPermission('sales:contract:salescontract:del')"
            type="danger"
            size="small"
            icon="el-icon-delete"
            @click="del()"
            :disabled="
              $refs.salescontractTable &&
              $refs.salescontractTable.getCheckboxRecords().length === 0
            "
            plain
            >删除
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
          ref="salescontractTable"
          show-header-overflow
          show-overflow
          highlight-hover-row
          :menu-config="{}"
          :print-config="{}"
          :import-config="{}"
          :export-config="{}"
          @sort-change="sortChangeHandle"
          :sort-config="{ remote: true, showIcon: false }"
          :data="dataList"
          :checkbox-config="{}"
        >
          <vxe-column type="seq" width="40"></vxe-column>
          <vxe-column type="checkbox" width="40px"></vxe-column>
          <vxe-column field="contractCode" sortable title="合同编号">
            <template slot-scope="scope">
              <el-link
                type="primary"
                :underline="false"
                v-if="hasPermission('sales:contract:salescontract:edit')"
                @click="edit(scope.row.id)"
                >{{ scope.row.contractCode }}</el-link
              >
              <el-link
                type="primary"
                :underline="false"
                v-else-if="hasPermission('sales:contract:salescontract:view')"
                @click="view(scope.row.id)"
                >{{ scope.row.contractCode }}</el-link
              >
              <span v-else>{{ scope.row.contractCode }}</span>
            </template>
          </vxe-column>
          <vxe-column field="customer.customerName" sortable title="客户">
            <template slot-scope="scope">
              {{ scope.row.customer && scope.row.customer.customerName }}
            </template>
          </vxe-column>
          <vxe-column field="contractTime" sortable title="合同日期">
          </vxe-column>
          <vxe-column field="files" sortable title="合同附件">
            <template slot-scope="scope">
              <a
                :href="item"
                target="_blank"
                :key="index"
                v-for="(item, index) in (scope.row.files || '').split('|')"
              >
                {{
                  decodeURIComponent(item.substring(item.lastIndexOf("/") + 1))
                }}
              </a>
            </template>
          </vxe-column>
          <vxe-column field="remark" sortable title="备注"> </vxe-column>
          <vxe-column field="createBy.name" sortable title="创建人">
          </vxe-column>
          <!--  <vxe-column field="createDate" sortable title="创建时间">
          </vxe-column> -->
          <vxe-column fixed="right" align="center" width="200" title="操作">
            <template slot-scope="scope">
              <el-button
                v-if="hasPermission('sales:contract:salescontract:view')"
                type="text"
                icon="el-icon-view"
                size="small"
                @click="view(scope.row.id)"
                >查看</el-button
              >
              <el-button
                v-if="hasPermission('sales:contract:salescontract:edit')"
                type="text"
                icon="el-icon-edit"
                size="small"
                @click="edit(scope.row.id)"
                >修改</el-button
              >
              <el-button
                v-if="hasPermission('sales:contract:salescontract:del')"
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
    <SalescontractForm
      ref="salescontractForm"
      @refreshDataList="refreshList"
    ></SalescontractForm>
  </div>
</template>

<script>
import SalescontractForm from "./SalescontractForm";
import SalescontractService from "@/api/sales/contract/SalescontractService";
import GridSelect from "@/components/gridSelect";
export default {
  data() {
    return {
      searchForm: {
        contractCode: "",
        customer: {
          id: "",
        },
        contractTime: "",
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
    GridSelect,
    SalescontractForm,
  },
  salescontractService: null,
  created() {
    this.salescontractService = new SalescontractService();
  },
  activated() {
    this.refreshList();
  },
  methods: {
    // 获取数据列表
    refreshList() {
      this.tablePage.orders = [];
      //倒序条件
      let pa = {};
      pa.column = "a.create_date";
      pa.asc = false;
      this.tablePage.orders.push(pa);

      this.loading = true;
      this.salescontractService
        .list({
          current: this.tablePage.currentPage,
          size: this.tablePage.pageSize,
          "orders.column": "a.create_date",
          "orders.asc": false,
          // ...this.searchForm
          contractCode: this.searchForm.contractCode,
          "customer.id": this.searchForm.customer.id,
          contractTime: this.searchForm.contractTime,
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
      this.$refs.salescontractForm.init("add", "");
    },
    // 修改
    edit(id) {
      id =
        id ||
        this.$refs.salescontractTable.getCheckboxRecords().map((item) => {
          return item.id;
        })[0];
      this.$refs.salescontractForm.init("edit", id);
    },
    // 查看
    view(id) {
      this.$refs.salescontractForm.init("view", id);
    },
    // 删除
    del(id) {
      let ids =
        id ||
        this.$refs.salescontractTable
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
        this.salescontractService.delete(ids).then(({ data }) => {
          this.$message.success(data);
          this.refreshList();
          this.loading = false;
        });
      });
    },
    resetSearch() {
      this.$refs.searchForm.resetFields();
      this.refreshList();
    },
  },
};
</script>
