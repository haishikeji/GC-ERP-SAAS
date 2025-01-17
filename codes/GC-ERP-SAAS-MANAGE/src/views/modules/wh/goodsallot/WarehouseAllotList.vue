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
      <!-- 搜索框-->
      <el-form-item prop="outWarehouse.id">
        <GridSelect
          title="选择调出仓库"
          placeholder="调出仓库"
          labelName="name"
          labelValue="id"
          :value="searchForm.outWarehouse.id"
          :limit="1"
          size="small"
          @getValue="
            (value) => {
              searchForm.outWarehouse.id = value;
            }
          "
          :columns="[
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
      <el-form-item prop="inWarehouse.id">
        <GridSelect
          title="选择调入仓库"
          placeholder="调入仓库"
          labelName="name"
          labelValue="id"
          :value="searchForm.inWarehouse.id"
          :limit="1"
          size="small"
          @getValue="
            (value) => {
              searchForm.inWarehouse.id = value;
            }
          "
          :columns="[
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
      <el-form-item prop="type">
        <el-select
          id="type"
          v-model="searchForm.type"
          placeholder="调拨类型"
          style="width: 100%"
        >
          <el-option
            v-for="item in $dictUtils.getDictList('ck_inventory_type')"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="aduitStatus">
        <el-select
          id="aduitStatus"
          v-model="searchForm.aduitStatus"
          placeholder="审核状态"
          style="width: 100%"
        >
          <el-option value="0" label="待审核">待审核 </el-option>
          <el-option value="1" label="审核通过">审核通过 </el-option>
          <el-option value="2" label="审核不通过">审核不通过 </el-option>
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
      <vxe-toolbar :refresh="{ query: refreshList }" export print custom>
        <template #buttons>
          <el-button
            v-if="hasPermission('wh:goodsallot:warehouseAllot:add')"
            type="primary"
            size="small"
            icon="el-icon-plus"
            @click="add()"
            >新建</el-button
          >
          <el-button
            v-if="hasPermission('wh:goodsallot:warehouseAllot:edit')"
            type="warning"
            size="small"
            icon="el-icon-edit-outline"
            @click="edit()"
            :disabled="
              $refs.warehouseAllotTable &&
              $refs.warehouseAllotTable.getCheckboxRecords().length !== 1
            "
            plain
            >修改</el-button
          >
          <!-- <el-button v-if="hasPermission('wh:goodsallot:warehouseAllot:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()" :disabled="$refs.warehouseAllotTable && $refs.warehouseAllotTable.getCheckboxRecords().length === 0" plain>删除</el-button> -->
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
          ref="warehouseAllotTable"
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
          <vxe-column type="seq" width="40" label="序号"></vxe-column>
          <vxe-column type="checkbox" width="40px"></vxe-column>
          <vxe-column type="expand" width="80">
            <template #content="{ row }">
              <el-tabs>
                <el-tab-pane label="仓库调拨详情">
                  <el-table
                    size="small"
                    :data="row.allotDetailDTOList"
                    style="width: 100%"
                  >
                    <el-table-column
                      prop="inventory.inventoryCode"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="存货编码"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="inventory.inventoryName"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="存货名称"
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
                      label="属性"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="num"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="调拨数量"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="inventory.inventoryWarehouse.inventoryInStock"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="在库库存"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="inventory.inventoryWarehouse.availableStock"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="可用库存"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="inventory.inventoryWarehouse.lockStock"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="锁定库存"
                    >
                    </el-table-column>

                    <el-table-column
                      prop="inventory.measurementMethod"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="计量方式"
                    >
                      <template slot-scope="scope">
                        {{
                          $dictUtils.getDictLabel(
                            "measurement_method",
                            scope.row.inventory.measurementMethod,
                            "-"
                          )
                        }}
                      </template>
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
                  </el-table>
                </el-tab-pane>
              </el-tabs>
            </template>
          </vxe-column>
          <vxe-column field="outWarehouse.name" sortable title="调出仓库">
            <template slot-scope="scope">
              <el-link
                type="primary"
                :underline="false"
                v-if="hasPermission('wh:goodsallot:warehouseAllot:edit')"
                @click="edit(scope.row.id)"
                >{{
                  scope.row.outWarehouse && scope.row.outWarehouse.name
                }}</el-link
              >
              <el-link
                type="primary"
                :underline="false"
                v-else-if="hasPermission('wh:goodsallot:warehouseAllot:view')"
                @click="view(scope.row.id)"
                >{{
                  scope.row.outWarehouse && scope.row.outWarehouse.name
                }}</el-link
              >
              <span v-else>{{
                scope.row.outWarehouse && scope.row.outWarehouse.name
              }}</span>
            </template>
          </vxe-column>
          <vxe-column field="inWarehouse.name" sortable title="调入仓库">
            <template slot-scope="scope">
              {{ scope.row.inWarehouse && scope.row.inWarehouse.name }}
            </template>
          </vxe-column>
          <vxe-column field="remark" sortable title="备注"> </vxe-column>
          <vxe-column field="aduitStatus" sortable title="审核状态">
            <template slot-scope="scope">
              <el-tag
                v-if="scope.row.aduitStatus === '1'"
                size="small"
                type="success"
                >审核通过</el-tag
              >
              <el-tag
                v-else-if="scope.row.aduitStatus === '0'"
                size="small"
                type="warning"
                >待审核</el-tag
              >
              <el-tag
                v-else-if="scope.row.aduitStatus === '2'"
                size="small"
                type="danger"
                >审核不通过</el-tag
              >
            </template>
          </vxe-column>
          <vxe-column field="aduitUser" sortable title="审核人">
            <template slot-scope="scope">
              {{ scope.row.aduitUser && scope.row.aduitUser.name }}
            </template>
          </vxe-column>
          <vxe-column field="aduitDate" sortable title="审核时间"> </vxe-column>
          <vxe-column field="aduitRemark" sortable title="审核备注">
          </vxe-column>
          <vxe-column field="createDate" sortable title="审核时间">
          </vxe-column>
          <vxe-column fixed="right" align="center" width="230" title="操作">
            <template slot-scope="scope">
              <el-button
                v-if="hasPermission('wh:goodsallot:warehouseAllot:view')"
                type="text"
                icon="el-icon-view"
                size="small"
                @click="view(scope.row.id)"
                >查看</el-button
              >
              <el-button
                v-if="
                  hasPermission('wh:goodsallot:warehouseAllot:edit') &&
                  scope.row.aduitStatus === '0'
                "
                type="text"
                icon="el-icon-edit"
                size="small"
                @click="edit(scope.row.id)"
                >修改</el-button
              >
              <el-button
                v-if="
                  hasPermission('wh:goodsallot:warehouseAllot:del') &&
                  scope.row.aduitStatus != 1
                "
                type="text"
                icon="el-icon-delete"
                size="small"
                @click="del(scope.row.id)"
                >删除</el-button
              >
              <el-button
                v-if="
                  hasPermission('wh:goodsallot:warehouseAllot:aduit') &&
                  scope.row.aduitStatus === '0'
                "
                type="text"
                icon="el-icon-user"
                size="small"
                @click="aduit(scope.row.id)"
                >审核</el-button
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
    <WarehouseAllotForm
      ref="warehouseAllotForm"
      @refreshDataList="refreshList"
    ></WarehouseAllotForm>
    <AduitForm ref="aduitForm" @refreshDataList="refreshList"> </AduitForm>
  </div>
</template>

<script>
import WarehouseAllotForm from "./WarehouseAllotForm";
import WarehouseAllotService from "@/api/wh/goodsallot/WarehouseAllotService";
import AduitForm from "./AduitForm.vue";
import GridSelect from "@/components/gridSelect";
export default {
  data() {
    return {
      searchForm: {
        outWarehouse: {
          id: "",
        },
        inWarehouse: {
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
    WarehouseAllotForm,
    AduitForm,
    GridSelect,
  },
  warehouseAllotService: null,
  created() {
    this.warehouseAllotService = new WarehouseAllotService();
  },
  activated() {
    this.refreshList();
  },
  methods: {
    // 获取数据列表
    refreshList() {
      this.loading = true;
      this.warehouseAllotService
        .list({
          current: this.tablePage.currentPage,
          size: this.tablePage.pageSize,
          orders: this.tablePage.orders,
          // ...this.searchForm
          "outWarehouse.id": this.searchForm.outWarehouse.id,
          "inWarehouse.id": this.searchForm.inWarehouse.id,
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
      this.$refs.warehouseAllotForm.init("add", "");
    },
    // 修改
    edit(id) {
      id =
        id ||
        this.$refs.warehouseAllotTable.getCheckboxRecords().map((item) => {
          return item.id;
        })[0];
      this.$refs.warehouseAllotForm.init("edit", id);
    },
    // 查看
    view(id) {
      this.$refs.warehouseAllotForm.init("view", id);
    },
    // 审核
    aduit(id) {
      this.$refs.aduitForm.init(id);
    },
    // 删除
    del(id) {
      let ids =
        id ||
        this.$refs.warehouseAllotTable
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
        this.warehouseAllotService.delete(ids).then(({ data }) => {
          this.$message.success(data);
          this.refreshList();
          this.loading = false;
        });
      });
    },
    // 查看详情
    detail({ row }) {
      return new Promise((resolve) => {
        this.warehouseAllotService.queryById(row.id).then(({ data }) => {
          this.dataList.forEach((item, index) => {
            if (item.id === row.id) {
              item.allotDetailDTOList = data.allotDetailDTOList;
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
