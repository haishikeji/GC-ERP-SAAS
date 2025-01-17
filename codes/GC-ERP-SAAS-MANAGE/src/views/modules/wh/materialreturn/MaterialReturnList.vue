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
      <el-form-item prop="startDate">
        <el-date-picker
          v-model="searchForm.startDate"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="选择日期时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item prop="endDate">
        <el-date-picker
          v-model="searchForm.endDate"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="选择日期时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item prop="pdCode">
        <el-input
          size="small"
          v-model="searchForm.materialPlan.materialplanCode"
          placeholder="物料计划单"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item prop="returnWarehouse.id">
        <GridSelect
          title="选择退货仓库"
          placeholder="请选择退货仓库"
          labelName="name"
          labelValue="id"
          :value="searchForm.returnWarehouse.id"
          :limit="1"
          size="small"
          @getValue="
            (value) => {
              searchForm.returnWarehouse.id = value;
            }
          "
          :columns="[
            {
              prop: 'id',
              label: 'id',
            },
            {
              prop: 'name',
              label: '仓库名称',
            },
          ]"
          :searchs="[
            {
              prop: 'name',
              label: '仓库名称',
            },
          ]"
          dataListUrl="/basic/warehouse/wareHouse/list"
          queryEntityUrl="/basic/warehouse/wareHouse/queryById"
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
            v-if="hasPermission('wh:materialreturn:materialReturn:add')"
            type="primary"
            size="small"
            icon="el-icon-plus"
            @click="add()"
            >新建</el-button
          >
          <!-- <el-button v-if="hasPermission('wh:materialreturn:materialReturn:edit')" type="warning" size="small" icon="el-icon-edit-outline" @click="edit()" :disabled="$refs.materialReturnTable && $refs.materialReturnTable.getCheckboxRecords().length !== 1" plain>修改</el-button>
            <el-button v-if="hasPermission('wh:materialreturn:materialReturn:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()" :disabled="$refs.materialReturnTable && $refs.materialReturnTable.getCheckboxRecords().length === 0" plain>删除</el-button> -->
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
          ref="materialReturnTable"
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
          <vxe-column type="expand" width="80">
            <template #content="{ row }">
              <el-tabs>
                <el-tab-pane label="退料详情">
                  <el-table
                    size="small"
                    :data="row.materialReturnDetailDTOList"
                    style="width: 100%"
                  >
                    <el-table-column
                      prop="inventory.inventoryCode"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="物料编码"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="inventory.inventoryName"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="物料名称"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="inventory.model"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="规格型号"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="inventory.nventoryAttributeValue"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="存货属性值"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="inventory.singalUnit"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
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
                      prop="num"
                      show-overflow-tooltip
                      label="退回数量"
                    >
                    </el-table-column>
                  </el-table>
                </el-tab-pane>
              </el-tabs>
            </template>
          </vxe-column>
          <vxe-column field="returnWarehouse.name" sortable title="退料仓库">
            <template slot-scope="scope">
              <!-- <el-link  type="primary" :underline="false" v-if="hasPermission('wh:materialreturn:materialReturn:edit')" @click="edit(scope.row.id)">{{scope.row.returnWarehouse && scope.row.returnWarehouse.name}}</el-link>
              <el-link  type="primary" :underline="false" v-else-if="hasPermission('wh:materialreturn:materialReturn:view')"  @click="view(scope.row.id)">{{scope.row.returnWarehouse &&  scope.row.returnWarehouse.name}}</el-link> -->
              <span>{{
                scope.row.returnWarehouse && scope.row.returnWarehouse.name
              }}</span>
            </template>
          </vxe-column>
          <vxe-column
            field="materialPlan.materialplanCode"
            sortable
            title="计划单"
          >
            <template slot-scope="scope">
              {{
                scope.row.materialPlan &&
                scope.row.materialPlan.materialplanCode
              }}
            </template>
          </vxe-column>
          <vxe-column field="returnUser.name" sortable title="退料人">
            <template slot-scope="scope">
              {{ scope.row.returnUser && scope.row.returnUser.name }}
            </template>
          </vxe-column>
          <vxe-column field="returnDate" sortable title="退货时间">
          </vxe-column>
          <vxe-column field="remark" sortable title="备注"> </vxe-column>
          <vxe-column fixed="right" align="center" width="200" title="操作">
            <template slot-scope="scope">
              <el-button
                v-if="hasPermission('wh:materialreturn:materialReturn:view')"
                type="text"
                icon="el-icon-view"
                size="small"
                @click="view(scope.row.id)"
                >查看</el-button
              >
              <!-- <el-button v-if="hasPermission('wh:materialreturn:materialReturn:edit')" type="text" icon="el-icon-edit" size="small" @click="edit(scope.row.id)">修改</el-button>
          <el-button v-if="hasPermission('wh:materialreturn:materialReturn:del')" type="text"  icon="el-icon-delete" size="small" @click="del(scope.row.id)">删除</el-button> -->
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
    <MaterialReturnForm
      ref="materialReturnForm"
      @refreshDataList="refreshList"
    ></MaterialReturnForm>
  </div>
</template>

<script>
import MaterialReturnForm from "./MaterialReturnForm";
import MaterialReturnService from "@/api/wh/materialreturn/MaterialReturnService";
import GridSelect from "@/components/gridSelect";
export default {
  data() {
    return {
      searchForm: {
        startDate: "",
        endDate: "",
        materialPlan: { materialplanCode: "" },
        returnWarehouse: { id: "" },
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
    MaterialReturnForm,
    GridSelect,
  },
  materialReturnService: null,
  created() {
    this.materialReturnService = new MaterialReturnService();
  },
  activated() {
    this.refreshList();
  },
  methods: {
    // 获取数据列表
    refreshList() {
      this.loading = true;
      this.materialReturnService
        .list({
          current: this.tablePage.currentPage,
          size: this.tablePage.pageSize,
          orders: this.tablePage.orders,
          // ...this.searchForm
          startDate: this.searchForm.startDate,
          endDate: this.searchForm.endDate,
          "materialPlan.materialplanCode":
            this.searchForm.materialPlan.materialplanCode,
          "returnWarehouse.id": this.searchForm.returnWarehouse.id,
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
      this.$refs.materialReturnForm.init("add", "");
    },
    // 修改
    edit(id) {
      id =
        id ||
        this.$refs.materialReturnTable.getCheckboxRecords().map((item) => {
          return item.id;
        })[0];
      this.$refs.materialReturnForm.init("edit", id);
    },
    // 查看
    view(id) {
      this.$refs.materialReturnForm.init("view", id);
    },
    // 删除
    del(id) {
      let ids =
        id ||
        this.$refs.materialReturnTable
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
        this.materialReturnService.delete(ids).then(({ data }) => {
          this.$message.success(data);
          this.refreshList();
          this.loading = false;
        });
      });
    },
    // 查看详情
    detail({ row }) {
      return new Promise((resolve) => {
        this.materialReturnService.queryById(row.id).then(({ data }) => {
          this.dataList.forEach((item, index) => {
            if (item.id === row.id) {
              item.materialReturnDetailDTOList =
                data.materialReturnDetailDTOList;
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
  },
};
</script>
