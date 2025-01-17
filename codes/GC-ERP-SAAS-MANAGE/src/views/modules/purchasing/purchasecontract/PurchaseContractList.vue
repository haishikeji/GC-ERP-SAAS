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
      <el-form-item prop="purchaseCode">
        <el-input
          size="small"
          v-model="searchForm.purchaseCode"
          placeholder="采购合同编号"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item prop="supplier.id">
        <GridSelect
          title="选择供应商"
          placeholder="请选择供应商"
          labelName="supplierName"
          labelValue="id"
          :value="searchForm.supplier.id"
          :limit="1"
          size="small"
          @getValue="
            (value) => {
              searchForm.supplier.id = value;
            }
          "
          :columns="[
            {
              prop: 'supplierName',
              label: '供应商名称',
            },
            {
              prop: 'concat',
              label: '联系人',
            },
            {
              prop: 'phone',
              label: '电话',
            },
            {
              prop: 'address',
              label: '地址',
            },
            {
              prop: 'supplierLevel',
              label: '供应商等级',
            },
          ]"
          :searchs="[
            {
              prop: 'supplierName',
              label: '供应商名称',
            },
          ]"
          dataListUrl="/supplier/inventoryset/supplier/list"
          queryEntityUrl="/supplier/inventoryset/supplier/queryById"
        >
        </GridSelect>
      </el-form-item>
      <el-form-item prop="sjfw">
        <el-date-picker
          v-model="searchForm.sjfw"
          type="daterange"
          size="small"
          align="right"
          value-format="yyyy-MM-dd"
          unlink-panels
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
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
            v-if="
              hasPermission('purchasing:purchasecontract:purchaseContract:add')
            "
            type="primary"
            size="small"
            icon="el-icon-plus"
            @click="add()"
            >新建</el-button
          >
          <el-button
            v-if="
              hasPermission('purchasing:purchasecontract:purchaseContract:edit')
            "
            type="warning"
            size="small"
            icon="el-icon-edit-outline"
            @click="edit()"
            :disabled="
              $refs.purchaseContractTable &&
              $refs.purchaseContractTable.getCheckboxRecords().length !== 1
            "
            plain
            >修改</el-button
          >
          <el-button
            v-if="
              hasPermission('purchasing:purchasecontract:purchaseContract:del')
            "
            type="danger"
            size="small"
            icon="el-icon-delete"
            @click="del()"
            :disabled="
              $refs.purchaseContractTable &&
              $refs.purchaseContractTable.getCheckboxRecords().length === 0
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
          ref="purchaseContractTable"
          show-header-overflow
          show-overflow
          highlight-hover-row
          :menu-config="{}"
          :print-config="{}"
          :import-config="{}"
          :export-config="{}"
          @sort-change="sortChangeHandle"
          :sort-config="{ remote: true }"
          :data="dataList"
          :checkbox-config="{ checkMethod: checCheck }"
        >
          <vxe-column type="seq" width="40"></vxe-column>
          <vxe-column type="checkbox" width="40px"></vxe-column>
          <vxe-column field="purchaseCode" sortable title="采购合同编号">
            <template slot-scope="scope">
              <el-link
                type="primary"
                :underline="false"
                v-if="
                  hasPermission(
                    'purchasing:purchasecontract:purchaseContract:edit'
                  ) && scope.row.purchaseState == '1'
                "
                @click="edit(scope.row.id)"
              >
                {{ scope.row.purchaseCode }}
              </el-link>
              <el-link
                type="primary"
                :underline="false"
                v-else-if="
                  hasPermission(
                    'purchasing:purchasecontract:purchaseContract:view'
                  )
                "
                @click="view(scope.row.id)"
                >{{ scope.row.purchaseCode }}</el-link
              >
              <span v-else>{{ scope.row.purchaseCode }}</span>
            </template>
          </vxe-column>
          <vxe-column field="supplier.supplierName" sortable title="供应商">
            <template slot-scope="scope">
              {{ scope.row.supplier && scope.row.supplier.supplierName }}
            </template>
          </vxe-column>
          <vxe-column field="purchaseData" sortable title="合同时间">
          </vxe-column>
          <vxe-column field="purchaseFiles" sortable title="合同附件">
            <template slot-scope="scope">
              <a
                :href="item"
                target="_blank"
                :key="index"
                v-for="(item, index) in (scope.row.purchaseFiles || '').split(
                  '|'
                )"
              >
                {{
                  decodeURIComponent(item.substring(item.lastIndexOf("/") + 1))
                }}
              </a>
            </template>
          </vxe-column>
          <vxe-column field="purchaseState" sortable title="合同状态">
            <template slot-scope="scope">
              {{
                $dictUtils.getDictLabel(
                  "sx_pur_contract",
                  scope.row.purchaseState,
                  "-"
                )
              }}
            </template>
          </vxe-column>
          <vxe-column field="purchaseResult" sortable title="合同备注">
          </vxe-column>
          <vxe-column fixed="right" align="center" width="200" title="操作">
            <template slot-scope="scope">
              <el-button
                v-if="
                  hasPermission(
                    'purchasing:purchasecontract:purchaseContract:view'
                  )
                "
                type="text"
                icon="el-icon-view"
                size="small"
                @click="view(scope.row.id)"
                >查看</el-button
              >
              <el-button
                v-if="
                  hasPermission(
                    'purchasing:purchasecontract:purchaseContract:edit'
                  )
                "
                type="text"
                icon="el-icon-edit"
                size="small"
                @click="edit(scope.row.id) && scope.row.purchaseState == '1'"
                >修改
              </el-button>
              <el-button
                v-if="
                  hasPermission(
                    'purchasing:purchasecontract:purchaseContract:del'
                  )
                "
                type="text"
                icon="el-icon-delete"
                size="small"
                @click="del(scope.row.id) && scope.row.purchaseState == '1'"
                >删除
              </el-button>
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
    <PurchaseContractForm
      ref="purchaseContractForm"
      @refreshDataList="refreshList"
    ></PurchaseContractForm>
  </div>
</template>

<script>
import PurchaseContractForm from "./PurchaseContractForm";
import PurchaseContractService from "@/api/purchasing/purchasecontract/PurchaseContractService";
import GridSelect from "@/components/gridSelect";
export default {
  data() {
    return {
      searchForm: {
        purchaseCode: "",
        supplier: {
          id: "",
        },
        sjfw: [],
        purchaseState: "",
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
    PurchaseContractForm,
  },
  purchaseContractService: null,
  created() {
    this.purchaseContractService = new PurchaseContractService();
    this.defaultDate();
  },
  activated() {
    this.refreshList();
  },
  methods: {
    checCheck({ row }) {
      if (row.purchaseState === "1") {
        return true;
      } else {
        return false;
      }
    },
    // 获取数据列表
    refreshList() {
      if (this.searchForm.sjfw != null && this.searchForm.sjfw.length > 0) {
        this.searchForm.sjfw.forEach((item, index) => {
          if (index == 0) {
            this.searchForm.purchaseBegin = item + " 00:00:00";
          } else if (index == 1) {
            this.searchForm.purchaseEnd = item + " 23:59:59";
          }
        });
      }
      this.loading = true;
      this.purchaseContractService
        .list({
          current: this.tablePage.currentPage,
          size: this.tablePage.pageSize,
          orders: this.tablePage.orders,
          purchaseCode: this.searchForm.purchaseCode,
          "supplier.id": this.searchForm.supplier.id,
          purchaseBegin: this.searchForm.purchaseBegin,
          purchaseEnd: this.searchForm.purchaseEnd,
          purchaseState: this.searchForm.purchaseState,
          // ...this.searchForm
        })
        .then(({ data }) => {
          this.dataList = data.records;
          this.tablePage.total = data.total;
          this.loading = false;
        });
    },
    //设置当前月
    defaultDate() {
      let date = new Date();
      let year = date.getFullYear().toString();
      let month =
        date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1).toString()
          : (date.getMonth() + 1).toString();
      let da =
        date.getDate() < 10
          ? "0" + date.getDate().toString()
          : date.getDate().toString();
      let end = year + "-" + month + "-" + da;
      let beg = year + "-" + month + "-01";
      this.searchForm.sjfw = [beg, end]; //将值设置给插件绑定的数据
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
      this.$refs.purchaseContractForm.init("add", "");
    },
    // 修改
    edit(id) {
      id =
        id ||
        this.$refs.purchaseContractTable.getCheckboxRecords().map((item) => {
          return item.id;
        })[0];
      this.$refs.purchaseContractForm.init("edit", id);
    },
    // 查看
    view(id) {
      this.$refs.purchaseContractForm.init("view", id);
    },
    // 删除
    del(id) {
      let ids =
        id ||
        this.$refs.purchaseContractTable
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
        this.purchaseContractService.delete(ids).then(({ data }) => {
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
